/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.impl.gui;

import jadam.*;
import jadam.impl.util.ArrayUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author vpc
 */
public abstract class AbstractDrawItem implements DrawItem {
    private String type;
    public ItemProps props;
    public ItemBuildContext buildContext;

    public AbstractDrawItem(String type) {
        this.type = type;
    }

    public ItemProps getProperties() {
        return props;
    }

    public ItemProps setProperties(ItemProps attrs) {
        this.props = attrs;
        return this.props;
    }

    @Override
    public Runnable run(String name, Object... args) {
        return props.runnable(name, args);
    }

    @Override
    public String type() {
        return type;
    }

    public abstract Rectangle2D bounds(DrawContext drawContext);

    @Override
    public void draw(DrawContext drawContext) {
        if (!props.isVisible()) {
            return;
        }
        double r = props.getRotation();
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
        setProperties(this.buildContext.typeProps().clone());
        getProperties().setX(this.buildContext.globalProps().getX());
        getProperties().setY(this.buildContext.globalProps().getY());
    }

    public void move(double x, double y, int seconds) {
        long step = 10;
        int iterations = (int) (seconds * 1000L / step);
        ItemProps a = this.getProperties();
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
            a.setPoint(points[j]);
            buildContext.refresh();
        }
    }

    public void rotate(double to) {
        ItemProps a = this.getProperties();
        a.setRotation(to);
        buildContext.refresh();
    }

    public void rotate(double from, double to, int seconds) {
        long step = 10;
        int iterations = (int) (seconds * 1000L / step);
        ItemProps a = this.getProperties();
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

    public boolean isVisible() {
        return props.isVisible();
    }

    public DrawItem setVisible(boolean visible) {
        this.props.setVisible(visible);
        return this;
    }

    public String getLabel() {
        return props.getLabel();
    }

    public DrawItem setLabel(String label) {
        this.props.setLabel(label);
        return this;
    }

    public Color getPointColor() {
        return props.getPointColor();
    }

    public DrawItem setPointColor(Color pointColor) {
        this.props.setPointColor(pointColor);
        return this;
    }

    public PointStyle getPointStyle() {
        return props.getPointStyle();
    }

    public DrawItem setPointStyle(PointStyle pointStyle) {
        this.props.setPointStyle(pointStyle);
        return this;
    }

    public double getRotation() {
        return props.getRotation();
    }

    public DrawItem setRotation(double rotation) {
        this.props.setRotation(rotation);
        return this;
    }

    public SpeedFunction getSpeed() {
        return props.getSpeed();
    }

    public DrawItem setSpeed(SpeedFunction speed) {
        this.props.setSpeed(speed);
        return this;
    }

    public CurveMode getCurve() {
        return props.getCurve();
    }

    public DrawItem setCurve(CurveMode curve) {
        this.props.setCurve(curve);
        return this;
    }

    public int getLineWidth() {
        return props.getLineWidth();
    }

    public DrawItem setLineWidth(int lineWidth) {
        this.props.setLineWidth(lineWidth);
        return this;
    }

    public LineStyle getLineStyle() {
        return props.getLineStyle();
    }

    public DrawItem setLineStyle(LineStyle lineStyle) {
        this.props.setLineStyle(lineStyle);
        return this;
    }

    public Align getAlign() {
        return props.getAlign();
    }

    public DrawItem setAlign(Align align) {
        this.props.setAlign(align);
        return this;
    }

    public boolean isDrawBorder() {
        return props.isDrawBorder();
    }

    public DrawItem setDrawBorder(boolean drawBorder) {
        this.props.setDrawBorder(drawBorder);
        return this;
    }

    public Point2D getPoint() {
        return props.getPoint();
    }

    public double getX() {
        return props.getX();
    }

    public DrawItem setXY(Point2D p) {
        props.setPoint(p);
        return this;
    }

    public DrawItem setX(double x) {
        this.props.setX(x);
        return this;
    }

    public double getY() {
        return props.getY();
    }

    public DrawItem setY(double y) {
        this.props.setY(y);
        return this;
    }

    public Font getFont() {
        return props.getFont();
    }

    public DrawItem setFont(Font font) {
        this.props.setFont(font);
        return this;
    }

    public double getW() {
        return props.getW();
    }

    public DrawItem setW(double w) {
        this.props.setW(w);
        return this;
    }

    public double getH() {
        return props.getH();
    }

    public DrawItem setH(double h) {
        this.props.setH(h);
        return this;
    }

    public Color getLineColor() {
        return props.getLineColor();
    }

    public DrawItem setLineColor(Color lineColor) {
        this.props.setLineColor(lineColor);
        return this;
    }

    public Paint getBackgroundColor() {
        return props.getBackgroundColor();
    }

    public DrawItem setBackgroundColor(Paint backgroundColor) {
        this.props.setBackgroundColor(backgroundColor);
        return this;
    }

    public boolean isFill() {
        return props.isFill();
    }

    public DrawItem setFill(boolean fill) {
        props.setFill(fill);
        return this;
    }
}
