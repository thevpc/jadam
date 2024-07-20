package jadam.impl.gui.items;

import jadam.impl.gui.*;
import jadam.impl.util.DrawContextUtils;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Polygon extends AbstractDrawItem {
    java.util.List<Point2D> points = new ArrayList<>();

    public Polygon() {
        super("polygon");
    }

    public Rectangle2D modelBounds() {
        double[] xx = new double[points.size()];
        double[] yy = new double[points.size()];

        double minX = 0;
        double maxX = 0;
        double minY = 0;
        double maxY = 0;

        for (int i = 0; i < xx.length; i++) {
            xx[i] = points.get(i).getX();
            yy[i] = points.get(i).getY();
            if(i==0){
                minX = xx[i];
                maxX = xx[i];
                minY = yy[i];
                maxY = yy[i];
            }else{
                minX = Math.min(minX, xx[i]);
                maxX = Math.max(maxX, xx[i]);
                minY = Math.min(minY, yy[i]);
                maxY = Math.max(maxY, yy[i]);
            }
        }
        return new Rectangle2D.Double(
                minX, minY,
                maxX - minX,
                maxY - minY
        );
    }

    @Override
    public Rectangle2D bounds(DrawContext drawContext) {
        int[] xx = new int[points.size()];
        int[] yy = new int[points.size()];

        double minX = 0;
        double maxX = 0;
        double minY = 0;
        double maxY = 0;

        for (int i = 0; i < xx.length; i++) {
            xx[i] = (int) drawContext.xPixels(points.get(i).getX());
            yy[i] = (int) drawContext.yPixels(points.get(i).getY());
            if(i==0){
                minX = xx[i];
                maxX = xx[i];
                minY = yy[i];
                maxY = yy[i];
            }else{
                minX = Math.min(minX, xx[i]);
                maxX = Math.max(maxX, xx[i]);
                minY = Math.min(minY, yy[i]);
                maxY = Math.max(maxY, yy[i]);
            }
        }
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
        int[] xx = new int[points.size()];
        int[] yy = new int[points.size()];
        for (int i = 0; i < xx.length; i++) {
            xx[i] = (int) drawContext.xPixels(points.get(i).getX());
            yy[i] = (int) drawContext.yPixels(points.get(i).getY());
        }

        if (fill) {
            DrawContextUtils.fillBackground(getProperties(), drawContext, c -> c.graphics().fillPolygon(xx, yy, xx.length));
        }
        if (drawLine) {
            DrawContextUtils.drawLine(getProperties(), drawContext, c -> c.graphics().drawPolygon(xx, yy, xx.length));
        }
        Rectangle2D bounds = bounds(drawContext);
        DrawContextUtils.drawLabel(getProperties(), a.getLabel(), (int) bounds.getCenterX(), (int) bounds.getCenterY(), a.getLineColor(), drawContext);
    }

    @Override
    public void onAdd(ItemBuildContext buildContext) {
        super.onAdd(buildContext);
        DrawItem[] items = buildContext.items();
        java.util.List<Line> lines = new ArrayList<>();
        for (int i = items.length - 2; i >= 0; i--) {
            if (items[i] instanceof Line) {
                Line item = (Line) items[i];
                if (lines.isEmpty()) {
                    lines.add(item);
                } else {
                    Line last = lines.get(lines.size()-1);
                    Point2D point2D = last.getProperties().getPosition();
                    if (point2D.equals(item.getTo())) {
                        lines.add(item);
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        if (lines.size() < 2) {
            throw new IllegalArgumentException("missing lines to create polygon");
        }
        for (int i = lines.size() - 1; i >= 0; i--) {
            points.add(new Point2D.Double(lines.get(i).getProperties().x, lines.get(i).getProperties().y));
        }
        points.add(lines.get(0).getTo());
        props.setPosition(points.get(0).getX(),points.get(0).getY());
        buildContext.replaceHead(lines.size() + 1, this);
    }

    @Override
    public void moveTo(double x, double y) {
        double dx = x-props.getX();
        double dy = y-props.getY();
        if(dx!=0 && dy!=0){
            java.util.List<Point2D> points2 = new ArrayList<>();
            for (Point2D point : points) {
                points2.add(new Point2D.Double(point.getX()+dx,point.getY()+dy));
            }
            this.points=points2;
            props.setPosition(points.get(0).getX(),points.get(0).getY());
        }
    }
}
