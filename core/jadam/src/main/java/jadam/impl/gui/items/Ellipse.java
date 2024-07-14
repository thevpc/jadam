package jadam.impl.gui.items;

import jadam.impl.gui.AbstractDrawItem;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;
import jadam.impl.util.DrawContextUtils;

import java.awt.geom.Rectangle2D;

public class Ellipse extends AbstractDrawItem {
    private double xRadius;
    private double yRadius;

    public Ellipse(double xRadius, double yRadius) {
        super("ellipse");
        this.xRadius = xRadius;
        this.yRadius = yRadius;
    }

    @Override
    public Rectangle2D bounds(DrawContext drawContext) {
        double w= drawContext.xPixels(xRadius);
        double h= drawContext.yPixels(yRadius);
        return DrawContextUtils.boundsByCenter(2*w, 2*h, getAttrs(), drawContext);
    }

    @Override
    public void drawImpl(DrawContext drawContext) {
        ItemProps a = getAttrs();
        boolean drawLine = a.isDrawBorder();
        boolean fill = a.isFill();
        if (!drawLine && !fill) {
            drawLine = true;
        }
        Rectangle2D bounds = bounds(drawContext);
        int x = (int) bounds.getMinX();
        int y = (int) bounds.getMinY();
        int w = (int) bounds.getWidth();
        int h = (int) bounds.getHeight();
        if(fill){
            DrawContextUtils.fillBackground(getAttrs(), drawContext, c -> c.graphics().fillOval(x, y, w, h));
        }
        if(drawLine){
            DrawContextUtils.drawLine(getAttrs(), drawContext, c -> c.graphics().drawOval(x, y, w, h));
            DrawContextUtils.drawLabel(getAttrs(), a.getLabel(), (int) bounds.getCenterX(), (int) bounds.getCenterY(), a.getLineColor(), drawContext);
        }
    }
}
