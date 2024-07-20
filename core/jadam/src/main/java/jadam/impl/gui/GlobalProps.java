package jadam.impl.gui;

import java.awt.geom.Point2D;

public class GlobalProps implements Cloneable{
    private double x = 0;
    private double y = 0;
    private double gridMinX = -100;
    private double gridMinY = -100;
    private double gridWidth = 200;
    private double gridHeight = 200;
    private DrawItem lastItem;

    public DrawItem getLastItem() {
        return lastItem;
    }

    public GlobalProps setLastItem(DrawItem lastItem) {
        this.lastItem = lastItem;
        return this;
    }

    public double getX() {
        return x;
    }

    public GlobalProps setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public GlobalProps setY(double y) {
        this.y = y;
        return this;
    }

    public double getGridWidth() {
        return gridWidth;
    }

    public GlobalProps setGridWidth(double virtualWidth) {
        this.gridWidth = virtualWidth;
        return this;
    }

    public double getGridHeight() {
        return gridHeight;
    }

    public GlobalProps setGridHeight(double virtualHeight) {
        this.gridHeight = virtualHeight;
        return this;
    }

    public GlobalProps setGridMinX(double virtualXOffset) {
        this.gridMinX = virtualXOffset;
        return this;
    }

    public GlobalProps setGridMinY(double gridMinY) {
        this.gridMinY = gridMinY;
        return this;
    }

    public double getGridMinX() {
        return gridMinX;
    }

    public double getGridMaxX() {
        return gridMinX + gridWidth;
    }

    public double getGridMinY() {
        return gridMinY;
    }

    public double getGridMaxY() {
        return gridMinY + gridHeight;
    }

    public GlobalProps setXY(double x,double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public GlobalProps setXY(Point2D to) {
        x = to.getX();
        y = to.getY();
        return this;
    }

    public GlobalProps setGridIntervalX(double minX, double maxX) {
        setGridWidth(maxX - minX);
        setGridMinX(minX);
        return this;
    }

    public GlobalProps setGridIntervalY(double minY, double maxY) {
        setGridHeight(maxY - minY);
        setGridMinY(minY);
        return this;
    }

    public GlobalProps copy() {
        try {
            return (GlobalProps) clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
