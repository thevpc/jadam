/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.impl.gui;

import jadam.SpeedFunction;
import jadam.StepCurveMode;
import jadam.impl.gui.items.Console;
import jadam.impl.gui.items.Grid;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.JComponent;

/**
 * @author vpc
 */
public class AdamDrawComponent extends JComponent {

    public final java.util.List<DrawItem> items = new ArrayList<>();
    public final GlobalProps globalProps = new GlobalProps();
    public final Map<String, ItemProps> sharedAttrsByType = new LinkedHashMap<>();
    public Timer timer = new Timer();

    public AdamDrawComponent() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refresh();
            }
        }, 1000, 500);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Console console = (Console) find("console");
                if (console != null) {
                    if (console.onKeyPressed(e)) {
                        refresh();
                    }
                }
            }
        });
        this.setFocusTraversalKeysEnabled(false);
        this.setFocusable(true);
        requestFocusInWindow();
    }

    public void refresh() {
        invalidate();
        repaint();
    }

    public <T extends DrawItem> T add(T i) {
        synchronized (items) {
            this.items.add(i);
        }
        i.onAdd(new MyItemBuildContext(i.type()));
        refresh();
        return i;
    }

    public DrawItem find(String name) {
        synchronized (items) {
            for (int i = items.size() - 1; i >= 0; i--) {
                if (items.get(i).type().equals(name)) {
                    return items.get(i);
                }
            }
        }
        return null;
    }

    public void println(String text) {
        console().println(text);
        refresh();
    }

    public String readln() {
        return console().readln();
    }

    private Console console() {
        Console console = (Console) find("console");
        if (console == null) {
            console = new Console();
            add(console);
        }
        return console;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getSize();

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        long tic = System.currentTimeMillis();
        DrawContext drawContext = new DrawContext(
                g2, size, globalProps, tic, this
        );
        DrawItem[] toDraw;
        synchronized (items) {
            toDraw = items.toArray(new DrawItem[0]);
        }
        for (DrawItem drawItem : toDraw) {
            drawItem.draw(drawContext);
        }
    }

    public ItemProps sharedAttrsByType(String type) {
        synchronized (sharedAttrsByType) {
            ItemProps a = sharedAttrsByType.get(type);
            if (a != null) {
                return a;
            }
            switch (type) {
                case "": {
                    a = new ItemProps();
                    sharedAttrsByType.put(type, a);
                    return a;
                }
                default: {
                    ItemProps d = sharedAttrsByType.get("");
                    if (d == null) {
                        d = new ItemProps();
                        sharedAttrsByType.put("", d);
                    }
                    a = d.clone();
                    sharedAttrsByType.put(type, a);
                    return a;
                }
            }
        }
    }

    public void setSpeed(SpeedFunction speedFunction) {
        AbstractDrawItem i = lastAbstractDrawItem();
        if (i != null) {
            sharedAttrsByType(i.type()).setSpeed(speedFunction);
            i.getAttrs().setSpeed(speedFunction);
        } else {
            sharedAttrsByType("").setSpeed(speedFunction);
        }
    }

    public void setCurve(StepCurveMode curve) {
        AbstractDrawItem i = lastAbstractDrawItem();
        if (i != null) {
            sharedAttrsByType(i.type()).setCurve(curve);
            i.getAttrs().setCurve(curve);
        } else {
            sharedAttrsByType("").setCurve(curve);
        }
    }

    private AbstractDrawItem lastAbstractDrawItem() {
        DrawItem[] items1 = items();
        if (items1.length > 0) {
            return (AbstractDrawItem) items1[items1.length - 1];
        }
        return null;
    }

    public void move(double x, double y, int seconds) {
        AbstractDrawItem i = lastAbstractDrawItem();
        if (i != null) {
            i.move(x, y, seconds);
        }
    }

    public void rotate(double fromAngle, double toAngle, int seconds) {
        AbstractDrawItem i = lastAbstractDrawItem();
        if (i != null) {
            i.rotate(fromAngle, toAngle, seconds);
        }
    }

    public void rotate(double angle) {
        AbstractDrawItem i = lastAbstractDrawItem();
        if (i != null) {
            i.rotate(angle);
        }
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void runShared(String type, String name, Object... args) {
        switch (name) {
            case "setBackgroundColor":
            case "setPointColor":
            case "setLineColor":
            case "setFont":
            case "setFontSize": {
                Runnable rr = sharedAttrsByType(type).runnable(name, args);
                if (rr != null) {
                    rr.run();
                }
                break;
            }
        }
    }

    public void run(String name, Object... args) {
        DrawItem[] items1 = items();
        for (int i = items1.length - 1; i >= 0; i--) {
            Runnable p = items1[i].run(name, args);
            if (p != null) {
                runShared(name, name, args);
                p.run();
                return;
            }
        }
        throw new IllegalArgumentException("method not found " + name);
    }

    public void goTo(double x, double y) {
        globalProps.setX(x);
        globalProps.setY(y);
    }


    public DrawItem[] items() {
        synchronized (items) {
            return items.toArray(new DrawItem[0]);
        }
    }

    public Grid drawGrid() {
        Grid g = (Grid) find("grid");
        if(g==null){
            return add(new Grid());
        }
        return g;
    }


    private class MyItemBuildContext implements ItemBuildContext {
        private String type;

        public MyItemBuildContext(String type) {
            this.type = type;
        }

        public GlobalProps globalProps() {
            return globalProps;
        }

        public ItemProps typeProps() {
            return sharedAttrsByType(type);
        }

        @Override
        public void sleep(long millis) {
            AdamDrawComponent.this.sleep(millis);
        }

        @Override
        public void refresh() {
            AdamDrawComponent.this.refresh();
        }

        @Override
        public DrawItem[] items() {
            return AdamDrawComponent.this.items();
        }

        @Override
        public DrawItem peek(int pos) {
            synchronized (items) {
                int i = items.size() - pos;
                return i >= 0 ? items.get(i) : null;
            }
        }

        public void replaceHead(int count, DrawItem a) {
            synchronized (items) {
                for (int i = 0; i < count; i++) {
                    items.remove(items.size() - 1);
                }
                if (a != null) {
                    items.add(a);
                }
            }
        }
    }

    //    public DrawItem text(String name) {
//        return new AbstractDrawItem(name) {
//            @Override
//            public void draw(Graphics2D g, long tic) {
//                g.drawString(name, this.pen.x, this.pen.y);
//            }
//
//            @Override
//            public DrawItemAttrs setAttrs(DrawItemAttrs attrs) {
//                DrawItemAttrs a = super.setAttrs(attrs);
//                Toolkit.getDefaultToolkit().getFontMetrics(font).
//                FontRenderContext frc = new java.awt.font.FontRenderContext(a.font.getTransform(), true, true);
//                LineMetrics m = a.font.getLineMetrics(name, frc);
//                a=a.clone();
//                a.x=m.
//                return a;
//            }
//
//        };
//    }

    public void setGridIntervalX(double minX, double maxX) {
        globalProps.setGridWidth(maxX - minX);
        globalProps.setGridMinX(minX);
    }

    public void setGridIntervalY(double minY, double maxY) {
        globalProps.setGridHeight(maxY - minY);
        globalProps.setGridMinY(minY);
    }

}
