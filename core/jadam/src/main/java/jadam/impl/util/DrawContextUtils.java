package jadam.impl.util;

import jadam.LineStyle;
import jadam.impl.gui.DrawContext;
import jadam.impl.gui.DrawContextRunner;
import jadam.impl.gui.ItemProps;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DrawContextUtils {
    public static void drawArrayHead(ItemProps a, Point2D origin, Point2D direction, DrawContext drawContext) {
        Graphics2D g = drawContext.graphics();
        Color oldColor = g.getColor();


        Stroke oldStroke = g.getStroke();
        int lineWidth = a.getLineWidth();
        if(lineWidth<=0){
            lineWidth=2;
        }
        g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0));
        g.setColor(a.getLineColor());


        int[] xPoints;
        int[] yPoints;
        int arrowSize=8;
        double arrowWidth = arrowSize;
        double arrowHeight = arrowSize;
        if (arrowWidth <= 0) {
            arrowWidth = arrowHeight;
        }
        if (arrowHeight <= 0) {
            arrowHeight = arrowWidth;
        }
        if (arrowHeight <= 0) {
            arrowHeight = 10;
        }
        if (arrowWidth <= 0) {
            arrowWidth = 10;
        }
        double angle = Math.atan2(direction.getY(), direction.getX());
        AffineTransform originalTransform = g.getTransform();
        g.translate(origin.getX(), origin.getY());
        g.rotate(angle);
        xPoints = new int[]{-(int) arrowWidth, 0, -(int) arrowWidth};
        yPoints = new int[]{-(int) arrowHeight, 0, (int) arrowHeight};
        g.drawPolyline(xPoints, yPoints, xPoints.length);
        g.setTransform(originalTransform);
        g.setStroke(oldStroke);
        g.setColor(oldColor);
    }

    public static void drawLine(ItemProps a, DrawContext drawContext, DrawContextRunner runner) {
        Graphics2D g = drawContext.graphics();
        Stroke o = g.getStroke();
        LineStyle lineStyle = a.getLineStyle();
        int lineWidth = a.getLineWidth();
        if(lineWidth<=0){
            lineWidth=2;
        }
        switch (lineStyle) {
            case DASHED: {
                g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10,4}, 2));
                break;
            }
            default: {
                g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0));
                break;
            }
        }
        g.setColor(a.getLineColor());
        runner.run(drawContext);
        g.setStroke(o);
    }

    public static void fillBackground(ItemProps a, DrawContext drawContext, DrawContextRunner runner) {
        Graphics2D g = drawContext.graphics();
        g.setPaint(a.getBackgroundColor());
        runner.run(drawContext);
    }

    public static void drawBorder(ItemProps a, DrawContext drawContext, DrawContextRunner runner) {
        drawLine(a, drawContext, runner);
    }

    public static void drawPoint(ItemProps a, DrawContext drawContext, DrawContextRunner runner) {
        Graphics2D g = drawContext.graphics();
        g.setColor(a.getPointColor());
        runner.run(drawContext);
    }

    public static void drawLabel(ItemProps attrs, String label, int x, int y, Color color, DrawContext drawContext) {
        if (label != null && label.trim().length() > 0) {
            Graphics2D g = drawContext.graphics();
            Color oldColor = g.getColor();
            Font oldFont = g.getFont();
            if(color!=null) {
                g.setColor(color);
            }
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 8));
            g.drawString(label.trim(), x + 10, y - 10);
            g.setFont(oldFont);
            g.setColor(oldColor);
        }
    }

    public static Rectangle2D boundsByCenter(double w, double h, ItemProps a, DrawContext drawContext) {
        double x = drawContext.xPixels(a.getX());
        double y = drawContext.yPixels(a.getY());
        w = w / 2;
        h = h / 2;
        int x0 = (int) (x - w);
        int y0 = (int) (y - h);
        return new Rectangle2D.Double(x0, y0, (int) (2 * w), (int) (2 * h));
    }
}
