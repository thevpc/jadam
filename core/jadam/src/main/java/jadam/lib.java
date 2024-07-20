/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam;

import jadam.impl.cui.AdamConsole;
import jadam.impl.gui.*;
import jadam.impl.AdamLib;
import jadam.impl.gui.items.*;
import jadam.impl.gui.items.Image;
import jadam.impl.gui.items.Point;
import jadam.impl.gui.items.Polygon;
import jadam.impl.gui.items.Rectangle;
import jadam.impl.util.ArrayUtils;
import jadam.impl.util.ColorUtils;
import jadam.impl.util.CompareUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author vpc
 */
public class lib {
    private static Random rand = new Random();
    public static final Color black = ColorUtils.black;
    public static final Color blue = ColorUtils.blue;
    public static final Color red = ColorUtils.red;
    public static final Color yellow = ColorUtils.yellow;
    public static final Color green = ColorUtils.green;
    public static final Color cyan = ColorUtils.cyan;
    public static final Color magenta = ColorUtils.magenta;
    public static final Color orange = ColorUtils.orange;
    public static final Color pink = ColorUtils.pink;
    public static final Color gray = ColorUtils.gray;
    public static final Color lightGray = ColorUtils.lightGray;
    public static final Color lightBlue = ColorUtils.lightBlue;
    public static final Color lightRed = ColorUtils.lightRed;
    public static final Color lightYellow = ColorUtils.lightYellow;
    public static final Color lightGreen = ColorUtils.lightGreen;
    public static final Color lightCyan = ColorUtils.lightCyan;
    public static final Color lightMagenta = ColorUtils.lightMagenta;
    public static final Color lightOrange = ColorUtils.lightOrange;
    public static final Color lightPink = ColorUtils.lightPink;
    public static final Color darkBlue = ColorUtils.darkBlue;
    public static final Color darkRed = ColorUtils.darkRed;
    public static final Color darkYellow = ColorUtils.darkYellow;
    public static final Color darkOrange = ColorUtils.darkOrange;
    public static final Color darkGray = ColorUtils.darkGray;
    public static final Color darkGreen = ColorUtils.darkGreen;
    public static final Color darkCyan = ColorUtils.darkCyan;
    public static final Color darkMagenta = ColorUtils.darkMagenta;
    public static final Color darkPink = ColorUtils.darkPink;
    public static final Color white = ColorUtils.white;
    public static final Color[] colors = ColorUtils.colors;

    private static AdamLib CONSOLE = new AdamConsole();
    private static AdamGuiHolder.AdamGUI GUI = new AdamGuiHolder().lib();
    private static AdamLib lib = GUI;

    public static void useConsole() {
        lib = CONSOLE;
    }

    public static void useGui() {
        lib = GUI;
    }

    public static Polygon closePolygon() {
        return GUI.base().add(new Polygon());
    }

    public static Grid drawGrid() {
        return GUI.base().grid();
    }

    public static Text drawText(String text) {
        return GUI.base().add(new Text(text));
    }

    public static void goTo(double x, double y) {
        globalProps().setXY(x, y);
    }

    private static GlobalProps globalProps() {
        return GUI.base().globalProps();
    }

    public static Line lineTo(double x, double y) {
        return GUI.base().add(new Line(new Point2D.Double(x, y)));
    }

    public static DoubleToDoubleFunctionPlot plot(DoubleToDoubleFunction f) {
        return GUI.base().add(new DoubleToDoubleFunctionPlot(f));
    }

    public static Rectangle rectangle(double width, double height) {
        return GUI.base().add(new Rectangle(width, height));
    }

    public static Arrow arrowTo(double x, double y) {
        return GUI.base().add(new Arrow(new Point2D.Double(x, y)));
    }

    public static Point point() {
        return GUI.base().add(new Point());
    }

    public static Point point(double x, double y) {
        goTo(x, y);
        return GUI.base().add(new Point());
    }

    public static Circle circle(double radius) {
        return GUI.base().add(new Circle(radius));
    }

    public static Image image(String path) {
        return GUI.base().add(new Image(path));
    }

    public static Ellipse ellipse(double xradius, double yradius) {
        return GUI.base().add(new Ellipse(xradius, yradius));
    }

    public static void setLineWidth(int lineWidth) {
        invoke("setLineWidth", lineWidth);
    }

    public static void setLabel(String label) {
        invoke("setLabel", label);
    }

    public static void flipH() {
        invoke("flipH");
    }

    public static void flipV() {
        invoke("flipV");
    }

    public static void flipVH() {
        invoke("flipVH");
    }

    public static void setFlipH(boolean value) {
        invoke("setFlipH", value);
    }

    public static void setFlipV(boolean value) {
        invoke("setFlipV", value);
    }

    public static void setFlipVH(boolean value) {
        invoke("setFlipVH", value);
    }

    public static void setSize(double w, double h) {
        setWidth(w);
        setHeight(h);
    }

    public static void setWidth(double value) {
        invoke("setWidth", value);
    }

    public static void setHeight(double value) {
        invoke("setHeight", value);
    }

    public static void setLineStyle(int lineStyle) {
        invoke("setLineStyle", lineStyle);
    }

    public static void setLineStyle(String lineStyle) {
        invoke("setLineStyle", lineStyle);
    }

    public static void setLineStyle(LineStyle lineStyle) {
        invoke("setLineStyle", lineStyle);
    }

    public static void setLineStyleDashed() {
        setLineStyle(LineStyle.DASHED);
    }

    public static void setLineStyleNormal() {
        setLineStyle(LineStyle.NORMAL);
    }

    public static void setBackgroundColor(Paint color) {
        invoke("setBackgroundColor", color);
    }

    public static void setPointColor(Color color) {
        invoke("setPointColor", color);
    }

    public static void setPointStyle(PointStyle pointStyle) {
        invoke("setPointStyle", pointStyle);
    }

    public static void setPointStyle(String pointStyle) {
        invoke("setPointStyle", pointStyle);
    }

    public static void setPointStyle(int pointStyle) {
        invoke("setPointStyle", pointStyle);
    }

    public static void setLineColor(Color color) {
        invoke("setLineColor", color);
    }

    public static void setTextColor(Color color) {
        invoke("setTextColor", color);
    }


    public static void fill(Color color) {
        setFill(true);
        setBackgroundColor(color);
    }

    public static void fill() {
        setFill(true);
    }

    public static void setFill(boolean fill) {
        invoke("setFill", fill);
    }

    public static void drawBorder(Color color) {
        drawBorder(true);
        setLineColor(color);
    }

    public static void drawBorder() {
        drawBorder(true);
    }

    public static void drawBorder(boolean drawBorder) {
        invoke("setDrawBorder", drawBorder);
    }

    public static void setFont(Font font) {
        invoke("setFont", font);
    }

    public static void setFont(String font) {
        invoke("setFont", font);
    }

    public static void setFontSize(double fontSize) {
        invoke("setFontSize", fontSize);
    }

    public static void align(Align align) {
        invoke("setAlign", align);
    }

    public static void println(Object message) {
        lib.println(message);
    }

    public static void print(Object message) {
        lib.print(message);
    }

    public static String readln(Object message) {
        return lib.readln(message);
    }

    public static String readln() {
        return lib.readln();
    }

    public static String read(Object message) {
        return lib.read(message);
    }

    public static void clearConsole(){
        GUI.base().console().clear();
    }

    public static String readString() {
        return readln();
    }

    public static int readInt() {
        return Integer.parseInt(readln());
    }

    public static long readLong() {
        return Long.parseLong(readln());
    }

    public static double readDouble() {
        return Double.parseDouble(readln());
    }

    public static boolean readBoolean() {
        return Boolean.parseBoolean(readln());
    }

    public static float readFloat() {
        return Float.parseFloat(readln());
    }

    public static void sleep(long millis) {
        GUI.base().sleep(millis);
    }

    public static void moveTo(double x, double y) {
        last().moveTo(x, y);
    }
    public static void moveTo(double x, double y, int seconds) {
        last().moveTo(x, y, seconds);
    }

    public static void moveToBy(double x, double y) {
        last().moveBy(x, y);
    }
    public static void moveToBy(double x, double y, int seconds) {
        last().moveBy(x, y, seconds);
    }

    public static void rotateTo(double toAngle, int seconds) {
        last().rotateTo(toAngle, seconds);
    }

    public static void rotateBy(double toAngle, int seconds) {
        last().rotateBy(toAngle, seconds);
    }

    public static void rotateTo(double angle) {
        last().rotateTo(angle);
    }

    public static void rotateBy(double angle) {
        last().rotateBy(angle);
    }

    public static void speedUp() {
        setSpeed(SpeedFunction.ACCELERATE);
    }

    public static void speedDown() {
        setSpeed(SpeedFunction.DECELERATE);
    }

    public static void speedUpAndDown() {
        setSpeed(SpeedFunction.ACCELERATE_DECELERATE);
    }

    public static void speedDownAndUp() {
        setSpeed(SpeedFunction.DECELERATE_ACCELERATE);
    }

    public static void setSpeed(SpeedFunction accelerationFunction) {
        GUI.base().setSpeed(accelerationFunction);
    }

    public static void linearCurve() {
        GUI.base().setCurve(CurveMode.LINEAR);
    }

    public static void quadCurve() {
        GUI.base().setCurve(CurveMode.QUAD);
    }

    public static boolean eq(Object a, Object b) {
        return CompareUtils.eq(a, b);
    }

    public static double random() {
        return rand.nextDouble();
    }

    public static int randomInt(int from, int to) {
        return rand.nextInt(from, to);
    }

    public static double randomDouble(double from, double to) {
        return rand.nextDouble(from, to);
    }

    public static float randomFloat(float from, float to) {
        return rand.nextFloat(from, to);
    }

    public static Color randomColor() {
        return ColorUtils.colors[randomInt(0, ColorUtils.colors.length)];
    }


    public static boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isBoolean(String value) {
        try {
            if (value != null) {
                switch (value.trim().toLowerCase()) {
                    case "true":
                    case "false":
                        return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean booleanOf(String value) {
        try {
            if (value != null) {
                switch (value.trim().toLowerCase()) {
                    case "true":
                        return true;
                    case "false":
                        return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public static int intOf(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static long longOf(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static double doubleOf(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static double floatOf(String value) {
        try {
            return Float.parseFloat(value);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static String[] split(String value) {
        return split(value, " \t\n\r\f,;", false);
    }

    public static String[] split(String value, String delimiters) {
        return split(value, delimiters, false);
    }

    public static String[] split(String value, String delimiters, boolean returnDelimiters) {
        java.util.List<String> all = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(value == null ? "" : value, delimiters, returnDelimiters);
        while (st.hasMoreTokens()) {
            all.add(st.nextToken());
        }
        return all.toArray(new String[0]);
    }

    public static void run(Runnable run) {
        if (run != null) {
            GUI.base().startThread(run);
        }
    }

    public static double sin(double x) {
        return Math.sin(x);
    }

    public static double log10(double x) {
        return Math.log10(x);
    }

    public static double log1p(double x) {
        return Math.log1p(x);
    }

    public static double ceil(double x) {
        return Math.ceil(x);
    }

    public static double floor(double x) {
        return Math.floor(x);
    }

    public static double round(double x) {
        return Math.round(x);
    }

    public static double log(double x) {
        return Math.log(x);
    }

    public static double cos(double x) {
        return Math.cos(x);
    }

    public static double tan(double x) {
        return Math.tan(x);
    }

    public static double cotan(double x) {
        return 1 / Math.tan(x);
    }

    public static double abs(double x) {
        return Math.abs(x);
    }

    public static double abs(float x) {
        return Math.abs(x);
    }

    public static int abs(int x) {
        return Math.abs(x);
    }

    public static long abs(long x) {
        return Math.abs(x);
    }

    public static double atan(double x) {
        return Math.atan(x);
    }

    public static double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    public static double acos(double x) {
        return Math.acos(x);
    }

    public static double asin(double x) {
        return Math.asin(x);
    }

    public static double sinc(double x) {
        return x == 0 ? 1 : Math.sin(x) / x;
    }

    public static double sqr(double x) {
        return x * x;
    }

    public static double sqrt(double x) {
        return Math.sqrt(x);
    }

    public static double min(double... all) {
        return ArrayUtils.min(all);
    }

    public static double max(double... all) {
        return ArrayUtils.max(all);
    }

    public static float min(float... all) {
        return ArrayUtils.min(all);
    }

    public static float max(float... all) {
        return ArrayUtils.max(all);
    }

    public static long min(long... all) {
        return ArrayUtils.min(all);
    }

    public static long max(long... all) {
        return ArrayUtils.max(all);
    }

    public static int min(int... all) {
        return ArrayUtils.min(all);
    }

    public static int max(int... all) {
        return ArrayUtils.max(all);
    }

    public static void hideGrid() {
        GUI.base().grid().setVisible(false);
        GUI.base().refresh();
    }

    public static void showGrid() {
        GUI.base().grid().setVisible(true);
        GUI.base().refresh();
    }

    public static void setGridInterval(double min, double max) {
        setGridIntervalX(min, max);
        setGridIntervalY(min, max);
    }

    public static void setGridInterval(double max) {
        max=Math.abs(max);
        setGridIntervalX(-max, max);
        setGridIntervalY(-max, max);
    }

    public static void setGridIntervalX(double minX, double maxX) {
        GUI.base().grid();
        globalProps().setGridIntervalX(minX, maxX);
        GUI.base().refresh();
    }

    public static void setGridIntervalY(double minY, double maxY) {
        GUI.base().grid();
        globalProps().setGridIntervalY(minY, maxY);
        GUI.base().refresh();
    }

    public static void setGridTic(double t) {
        setGridTicX(t);
        setGridTicY(t);
    }

    public static void setGridTicX(double x) {
        GUI.base().grid();
        invoke("setGridTicX", x);
    }

    public static void setGridTicY(double y) {
        GUI.base().grid();
        invoke("setGridTicY", y);
    }

    private static void invoke(String name, Object... args) {
        Optional<Runnable> t = GUI.base().runnable(name, args);
        if(t.isEmpty()){
            throw new IllegalArgumentException("method not found " + name);
        }
        t.get().run();
        GUI.base().refresh();
    }

    public static ItemGroup startGroup() {
        return GUI.base().add(new ItemGroup(true));
    }

    public static ItemGroup closeGroup() {
        return GUI.base().add(new ItemGroup(false));
    }

    public static DrawItemAny last() {
        DrawItemAny drawItemAny = GUI.base().lastItem();
        if(drawItemAny == null) {
            throw new IllegalArgumentException("missing element");
        }
        return drawItemAny;
    }

}
