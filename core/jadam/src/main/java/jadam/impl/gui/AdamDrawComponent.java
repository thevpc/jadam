/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.impl.gui;

import jadam.SpeedFunction;
import jadam.CurveMode;
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
    public final InheritableThreadLocal<GlobalProps> globalProps = new InheritableThreadLocal<>();
    public final InheritableThreadLocal<TypeProps> sharedAttrsByType = new InheritableThreadLocal<>();
    public Timer timer = new Timer();

    public AdamDrawComponent() {
        globalProps.set(new GlobalProps());
        sharedAttrsByType.set(new TypeProps());
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
        if(i!=null) {
            synchronized (items) {
                this.items.add(i);
            }
            globalProps().setLastItem(i);
            i.onAdd(new MyItemBuildContext(i.type()));
            refresh();
            return i;
        }
        return null;
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

    public void printMessage(Object text) {
        console().print(text);
        refresh();
    }

    public void println(Object text) {
        console().println(text);
        refresh();
    }

    public String readln() {
        return console().readln();
    }

    public String readln(Object line) {
        return console().readln(line);
    }

    public String read(Object line) {
        return console().read(line);
    }

    public Console console() {
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
                g2, size, globalProps(), tic, this
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
            TypeProps stringItemPropsMap = sharedAttrsByType();
            ItemProps a = stringItemPropsMap.get(type);
            if (a != null) {
                return a;
            }
            switch (type) {
                case "": {
                    a = new ItemProps();
                    stringItemPropsMap.put(type, a);
                    return a;
                }
                default: {
                    ItemProps d = stringItemPropsMap.get("");
                    if (d == null) {
                        d = new ItemProps();
                        stringItemPropsMap.put("", d);
                    }
                    a = d.clone();
                    stringItemPropsMap.put(type, a);
                    return a;
                }
            }
        }
    }

    public void setSpeed(SpeedFunction speedFunction) {
        DrawItemAny i = lastItem();
        if (i != null) {
            sharedAttrsByType(i.type()).setSpeed(speedFunction);
            i.getProperties().setSpeed(speedFunction);
        } else {
            sharedAttrsByType("").setSpeed(speedFunction);
        }
    }

    public void setCurve(CurveMode curve) {
        DrawItemAny i = lastItem();
        if (i != null) {
            sharedAttrsByType(i.type()).setCurve(curve);
            i.getProperties().setCurve(curve);
        } else {
            sharedAttrsByType("").setCurve(curve);
        }
    }

    public DrawItemAny lastItem() {
        return (DrawItemAny) globalProps().getLastItem();
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

    public Optional<Runnable> runnable(String name, Object... args) {
        DrawItem[] items1 = items();
        for (int i = items1.length - 1; i >= 0; i--) {
            Runnable p = items1[i].run(name, args);
            if (p != null) {
                return Optional.of(() -> {
                    runShared(name, name, args);
                    p.run();
                });
            }
        }
        return Optional.empty();
    }

//    public void run(String name, Object... args) {
//        Optional<Runnable> t = runnable(name, args);
//        if(t.isEmpty()){
//            throw new IllegalArgumentException("method not found " + name);
//        }
//        t.get().run();
//    }

    public DrawItem[] items() {
        synchronized (items) {
            return items.toArray(new DrawItem[0]);
        }
    }

    public Grid grid() {
        Grid g = (Grid) find("grid");
        if (g == null) {
            return add(new Grid());
        }
        return g;
    }

    public void startThread(Runnable run) {
        if(run!=null){
            GlobalProps oldGlobal = globalProps().copy();
            TypeProps oldByType = sharedAttrsByType().copy();
            new Thread(()->{
                globalProps.set(oldGlobal);
                sharedAttrsByType.set(oldByType);
                run.run();
            }).start();
        }
    }


    private class MyItemBuildContext implements ItemBuildContext {
        private String type;

        public MyItemBuildContext(String type) {
            this.type = type;
        }

        public GlobalProps globalProps() {
            return AdamDrawComponent.this.globalProps();
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
                    if(items.size()>0){
                        globalProps().setLastItem(items.get(items.size()-1));
                    }else{
                        globalProps().setLastItem(null);
                    }
                }
                if (a != null) {
                    items.add(a);
                    globalProps().setLastItem(a);
                }
            }
        }
    }

    public TypeProps sharedAttrsByType() {
        TypeProps t = sharedAttrsByType.get();
        if (t == null) {
            t = new TypeProps();
            sharedAttrsByType.set(t);
        }
        return t;
    }

    public GlobalProps globalProps() {
        GlobalProps t = globalProps.get();
        if (t == null) {
            t = new GlobalProps();
            globalProps.set(t);
        }
        return t;
    }
}
