package jadam.impl.util;

import java.awt.*;

public class ColorUtils {
    public static final Color black = Color.black;
    public static final Color blue = Color.blue;
    public static final Color red = Color.red;
    public static final Color yellow = Color.yellow;
    public static final Color green = Color.green;
    public static final Color cyan = Color.cyan;
    public static final Color magenta = Color.magenta;
    public static final Color orange = Color.orange;
    public static final Color pink = Color.pink;
    public static final Color gray = Color.gray;
    public static final Color lightGray = Color.lightGray;
    public static final Color lightBlue = Color.blue.brighter();
    public static final Color lightRed = Color.red.brighter();
    public static final Color lightYellow = Color.yellow.brighter();
    public static final Color lightGreen = Color.green.brighter();
    public static final Color lightCyan = Color.cyan.brighter();
    public static final Color lightMagenta = Color.magenta.brighter();
    public static final Color lightOrange = Color.orange.brighter();
    public static final Color lightPink = Color.pink.brighter();
    public static final Color darkBlue = new Color(0x00006d);
    public static final Color darkRed = new Color(0x950000);
    public static final Color darkOrange = new Color(0xbf7f00);
    public static final Color darkYellow = new Color(0xb18912);
    public static final Color darkGreen = new Color(0x005800);
    public static final Color darkCyan = new Color(0x007c7c);
    public static final Color darkMagenta = new Color(0x7c007c);
    public static final Color darkPink = new Color(0x946666);
    public static final Color darkGray = Color.darkGray;
    public static final Color white = Color.white;

    public static final Color[] colors = {
            black,
            blue,
            red,
            yellow,
            green,
            cyan,
            magenta,
            orange,
            pink,
            gray,
            lightGray,
            lightBlue,
            lightRed,
            lightYellow,
            lightGreen,
            lightCyan,
            lightMagenta,
            lightOrange,
            lightPink,
            darkBlue,
            darkRed,
            darkOrange,
            darkYellow,
            darkGreen,
            darkCyan,
            darkMagenta,
            darkPink,
            darkGray,
            white,
    };

    private static int cint(String s) {
        if (s == null) {
            return -1;
        }
        try {
            int i = Integer.parseInt(s);
            return i >= 0 && i <= 255 ? i : -1;
        } catch (Exception ex) {
            return -1;
        }
    }

    public static Paint resolvePaint(Object color) {
        if (color == null) {
            return black;
        }
        if (color instanceof Paint) {
            return (Paint) color;
        }
        return resolveColor(color);
    }

    public static Color resolveColor(Object color) {
        return resolveColorOrDefault(color, Color.BLACK);
    }

    public static Color resolveColorOrDefault(Object color, Color defaultColor) {
        if (color instanceof Color) {
            return (Color) color;
        }
        if (color instanceof Integer i) {
            return colors[Math.abs(i) % colors.length];
        }
        if (color instanceof String) {
            String s = color.toString().trim().toLowerCase();
            switch (s) {
                case "black":
                    return Color.black;
                case "blue":
                    return Color.blue;
                case "red":
                    return Color.red;
                case "yellow":
                    return Color.yellow;
                case "green":
                    return Color.green;
                case "cyan":
                    return Color.cyan;
                case "magenta":
                    return Color.magenta;
                case "orange":
                    return Color.orange;
                case "pink":
                    return Color.pink;
                case "gray":
                    return Color.gray;

                case "lightgray":
                    return Color.lightGray;
                case "lightblue":
                    return Color.blue.brighter();
                case "lightred":
                    return Color.red.brighter();
                case "lightyellow":
                    return Color.yellow.brighter();
                case "lightgreen":
                    return Color.green.brighter();
                case "lightcyan":
                    return Color.cyan.brighter();
                case "lightmagenta":
                    return Color.magenta.brighter();
                case "lightorange":
                    return Color.orange.brighter();
                case "lightpink":
                    return Color.pink.brighter();

                case "darkblue":
                    return Color.blue.darker();
                case "darkred":
                    return Color.red.darker();
                case "darkyellow":
                    return Color.yellow.darker();
                case "darkgray":
                    return Color.darkGray;
                case "darkgreen":
                    return Color.green.darker();
                case "darkcyan":
                    return Color.cyan.darker();
                case "darkmagenta":
                    return Color.magenta.darker();
                case "darkpink":
                    return Color.pink.darker();
                case "white":
                    return Color.white;
                default: {
                    if (s.startsWith("#")) {
                        try {
                            return new Color(Integer.parseInt(s.substring(1), 16));
                        } catch (Exception ex) {
                            return defaultColor;
                        }
                    }
                    if (s.indexOf(",") >= 0) {
                        String[] a = s.split(",");
                        if (a.length == 3 || a.length == 4) {
                            int r = cint(a[0]);
                            int g = cint(a[1]);
                            int b = cint(a[2]);
                            int aa = cint(a.length == 4 ? a[3] : null);
                            if (r >= 0 && g >= 0 && b >= 0) {
                                return
                                        new Color(
                                                r,
                                                g,
                                                b,
                                                aa < 0 ? 255 : aa
                                        )
                                        ;
                            }
                        }
                    }
                }
            }
        }
        return defaultColor;
    }
}
