package jadam.impl.gui.items;

import jadam.impl.gui.AbstractDrawItem;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class DrawItemBoundedBase extends AbstractDrawItem {
    public DrawItemBoundedBase(String id) {
        super(id);
    }

    public Dimension preferredSize(DrawContext drawContext, Dimension d) {
        return null;
    }

    @Override
    public Rectangle2D bounds(DrawContext drawContext) {
        ItemProps a = getAttrs();
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
