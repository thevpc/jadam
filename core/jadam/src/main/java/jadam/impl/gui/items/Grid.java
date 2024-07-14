package jadam.impl.gui.items;

import jadam.impl.gui.AbstractDrawItem;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.GlobalProps;
import jadam.impl.util.ColorUtils;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Grid extends AbstractDrawItem {
    private double xStep = 10;
    private double yStep = 10;
    private Color xAxisColor = Color.GRAY.darker();
    private Color yAxisColor = Color.GRAY.darker();
    private Color xGridColor = ColorUtils.lightGray;
    private Color yGridColor = ColorUtils.lightGray;

    public Grid() {
        super("grid");
    }

    @Override
    public Rectangle2D bounds(DrawContext drawContext) {
        return drawContext.bounds();
    }

    @Override
    public Runnable run(String name, Object... args) {
        Runnable r = super.run(name, args);
        if (r != null) {
            return r;
        }
        switch (name) {
            case "setGridTicX": {
                return () -> xStep = ((Number) args[0]).doubleValue();
            }
            case "setGridTicY": {
                return () -> yStep = ((Number) args[0]).doubleValue();
            }
        }
        return null;
    }

    @Override
    public void drawImpl(DrawContext drawContext) {
        Graphics2D g = drawContext.graphics();
        Dimension s = drawContext.size();
        Data d = new Data();
        d.oldStroke = g.getStroke();
        d.oldColor = g.getColor();
        d.oldFont = g.getFont();

        java.awt.Point center = new Point(s.width / 2, s.height / 2);
        GlobalProps gp = drawContext.getGlobalProps();
        d.xStepPixel = s.width / gp.getGridWidth() * xStep;
        d.yStepPixel = s.height / gp.getGridHeight() * yStep;
        d.primary = new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10, null, 0);
        d.secondary = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10, new float[]{10}, 20);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 8));

        {
            double x = 0;
            int i = 0;
            while (true) {
                boolean aa = verticalLine(drawContext, x + i * xStep, d);
                boolean bb = false;
                if (i > 0) {
                    bb = verticalLine(drawContext, x - i * xStep, d);
                }
                if (!aa && !bb) {
                    break;
                }
                i++;
            }
        }
        {
            double y = 0;
            int i = 0;
            while (true) {
                boolean aa = horizontalLine(drawContext, y + i * yStep, d);
                boolean bb = false;
                if (i > 0) {
                    bb = horizontalLine(drawContext, y - i * yStep, d);
                }
                if (!aa && !bb) {
                    break;
                }
                i++;
            }
        }

        g.setFont(d.oldFont);
        g.setStroke(d.oldStroke);
        g.setColor(d.oldColor);
    }

    private static class Data {
        double xStepPixel;
        double yStepPixel;
        Stroke oldStroke;
        Color oldColor;
        Font oldFont;
        Stroke primary;
        Stroke secondary;

    }

    private boolean verticalLine(DrawContext drawContext, double x, Data d) {
        Graphics2D g = drawContext.graphics();
        double xx = drawContext.xPixels(x);
        Dimension s = drawContext.size();
        if (xx >= 0 && xx <= s.getWidth()) {
            if (x == 0) {
                g.setStroke(d.primary);
                g.setColor(yAxisColor);
                g.drawLine((int) xx, 0, (int) xx, s.height);
                double y0 = drawContext.yPixels(0);
                g.drawString("(0,0)", (int) (xx + 0), (int) (y0 + 10));
                {
                    double y = 0;
                    int i = 1;
                    while (true) {
                        boolean some = false;
                        double y3 = drawContext.yPixels(y + i * yStep);
                        if (y3 >= 0 && y3 <= s.getHeight()) {
                            g.drawString(String.valueOf(y + i * yStep), (int) xx + 3, (int) y3 - 3);
                            g.drawLine((int) xx - 2, (int) y3, (int) xx + 2, (int) y3);
                            some = true;
                        }
                        y3 = drawContext.yPixels(y - i * yStep);
                        if (y3 >= 0 && y3 <= s.getHeight()) {
                            g.drawString(String.valueOf(y + i * yStep), (int) xx + 5, (int) y3 - 3);
                            g.drawLine((int) xx - 2, (int) y3, (int) xx + 2, (int) y3);
                            some = true;
                        }

                        if (!some) {
                            break;
                        }
                        i++;
                    }
                }
            } else {
                g.setStroke(d.secondary);
                g.setColor(yGridColor);
                g.drawLine((int) xx, 0, (int) xx, s.height);
            }
            return true;
        }
        return false;
    }

    private boolean horizontalLine(DrawContext drawContext, double y, Data d) {
        Graphics2D g = drawContext.graphics();
        double yy = drawContext.yPixels(y);
        Dimension s = drawContext.size();
        if (yy >= 0 && yy <= s.getWidth()) {
            if (y == 0) {
                g.setStroke(d.primary);
                g.setColor(xAxisColor);
                g.drawLine(0, (int) yy, s.width, (int) yy);
                {
                    double x = 0;
                    int i = 1;
                    while (true) {
                        boolean some = false;
                        double x3 = drawContext.xPixels(x + i * yStep);
                        if (x3 >= 0 && x3 <= s.getWidth()) {
                            g.drawString(String.valueOf(x + i * yStep), (int) x3 + 3, (int) yy + 10);
                            g.drawLine((int) x3, (int) yy - 2, (int) x3, (int) yy + 2);
                            some = true;
                        }
                        x3 = drawContext.xPixels(x - i * yStep);
                        if (x3 >= 0 && x3 <= s.getWidth()) {
                            g.drawString(String.valueOf(x + i * yStep), (int) x3 + 3, (int) yy + 10);
                            g.drawLine((int) x3, (int) yy - 2, (int) x3, (int) yy + 2);
                            some = true;
                        }

                        if (!some) {
                            break;
                        }
                        i++;
                    }
                }

            } else {
                g.setStroke(d.secondary);
                g.setColor(xGridColor);
                g.drawLine(0, (int) yy, s.width, (int) yy);
            }
            return true;
        }
        return false;
    }


}
