package jadam.impl.gui.items;

import jadam.impl.gui.AbstractDrawItem;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;
import jadam.impl.util.DrawContextUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Point extends AbstractDrawItem {

    public Point() {
        super("point");
    }


    @Override
    public Rectangle2D bounds(DrawContext c) {
        ItemProps a = getAttrs();
        double x = c.xPixels(a.getX());
        double y = c.yPixels(a.getY());
        double size = 8;
        return new Rectangle2D.Double(
                x - size / 2, y - size / 2,
                size,
                size
        );
    }


    @Override
    public void drawImpl(DrawContext drawContext) {
        DrawContextUtils.drawPoint(getAttrs(), drawContext, c -> {
            ItemProps a = getAttrs();
            Graphics2D g = c.graphics();
            Rectangle2D b = bounds(c);
            switch (a.getPointStyle()) {
                case PLUS -> {
                    g.drawLine((int) b.getMinX(), (int) b.getCenterY(), (int) b.getMaxX(), (int) b.getCenterY());
                    g.drawLine((int) b.getCenterX(), (int) b.getMinY(), (int) b.getCenterX(), (int) b.getMaxY());
                }
                case CROSS -> {
                    g.drawLine((int) b.getMinX(), (int) b.getMinY(), (int) b.getMaxX(), (int) b.getMaxY());
                    g.drawLine((int) b.getMinX(), (int) b.getMaxY(), (int) b.getMaxX(), (int) b.getMinY());
                }
                case DOT -> {
                    g.fillOval((int) b.getMinX(), (int) b.getMinY(), (int) b.getWidth(), (int) b.getHeight());
                }
            }
            DrawContextUtils.drawLabel(getAttrs(), a.getLabel(), (int) b.getCenterX(), (int) b.getCenterY(), a.getPointColor(), drawContext);
        });
    }


}
