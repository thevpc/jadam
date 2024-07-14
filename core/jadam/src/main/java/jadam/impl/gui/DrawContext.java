package jadam.impl.gui;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

public class DrawContext {
    private Graphics2D graphics;
    private Dimension size;
    private GlobalProps globalProps;
    private long tic;
    private ImageObserver imageObserver;

    public DrawContext(Graphics2D graphics, Dimension size, GlobalProps globalProps, long tic, ImageObserver imageObserver) {
        this.graphics = graphics;
        this.size = size;
        this.globalProps = globalProps;
        this.tic = tic;
        this.imageObserver = imageObserver;
    }


    public ImageObserver imageObserver() {
        return imageObserver;
    }

    public Point2D xyPixels(Point2D a) {
        return new Point2D.Double(
                xPixels(a.getX()),
                yPixels(a.getY())
        );
    }

    public double xPixels(double a) {
        GlobalProps gp = getGlobalProps();
        return (a - gp.getGridMinX()) * size.width / gp.getGridWidth();
    }

    public double yPixels(double a) {
        GlobalProps gp = getGlobalProps();
        return (gp.getGridHeight()-(a - gp.getGridMinY())) * size.height  / gp.getGridHeight();
    }

    public double wPixels(double a) {
        return a * size.width / globalProps.getGridWidth();
    }

    public double hPixels(double a) {
        return a * size.height / globalProps.getGridHeight();
    }

    public Graphics2D graphics() {
        return graphics;
    }

    public Rectangle2D bounds() {
        Dimension s = size();
        return new Rectangle2D.Double(
                0, 0,
                s.getWidth(),
                s.getHeight()
        );
    }

    public Dimension size() {
        return size;
    }


    public GlobalProps getGlobalProps() {
        return globalProps;
    }

    public long tic() {
        return tic;
    }
}
