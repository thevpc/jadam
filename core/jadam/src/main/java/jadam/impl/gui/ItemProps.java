/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.impl.gui;

import jadam.*;
import jadam.impl.util.ColorUtils;
import jadam.impl.util.FontUtils;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 *
 * @author vpc
 */
public class ItemProps implements Cloneable{

    public double x = 0;
    public double y = 0;
    public double rotation = 0;
    public Font font;
    public boolean visible=true;
    public Align align=Align.TOP_LEFT;
    public SpeedFunction speed = SpeedFunction.ACCELERATE_DECELERATE;
    public StepCurveMode curve = StepCurveMode.LINEAR;

    private double w=100;
    private double h=100;
    private Color lineColor= ColorUtils.black;
    private Paint backgroundColor=ColorUtils.darkGreen;
    private Color pointColor=ColorUtils.darkGreen;
    private boolean drawBorder=false;
    private boolean fill=false;
    private int lineWidth =0;
    private LineStyle lineStyle =LineStyle.NORMAL;
    private PointStyle pointStyle = PointStyle.CROSS;
    private String label =null;

    @Override
    public ItemProps clone(){
        try {
            return (ItemProps) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new IllegalArgumentException("unsupported");
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public ItemProps setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ItemProps setLabel(String label) {
        this.label = label;
        return this;
    }

    public Color getPointColor() {
        return pointColor;
    }

    public ItemProps setPointColor(Color pointColor) {
        this.pointColor = pointColor;
        return this;
    }

    public PointStyle getPointStyle() {
        return pointStyle;
    }

    public ItemProps setPointStyle(PointStyle pointStyle) {
        this.pointStyle = pointStyle;
        return this;
    }

    public double getRotation() {
        return rotation;
    }

    public ItemProps setRotation(double rotation) {
        this.rotation = rotation;
        return this;
    }

    public SpeedFunction getSpeed() {
        return speed;
    }

    public ItemProps setSpeed(SpeedFunction acceleration) {
        this.speed = acceleration;
        return this;
    }

    public StepCurveMode getCurve() {
        return curve;
    }

    public ItemProps setCurve(StepCurveMode curve) {
        this.curve = curve;
        return this;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public ItemProps setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public ItemProps setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        return this;
    }

    public Align getAlign() {
        return align;
    }

    public ItemProps setAlign(Align align) {
        this.align = align;
        return this;
    }

    public boolean isDrawBorder() {
        return drawBorder;
    }

    public ItemProps setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
        return this;
    }

    public Point2D getPoint() {
        return new Point2D.Double(x,y);
    }

    public double getX() {
        return x;
    }

    public ItemProps setXY(Point2D  p) {
        this.x=p.getX();
        this.y=p.getY();
        return this;
    }

    public ItemProps setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public ItemProps setY(double y) {
        this.y = y;
        return this;
    }

    public Font getFont() {
        return font;
    }

    public ItemProps setFont(Font font) {
        this.font = font;
        return this;
    }

    public double getW() {
        return w;
    }

    public ItemProps setW(double w) {
        this.w = w;
        return this;
    }

    public double getH() {
        return h;
    }

    public ItemProps setH(double h) {
        this.h = h;
        return this;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public ItemProps setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public Paint getBackgroundColor() {
        return backgroundColor;
    }

    public ItemProps setBackgroundColor(Paint backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public boolean isFill() {
        return fill;
    }

    public ItemProps setFill(boolean fill) {
        this.fill = fill;
        return this;
    }

    public Runnable runnable(String name, Object... args) {
        switch (name) {
            case "setPointStyle": {
                Object c = args[0];
                return () -> this.setPointStyle(PointStyle.resolveDefault(c));
            }
            case "setLabel": {
                Object c = args[0];
                return () -> this.setLabel((String) c);
            }
            case "setBackgroundColor": {
                return () -> this.setBackgroundColor(ColorUtils.resolvePaint(args[0]));
            }
            case "setPointColor": {
                return () -> this.setPointColor(ColorUtils.resolveColor(args[0]));
            }
            case "setLineColor": {
                return () -> this.setLineColor(ColorUtils.resolveColor(args[0]));
            }
            case "setFill": {
                Boolean arg = (Boolean) args[0];
                return () -> this.setFill(arg);
            }
            case "setAlign": {
                return () -> this.setAlign(Align.resolveOrDefault(args[0]));
            }
            case "setDrawBorder": {
                Boolean arg = (Boolean) args[0];
                return () -> this.setDrawBorder(arg);
            }
            case "setFont": {
                return () -> this.setFont(FontUtils.resolveOrDefault(args[0]));
            }
            case "setLineWidth": {
                Number arg = (Number) args[0];
                return () -> {
                    setLineWidth(arg.intValue());
                };
            }
            case "setRotation": {
                Number arg = (Number) args[0];
                return () -> {
                    setRotation(arg.doubleValue());
                };
            }
            case "setLineStyle": {
                return ()->setLineStyle(LineStyle.resolveOrDefault(args[0]));
            }
            case "setFontSize": {
                Number arg = (Number) args[0];
                return () -> {
                    Font font = this.getFont();
                    if(font==null){
                        setFont(new Font(Font.MONOSPACED, Font.PLAIN, arg.intValue()));
                        return;
                    }
                    font=font.deriveFont(arg.floatValue());
                    this.setFont(font);
                };
            }
        }
        return null;
    }
}
