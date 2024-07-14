package jadam.impl.gui.items;

import jadam.impl.gui.AbstractDrawItem;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;
import jadam.impl.util.DrawContextUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Rectangle extends AbstractDrawItem {
    private double width;
    private double height;

    public Rectangle(double width,double height) {
        super("rectangle");
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle2D bounds(DrawContext c) {
        ItemProps a = getProperties();
        double fromX = c.xPixels(a.getX());
        double fromY = c.yPixels(a.getY());
        double toX = c.xPixels(a.getX()+width);
        double toY = c.yPixels(a.getY()+height);
        double minX = Math.min(fromX, toX);
        double maxX = Math.max(fromX, toX);
        double minY = Math.min(fromY, toY);
        double maxY = Math.max(fromY, toY);
        return new Rectangle2D.Double(
                minX, minY,
                maxX - minX,
                maxY - minY
        );
    }

    @Override
    public void drawImpl(DrawContext drawContext) {
        ItemProps a = getProperties();
        boolean drawLine = a.isDrawBorder();
        boolean fill = a.isFill();
        if (!drawLine && !fill) {
            drawLine = true;
        }
        Rectangle2D bounds = bounds(drawContext);
        if(fill) {
            DrawContextUtils.fillBackground(getProperties(), drawContext, c -> {
                Graphics2D g = c.graphics();
                g.fillRect((int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getWidth(), (int) bounds.getHeight());
            });
        }
        if(drawLine) {
            DrawContextUtils.drawLine(getProperties(), drawContext, c -> {
                Graphics2D g = c.graphics();
                g.drawRect((int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getWidth(), (int) bounds.getHeight());
            });
        }
        DrawContextUtils.drawLabel(getProperties(), a.getLabel(), (int) bounds.getCenterX(), (int) bounds.getCenterY(), a.getLineColor(), drawContext);
    }
}
