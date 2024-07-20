package jadam.impl.gui.items;

import jadam.impl.gui.AbstractDrawItem;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class DrawItemBoundedBase extends AbstractDrawItem {
    public DrawItemBoundedBase(String id) {
        super(id);
    }

    public Dimension preferredSize(DrawContext drawContext, Dimension d) {
        return null;
    }

    public Point2D.Double preferredModelSize(Point2D d) {
        return null;
    }

    @Override
    public Rectangle2D modelBounds() {
        ItemProps a = getProperties();
        double pw = a.getW();
        double ph = a.getH();
        Point2D.Double d2 = preferredModelSize(new Point2D.Double(pw, ph));
        if (d2 != null) {
            pw = d2.getX();
            ph = d2.getY();
        }

        double px = a.getX();
        double py = a.getY();

        switch (a.getAlign()) {
            case TOP -> {
                px += pw / 2;
            }
            case BOTTOM -> {
                px += pw / 2;
                py += ph;
            }
            case LEFT -> {
                py += ph / 2;
            }
            case RIGHT -> {
                px += pw;
                py += ph / 2;
            }
            case CENTER -> {
                px += pw / 2;
                py += ph / 2;
            }
            case TOP_LEFT -> {

            }
            case TOP_RIGHT -> {
                py += ph;
            }
            case BOTTOM_LEFT -> {
                px += pw;
            }
            case BOTTOM_RIGHT -> {
                px += pw;
                py += ph;
            }
        }
        return new Rectangle2D.Double(
                px,py,pw,ph
        );
    }

    @Override
    public Rectangle2D bounds(DrawContext drawContext) {
        ItemProps a = getProperties();
        double pw = drawContext.wPixels(a.getW());
        double ph = drawContext.hPixels(a.getH());
        Dimension d2 = preferredSize(drawContext, new Dimension((int) pw, (int) ph));
        if (d2 != null) {
            pw = d2.width;
            ph = d2.height;
        }

        double px = drawContext.xPixels(a.getX());
        double py = drawContext.yPixels(a.getY());

        switch (a.getAlign()) {
            case TOP -> {
                px += pw / 2;
            }
            case BOTTOM -> {
                px += pw / 2;
                py += ph;
            }
            case LEFT -> {
                py += ph / 2;
            }
            case RIGHT -> {
                px += pw;
                py += ph / 2;
            }
            case CENTER -> {
                px += pw / 2;
                py += ph / 2;
            }
            case TOP_LEFT -> {

            }
            case TOP_RIGHT -> {
                py += ph;
            }
            case BOTTOM_LEFT -> {
                px += pw;
            }
            case BOTTOM_RIGHT -> {
                px += pw;
                py += ph;
            }
        }
        return new Rectangle2D.Double(
               px,py,pw,ph
        );
    }

    @Override
    public void drawImpl(DrawContext drawContext) {
        Rectangle2D b = bounds(drawContext);
        drawImpl(drawContext, b);
    }

    protected void drawImpl(DrawContext drawContext, Rectangle2D rectangle) {

    }


}
