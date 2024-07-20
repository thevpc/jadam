package jadam.impl.gui.items;

import jadam.DoubleToDoubleFunction;
import jadam.impl.gui.AbstractDrawItem;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.GlobalProps;
import jadam.impl.gui.ItemProps;
import jadam.impl.util.ArrayUtils;
import jadam.impl.util.DrawContextUtils;

import java.awt.geom.Rectangle2D;

public class DoubleToDoubleFunctionPlot extends AbstractDrawItem {
    private DoubleToDoubleFunction f;

    public DoubleToDoubleFunctionPlot(DoubleToDoubleFunction f) {
        super("doubleToDoubleFunction");
        this.f = f;
    }

    @Override
    public Rectangle2D bounds(DrawContext drawContext) {
        return drawContext.bounds();
    }

    @Override
    public Rectangle2D modelBounds() {
        return new Rectangle2D.Double(
                Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY
        );
    }

    @Override
    public void drawImpl(DrawContext drawContext) {
        ItemProps a = getProperties();
        Rectangle2D bounds = bounds(drawContext);
        int steps=drawContext.size().width*10;
        DrawContextUtils.drawLine(getProperties(), drawContext, c -> {
            GlobalProps gp = drawContext.getGlobalProps();
            double[] xx = ArrayUtils.dtimes(gp.getGridMinX(), gp.getGridMaxX(), steps);
            double[] yy = new double[xx.length];
            yy[0]=f.apply(xx[0]);
            for (int i = 1; i < xx.length; i++) {
                yy[i]=f.apply(xx[i]);
                if(
                        Double.isFinite(yy[i])
                        && Double.isFinite(yy[i-1])
                ){
                    int fromX = (int) drawContext.xPixels(xx[i-1]);
                    int fromY = (int) drawContext.yPixels(yy[i-1]);
                    int toX = (int) drawContext.xPixels(xx[i]);
                    int toY = (int) drawContext.yPixels(yy[i]);
                    c.graphics().drawLine(fromX, fromY,
                            toX, toY);
                }
            }
        });
        DrawContextUtils.drawLabel(getProperties(), a.getLabel(), (int) bounds.getCenterX(), (int) bounds.getCenterY(), a.getLineColor(), drawContext);
    }
}
