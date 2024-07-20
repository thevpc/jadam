package jadam.impl.util;

import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;

import java.awt.geom.Rectangle2D;

public class GeomUtils {
    public static Rectangle2D union(Rectangle2D a, Rectangle2D b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        double minX = Math.min(a.getMinX(), b.getMinX());
        double minY = Math.min(a.getMinY(), b.getMinY());
        double maxX = Math.max(a.getMaxX(), b.getMaxX());
        double maxY = Math.max(a.getMaxY(), b.getMaxY());
        return new Rectangle2D.Double(
                minX, minY,
                maxX - minX, maxY - minY
        );
    }

    public static Rectangle2D boundsByCenter(double w, double h, ItemProps a) {
        double x = a.getX();
        double y = a.getY();
        w = w / 2;
        h = h / 2;
        int x0 = (int) (x - w);
        int y0 = (int) (y - h);
        return new Rectangle2D.Double(x0, y0, (int) (2 * w), (int) (2 * h));
    }

}
