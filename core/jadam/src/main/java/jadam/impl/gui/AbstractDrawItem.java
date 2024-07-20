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
public abstract class AbstractDrawItem implements DrawItemAny {
    private String type;
    public ItemProps props;
    public ItemBuildContext buildContext;

    public AbstractDrawItem(String type) {
        this.type = type;
    }

    @Override
    public ItemProps getProperties() {
        return props;
    }

    @Override
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

    public abstract Rectangle2D modelBounds();

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

    public void moveTo(double x, double y, int seconds) {
        long step = 10;
        int iterations = (int) (seconds * 1000L / step);
        ItemProps a = this.getProperties();
        if (seconds <= 0) {
            moveTo(x, y);
            return;
        }
        Point2D.Double from = new Point2D.Double(a.getX(), a.getY());
        Point2D.Double to = new Point2D.Double(x, y);
        Point2D[] points = ArrayUtils.ptimes(from, to, iterations, a.getCurve(), a.getSpeed());

        for (int j = 0; j < points.length; j++) {
            buildContext.sleep(step);
            moveTo(points[j].getX(), points[j].getY());
            buildContext.refresh();
        }
    }

    @Override
    public void moveBy(double x, double y, int seconds) {
        ItemProps a = this.getProperties();
        moveTo(a.getX() + x, a.getY() + y, seconds);
    }

    @Override
    public void moveBy(double x, double y) {
        Point2D p = getPosition();
        moveTo(p.getX()+x, p.getY()+y);
    }

    @Override
    public void rotateBy(double step, int seconds) {
        ItemProps a = this.getProperties();
        rotateTo(a.getRotation() + step, seconds);
    }

    @Override
    public void rotateTo(double to) {
        ItemProps a = this.getProperties();
        a.setRotation(to);
        buildContext.refresh();
    }

    @Override
    public void rotateBy(double step) {
        ItemProps a = this.getProperties();
        a.setRotation(a.getRotation() + step);
        buildContext.refresh();
    }

    public Point2D getPosition() {
        ItemProps a = this.getProperties();
        return a.getPosition();
    }

    @Override
    public void moveTo(double x, double y) {
        ItemProps a = this.getProperties();
        a.setPosition(x, y);
        buildContext.refresh();
    }

    @Override
    public void rotateTo(double to, int seconds) {
        ItemProps a = this.getProperties();
        double from = a.getRotation();
        long step = 10;
        int iterations = (int) (seconds * 1000L / step);
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

    @Override
    public boolean isVisible() {
        return props.isVisible();
    }

    @Override
    public DrawItem setVisible(boolean visible) {
        this.props.setVisible(visible);
        return this;
    }

    @Override
    public String getLabel() {
        return props.getLabel();
    }

    @Override
    public DrawItem setLabel(String label) {
        this.props.setLabel(label);
        return this;
    }

    @Override
    public Color getPointColor() {
        return props.getPointColor();
    }

    @Override
    public DrawItem setPointColor(Color pointColor) {
        this.props.setPointColor(pointColor);
        return this;
    }

    @Override
    public PointStyle getPointStyle() {
        return props.getPointStyle();
    }

    @Override
    public DrawItem setPointStyle(PointStyle pointStyle) {
        this.props.setPointStyle(pointStyle);
        return this;
    }

    @Override
    public double getRotation() {
        return props.getRotation();
    }

    @Override
    public DrawItem setRotation(double rotation) {
        this.props.setRotation(rotation);
        return this;
    }

    @Override
    public SpeedFunction getSpeed() {
        return props.getSpeed();
    }

    @Override
    public DrawItem setSpeed(SpeedFunction speed) {
        this.props.setSpeed(speed);
        return this;
    }

    @Override
    public CurveMode getCurve() {
        return props.getCurve();
    }

    @Override
    public DrawItem setCurve(CurveMode curve) {
        this.props.setCurve(curve);
        return this;
    }

    @Override
    public int getLineWidth() {
        return props.getLineWidth();
    }

    @Override
    public DrawItem setLineWidth(int lineWidth) {
        this.props.setLineWidth(lineWidth);
        return this;
    }

    @Override
    public LineStyle getLineStyle() {
        return props.getLineStyle();
    }

    @Override
    public DrawItem setLineStyle(LineStyle lineStyle) {
        this.props.setLineStyle(lineStyle);
        return this;
    }

    @Override
    public Align getAlign() {
        return props.getAlign();
    }

    @Override
    public DrawItem setAlign(Align align) {
        this.props.setAlign(align);
        return this;
    }

    @Override
    public boolean isDrawBorder() {
        return props.isDrawBorder();
    }

    @Override
    public DrawItem setDrawBorder(boolean drawBorder) {
        this.props.setDrawBorder(drawBorder);
        return this;
    }

    @Override
    public Font getFont() {
        return props.getFont();
    }

    @Override
    public DrawItem setFont(Font font) {
        this.props.setFont(font);
        return this;
    }

    @Override
    public double getW() {
        return props.getW();
    }

    @Override
    public DrawItem setW(double w) {
        this.props.setW(w);
        return this;
    }

    @Override
    public double getH() {
        return props.getH();
    }

    @Override
    public DrawItem setH(double h) {
        this.props.setH(h);
        return this;
    }

    @Override
    public Color getLineColor() {
        return props.getLineColor();
    }

    @Override
    public DrawItem setLineColor(Color lineColor) {
        this.props.setLineColor(lineColor);
        return this;
    }

    @Override
    public Paint getBackgroundColor() {
        return props.getBackgroundColor();
    }

    @Override
    public DrawItem setBackgroundColor(Paint backgroundColor) {
        this.props.setBackgroundColor(backgroundColor);
        return this;
    }

    @Override
    public boolean isFill() {
        return props.isFill();
    }

    @Override
    public DrawItem setFill(boolean fill) {
        props.setFill(fill);
        return this;
    }
}
