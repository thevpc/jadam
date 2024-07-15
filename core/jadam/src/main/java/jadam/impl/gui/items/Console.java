package jadam.impl.gui.items;

import jadam.impl.gui.DrawContext;
import jadam.impl.gui.DrawItem;
import jadam.impl.gui.ItemBuildContext;
import jadam.impl.util.ColorUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class Console implements DrawItem {
    private class CurrAtt implements Cloneable {
        private Color inputColor = Color.GREEN.darker();
        private Color outputColor = Color.BLACK;
        private Color cursorColor = Color.RED;
        private Color currColor = Color.RED.darker();
        private Color currColor2 = Color.YELLOW.darker();

        public CurrAtt copy() {
            try {
                return (CurrAtt) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Row> rows0 = new ArrayList<>();
    private CurrAtt att = new CurrAtt();
    private Row curr = new Row(RowMode.CURR, "", false, att);
    private Consumer<String> newLineConsumer = System.out::println;
    private int maxBufferRows = 200;
    private final AtomicReference<CountDownLatch> latchHolder = new AtomicReference<>();
    private String lastInput = "";
    private Font monospaced = new Font("Monospaced", Font.PLAIN, 16);
    private Font monospacedBold = new Font("Monospaced", Font.BOLD, 16);
    private ItemBuildContext buildContext;

    @Override
    public String type() {
        return "console";
    }

    @Override
    public void onAdd(ItemBuildContext buildContext) {
        this.buildContext = buildContext;
    }

    @Override
    public Runnable run(String name, Object... args) {
        switch (name) {
            case "onKeyPressed": {
                KeyEvent arg = (KeyEvent) args[0];
                return () -> this.onKeyPressed(arg);
            }
            case "readln": {
                return () -> this.readln();
            }
            case "println": {
                String a = (String) args[0];
                return () -> this.println(a);
            }
            case "setCursorColor": {
                Color a = (Color) args[0];
                return () -> currentAtt().cursorColor = a;
            }
            case "setInputColor": {
                Color a = (Color) args[0];
                return () -> currentAtt().inputColor = a;
            }
            case "setTextColor": {
                Color a = (Color) args[0];
                return () -> currentAtt().outputColor = a;
            }
        }
        return null;
    }

    private CurrAtt currentAtt() {
        Row c = currentRow();
        if (c != null) {
            return c.att;
        } else {
            return att;
        }
    }

    private Row currentRow() {
        if (curr.enabled) {
            return curr;
        } else if (rows0.size() > 0) {
            return rows0.get(rows0.size() - 1);
        }
        return null;
    }

    @Override
    public void draw(DrawContext drawContext) {
        Graphics2D g = drawContext.graphics();
        Dimension size = drawContext.size();
        g.setFont(monospaced);
        Rectangle2D b = g.getFontMetrics().getStringBounds("A", g);
        int maxRows = (int) ((size.height - 10) / b.getHeight());
        int maxCols = (int) ((size.width - 10) / b.getWidth());

        int x0 = 3;
        int y0 = (int) b.getHeight();
        List<Row> rows1 = new ArrayList<>();
        for (Row row : rows0) {
            fill(row, rows1, maxCols);
        }
        if (curr.enabled) {
            rows1.add(curr);
        }
        int rowSize = rows1.size();
        int firstRow = rowSize - maxRows;
        if (firstRow < 0) {
            firstRow = 0;
        }
        for (int i = firstRow; i < rowSize; i++) {
            Row row = rows1.get(i);
            row.draw(g, x0, y0, (int) b.getWidth(), drawContext.tic());
            y0 += (int) b.getHeight();
        }
    }

    public boolean onKeyPressed(KeyEvent e) {
        int codePoint = e.getKeyCode();
        char c = e.getKeyChar();
        if (curr.enabled) {
            if (codePoint == 37) {
                //left
                if (curr.cursor > 0) {
                    curr.cursor--;
                }
            } else if (codePoint == 39) {
                //right
                if (curr.cursor < curr.text.length()) {
                    curr.cursor++;
                }
            } else if (codePoint == '\b') {
                if (curr.text.length() > 0) {
                    if (curr.cursor <= 0) {
                        //curr.text = curr.text.substring(curr.cursor+1);
                    } else {
                        curr.text = curr.text.substring(0, curr.cursor - 1) + curr.text.substring(curr.cursor);
                        curr.cursor--;
                    }
                }

            } else if (codePoint == 127 /*DEL*/) {
                if (curr.text.length() > 0 && curr.cursor < curr.text.length()) {
                    curr.text = curr.text.substring(0, curr.cursor) + curr.text.substring(curr.cursor + 1);
                    if (curr.cursor > 0 && curr.cursor >= curr.text.length()) {
                        curr.cursor--;
                    }
                }

            } else if (codePoint == '\n') {
                lastInput = curr.text;
                newLineConsumer.accept(curr.text);
                final CountDownLatch countDownLatch = latchHolder.get();
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
                curr.text = "";
                curr.cursor = 0;
                curr.enabled = false;
            } else if (codePoint >= 32) {
                if (curr.cursor >= curr.text.length()) {
                    curr.text += c;
                } else {
                    curr.text = curr.text.substring(0, curr.cursor) + c + curr.text.substring(curr.cursor);
                }
                curr.cursor++;
            }
            buildContext.refresh();
            return true;
        }
        buildContext.refresh();
        return false;
    }


    public String readln(Object line) {
        println(line);
        return readln();
    }

    public String read(Object line) {
        print(line);
        return readln();
    }

    public String readln() {
        curr.enabled = true;
        // obtaining a local reference for modifying the required latch
        final CountDownLatch oldLatch = latchHolder.getAndSet(null);
        if (oldLatch != null) {
            // checking the count each time to prevent unnecessary countdowns due to parallel countdowns
            while (0L < oldLatch.getCount()) {
                oldLatch.countDown();
            }
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        latchHolder.set(countDownLatch);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        curr.enabled = false;
        rows0.add(new Row(RowMode.IN, lastInput, true, att));
        return lastInput;
    }

    public void println(Object text) {
        rows0.add(new Row(RowMode.OUT, text == null ? "" : text.toString(), true, att));
        if (rows0.size() > maxBufferRows) {
            rows0.remove(0);
        }
    }

    public void print(Object text) {
        rows0.add(new Row(RowMode.OUT, text == null ? "" : text.toString(), true, att));
        if (rows0.size() > maxBufferRows) {
            rows0.remove(0);
        }
    }

    private void fill(Row rr, List<Row> fill, int maxCols) {
        String text = rr.text;
        if (text.isEmpty()) {
            fill.add(new Row(rr.mode, "", true, rr.att));
        }
        if (text.equals("\n")) {
            fill.add(new Row(rr.mode, "", true, rr.att));
            fill.add(new Row(rr.mode, "", true, rr.att));
        } else {

            for (String line : text.split("\n")) {
                while (true) {
                    if (line.length() <= maxCols) {
                        fill.add(new Row(rr.mode, line, true, rr.att));
                        break;
                    } else {
                        if (maxCols <= 0) {
                            break;
                        }
                        String s = line.substring(0, maxCols);
                        fill.add(new Row(rr.mode, s, true, rr.att));
                        line = line.substring(maxCols);
                    }
                }
            }
        }
    }

    public void clear() {
        rows0.clear();
    }

    private enum RowMode {
        IN, OUT, CURR,
        ;
    }

    private class Row {
        private RowMode mode;
        private String text;
        private boolean enabled;
        private boolean blinkStatus;
        private int cursor;
        private CurrAtt att;

        public Row(RowMode mode, String text, boolean enabled, CurrAtt att) {
            this.mode = mode;
            this.text = text;
            this.enabled = enabled;
            this.att = att.copy();
        }

        public void draw(Graphics2D g, int x, int y, int w, long tic) {
            blinkStatus = tic % 1000 < 400;
            String t = text;
            switch (mode) {
                case IN -> {
                    g.setColor(att.inputColor);
                    g.drawString(t, x, y);
                }
                case OUT -> {
                    g.setColor(att.outputColor);
                    g.drawString(t, x, y);
                }
                case CURR -> {
                    g.setColor(att.currColor);
                    g.drawString(t, x, y);
                    g.setColor(att.cursorColor);
                    if (tic % 1000 < 400) {
                        g.setColor(att.currColor);
                        g.setFont(monospacedBold);
                        g.drawString("_", x + (cursor * w), y + 5);
                        g.setFont(monospaced);
                    } else {
                        g.setColor(att.currColor2);
                        g.setFont(monospacedBold);
                        g.drawString("_", x + (cursor * w), y + 5);
                        g.setFont(monospaced);
                    }
                }
            }
        }
    }
}
