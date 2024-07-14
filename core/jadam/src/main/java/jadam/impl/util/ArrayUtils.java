package jadam.impl.util;

import jadam.SpeedFunction;
import jadam.StepCurveMode;

import java.awt.geom.Point2D;

public class ArrayUtils {
    public static long[] ltimes(long min, long max, int times) {
        long[] d = new long[times];
        if (times == 1) {
            d[0] = min;
        } else {
            long step = (max - min) / (times - 1);
            for (int i = 0; i < d.length; i++) {
                d[i] = min + i * step;
            }
        }
        return d;
    }

    public static long[] lsteps(long min, long max, long step) {
        int times = (int) Math.abs((max - min) / step) + 1;
        long[] d = new long[times];
        for (int i = 0; i < d.length; i++) {
            d[i] = min + i * step;
        }
        return d;
    }

    public static double[] dtimes(double min, double max, int times) {
        double[] d = new double[times];
        if (times == 1) {
            d[0] = min;
        } else {
            double step = (max - min) / (times - 1);
            for (int i = 0; i < d.length; i++) {
                d[i] = min + i * step;
            }
        }
        return d;
    }

    public static double[] dtimes(double min, double max, int times, SpeedFunction speed) {
        double[] d = new double[times];
        if (times == 1) {
            d[0] = min;
        } else {
            double[] speedValues = new double[times - 1];
            double speedValuesSum = 0;
            double step0 = 1.0 / (times - 1);
            for (int i = 0; i < times - 1; i++) {
                double v = i * step0;
                speedValues[i] = Math.abs(speed.value(v));
                speedValuesSum += speedValues[i];
            }
            for (int i = 0; i < times - 1; i++) {
                speedValues[i] = speedValues[i] / speedValuesSum * (max - min);
            }

            //double step = (max - min) / (times - 1);
            d[0] = min;
            for (int i = 1; i < times - 1; i++) {
                d[i] = speedValues[i - 1] + d[i - 1];
            }
            d[times - 1] = max;
        }
        return d;
    }

    public static Point2D[] ptimes(Point2D p1, Point2D p3, int times, StepCurveMode mode, SpeedFunction acc) {
        switch (mode) {
            case LINEAR -> {
                return pltimes(p1, p3, times, acc);

            }
            case QUAD -> {
                return pqtimes(p1, p3, times, acc);
            }
        }
        throw new IllegalArgumentException("unsupported yet");
    }

    public static Point2D[] pltimes(Point2D p1, Point2D p3, int times, SpeedFunction acc) {
        double[] xvalues = dtimes(p1.getX(), p3.getX(), times, acc);
        double[] yvalues = dtimes(p1.getY(), p3.getY(), times, acc);
        Point2D[] points = new Point2D[xvalues.length];
        for (int i = 0; i < xvalues.length; i++) {
            points[i] = new Point2D.Double(xvalues[i], yvalues[i]);
        }
        return points;
    }

    public static Point2D[] pqtimes(Point2D p1, Point2D p3, int times, SpeedFunction acc) {
        Point2D p2 = new Point2D.Double(
                p1.getX() + (p3.getX() - p1.getX()) * Math.random(),
                p1.getY() + (p3.getY() - p1.getY()) * Math.random()
        );
        return pqtimes(p1, p2, p3, times, acc);
    }

    public static Point2D[] pqtimes(Point2D p1, Point2D p2, Point2D p3, int times, SpeedFunction speedFunction) {
        double[] xvalues = dtimes(p1.getX(), p3.getX(), times, speedFunction);
        Lagrange lg = new Lagrange(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
        Point2D[] points = new Point2D[xvalues.length];
        for (int i = 0; i < xvalues.length; i++) {
            points[i] = new Point2D.Double(xvalues[i], lg.f(xvalues[i]));
        }
        return points;
    }


    public static double[] dsteps(double min, double max, double step) {
        if (step >= 0) {
            if (max < min) {
                return new double[0];
            }
            int times = (int) Math.abs((max - min) / step) + 1;
            double[] d = new double[times];
            for (int i = 0; i < d.length; i++) {
                d[i] = min + i * step;
            }
            return d;
        } else {
            if (min < max) {
                return new double[0];
            }
            int times = (int) Math.abs((max - min) / step) + 1;
            double[] d = new double[times];
            for (int i = 0; i < d.length; i++) {
                d[i] = min + i * step;
            }
            return d;
        }
    }

    private static class Lagrange {
        double x0;
        double y0;
        double x1;
        double y1;
        double x2;
        double y2;

        public Lagrange(double x0, double y0, double x1, double y1, double x2, double y2) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        private double L0(double x) {
            return (x - x1) * (x - x2) / ((x0 - x1) * (x0 - x2));
        }

        private double L1(double x) {
            return (x - x0) * (x - x2) / ((x0 - x1) * (x0 - x2));
        }

        private double L2(double x) {
            return (x - x0) * (x - x1) / ((x2 - x0) * (x2 - x1));
        }

        double f(double x) {
            return y0 * L0(x) + y1 * L1(x) + y2 * L2(x);
        }
    }


    public static double min(double... all) {
        if (all.length == 0) {
            return Double.NaN;
        }
        double v = all[0];
        for (int i = 1; i < all.length; i++) {
            double b = all[i];
            if (b < v) {
                v = b;
            }
        }
        return v;
    }

    public static double max(double... all) {
        if (all.length == 0) {
            return Double.NaN;
        }
        double v = all[0];
        for (int i = 1; i < all.length; i++) {
            double b = all[i];
            if (b > v) {
                v = b;
            }
        }
        return v;
    }


    public static float min(float... all) {
        if (all.length == 0) {
            return Float.NaN;
        }
        float v = all[0];
        for (int i = 1; i < all.length; i++) {
            float b = all[i];
            if (b < v) {
                v = b;
            }
        }
        return v;
    }

    public static float max(float... all) {
        if (all.length == 0) {
            return Float.NaN;
        }
        float v = all[0];
        for (int i = 1; i < all.length; i++) {
            float b = all[i];
            if (b > v) {
                v = b;
            }
        }
        return v;
    }


    public static long min(long... all) {
        if (all.length == 0) {
            return 0;
        }
        long v = all[0];
        for (int i = 1; i < all.length; i++) {
            long b = all[i];
            if (b < v) {
                v = b;
            }
        }
        return v;
    }

    public static long max(long... all) {
        if (all.length == 0) {
            return 0;
        }
        long v = all[0];
        for (int i = 1; i < all.length; i++) {
            long b = all[i];
            if (b > v) {
                v = b;
            }
        }
        return v;
    }

    public static int min(int... all) {
        if (all.length == 0) {
            return 0;
        }
        int v = all[0];
        for (int i = 1; i < all.length; i++) {
            int b = all[i];
            if (b < v) {
                v = b;
            }
        }
        return v;
    }

    public static int max(int... all) {
        if (all.length == 0) {
            return 0;
        }
        int v = all[0];
        for (int i = 1; i < all.length; i++) {
            int b = all[i];
            if (b > v) {
                v = b;
            }
        }
        return v;
    }

}
