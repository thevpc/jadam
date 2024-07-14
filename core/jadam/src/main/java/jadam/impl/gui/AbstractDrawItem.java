/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.impl.gui;

import jadam.impl.util.ArrayUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author vpc
 */
public abstract class AbstractDrawItem implements DrawItem {
    private String id;
    public ItemProps pen;
    public ItemBuildContext buildContext;

    public AbstractDrawItem(String id) {
        this.id = id;
    }

    public ItemProps getAttrs() {
        return pen;
    }

    public ItemProps setAttrs(ItemProps attrs) {
        this.pen = attrs;
        return this.pen;
    }

    @Override
    public Runnable run(String name, Object... args) {
        return pen.runnable(name, args);
    }

    @Override
    public String type() {
        return id;
    }

    public abstract Rectangle2D bounds(DrawContext drawContext);

    @Override
    public void draw(DrawContext drawContext) {
        if(!pen.isVisible()){
            return;
        }
        double r = pen.getRotation();
        if (r != 0) {
            Graphics2D g = drawContext.graphics();
            AffineTransform t = g.getTransform();
            Rectangle2D rec = bounds(drawContext);
            g.rotate(r, rec.getCenterX(), rec.getCenterY());
            drawImpl(drawContext);
            g.setTransform(t);
        } else {
            drawImpl(drawContext);
        }
    }

    public void drawImpl(DrawContext drawContext) {

    }

    @Override
    public void onAdd(ItemBuildContext buildContext) {
        this.buildContext = buildContext;
        setAttrs(this.buildContext.typeProps().clone());
        getAttrs().setX(this.buildContext.globalProps().getX());
        getAttrs().setY(this.buildContext.globalProps().getY());
    }

    @Override
    public void tic(long tic) {

    }


    public void move(double x, double y, int seconds) {
        long step = 10;
        int iterations = (int) (seconds * 1000L / step);
        ItemProps a = this.getAttrs();
        if (seconds <= 0) {
            a.setX(x);
            a.setY(y);
            return;
        }
        Point2D.Double from = new Point2D.Double(a.getX(), a.getY());
        Point2D.Double to = new Point2D.Double(x, y);
        Point2D[] points = ArrayUtils.ptimes(from, to, iterations, a.getCurve(), a.getSpeed());

        for (int j = 0; j < points.length; j++) {
            buildContext.sleep(step);
            a.setXY(points[j]);
            buildContext.refresh();
        }
    }

    public void rotate(double to) {
        ItemProps a = this.getAttrs();
        a.setRotation(to);
        buildContext.refresh();
    }

    public void rotate(double from, double to, int seconds) {
        long step = 10;
        int iterations = (int) (seconds * 1000L / step);
        ItemProps a = this.getAttrs();
        if (seconds <= 0) {
            a.setRotation(to);
            return;
        }
        double[] values = ArrayUtils.dtimes(from, to, iterations, a.getSpeed());
        for (int j = 0; j < values.length; j++) {
            buildContext.sleep(step);
            a.setRotation(values[j]);
            buildContext.refresh();
        }
    }
}
