package jadam.impl.gui;

import jadam.*;

import java.awt.*;
import java.awt.geom.Point2D;

public interface DrawItemAny extends DrawItem {
    Point2D getPosition();
    void moveTo(double x, double y, int seconds);

    void moveTo(double x, double y);

    ItemProps getProperties();

    ItemProps setProperties(ItemProps attrs);

    void rotateTo(double to);

    void rotateBy(double step);

    void rotateBy(double step, int seconds);

    void rotateTo(double to, int seconds);

    boolean isVisible();

    DrawItem setVisible(boolean visible);

    String getLabel();

    DrawItem setLabel(String label);

    Color getPointColor();

    DrawItem setPointColor(Color pointColor);

    PointStyle getPointStyle();

    DrawItem setPointStyle(PointStyle pointStyle);

    double getRotation();

    DrawItem setRotation(double rotation);

    SpeedFunction getSpeed();

    DrawItem setSpeed(SpeedFunction speed);

    CurveMode getCurve();

    DrawItem setCurve(CurveMode curve);

    int getLineWidth();

    DrawItem setLineWidth(int lineWidth);

    LineStyle getLineStyle();

    DrawItem setLineStyle(LineStyle lineStyle);

    Align getAlign();

    DrawItem setAlign(Align align);

    boolean isDrawBorder();

    DrawItem setDrawBorder(boolean drawBorder);

    Font getFont();

    DrawItem setFont(Font font);

    double getW();

    DrawItem setW(double w);

    double getH();

    DrawItem setH(double h);

    Color getLineColor();

    DrawItem setLineColor(Color lineColor);

    Paint getBackgroundColor();

    DrawItem setBackgroundColor(Paint backgroundColor);

    boolean isFill();

    DrawItem setFill(boolean fill);

    void moveBy(double x, double y, int seconds);

    void moveBy(double x, double y);
}
