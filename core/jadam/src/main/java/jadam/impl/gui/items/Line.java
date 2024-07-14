package jadam.impl.gui.items;

import jadam.impl.gui.AbstractDrawItem;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;
import jadam.impl.gui.ItemBuildContext;
import jadam.impl.util.DrawContextUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Line extends AbstractDrawItem {
    private Point2D to;

    public Line(Point2D to) {
        super("line");
        this.to = to == null ? new Point2D.Double(0, 0) : to;
    }

    public Point2D getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Line{" +
                "from=" + pen.getPoint() +
                "to=" + to +
                '}';
    }

    @Override
    public Rectangle2D bounds(DrawContext c) {
        ItemProps a = getAttrs();
        Graphics2D g = c.graphics();
        double fromX = c.xPixels(a.getX());
        double fromY = c.yPixels(a.getY());
        double toX = c.xPixels(to.getX());
        double toY = c.yPixels(to.getY());
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
        DrawContextUtils.drawLine(getAttrs(), drawContext, c -> {
            ItemProps a = getAttrs();
            Graphics2D g = c.graphics();
            double fromX = c.xPixels(a.getX());
            double fromY = c.yPixels(a.getY());
            double toX = c.xPixels(to.getX());
            double toY = c.yPixels(to.getY());
            g.drawLine((int) fromX, (int) fromY, (int) toX, (int) toY);
            Rectangle2D bounds = bounds(c);
            DrawContextUtils.drawLabel(getAttrs(), a.getLabel(), (int) bounds.getCenterX(), (int) bounds.getCenterY(), a.getLineColor(), drawContext);
        });
    }

    @Override
    public void onAdd(ItemBuildContext buildContext) {
        super.onAdd(buildContext);
        buildContext.typeProps().setXY(to);
        buildContext.globalProps().setXY(to);
    }
}