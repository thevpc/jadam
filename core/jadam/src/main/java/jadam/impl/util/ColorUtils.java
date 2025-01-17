package jadam.impl.util;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    public static final Color lightBrown = new Color(0x9a7b4f);
    public static final Color brown = new Color(0x3b1e08);
    public static final Color darkBrown = new Color(0x231709);

    public static final Color lightGold = new Color(0xffcf40);
    public static final Color gold = new Color(0xffd308);
    public static final Color darkGold = new Color(0xbf9b30);

    public static final Color lightViolet = new Color(0xef83ef);
    public static final Color violet = new Color(0x9308ff);
    public static final Color darkViolet = new Color(0x361668);

    public static final Color white = Color.white;
    private static boolean loadedColors=false;
    private static Map<String,Color> cachedColors=new HashMap<>();

    public static final Color[] colors = {
            black,
            blue,
            red,
            yellow,
            green,
            cyan,
            magenta,
            orange,
            gold,
            brown,
            pink,
            violet,
            gray,
            lightGray,
            lightBlue,
            lightRed,
            lightYellow,
            lightGold,
            lightGreen,
            lightCyan,
            lightMagenta,
            lightOrange,
            lightBrown,
            lightViolet,
            lightPink,
            darkBlue,
            darkRed,
            darkOrange,
            darkGold,
            darkYellow,
            darkGreen,
            darkCyan,
            darkMagenta,
            darkBrown,
            darkViolet,
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
                    Color a = _createColor(s);
                    if(a!=null){
                        return a;
                    }
                    s=_colorName(s);
                    if(s!=null) {
                        if (!loadedColors) {
                            Properties p = new Properties();
                            try (InputStream is = ColorUtils.class.getResourceAsStream("colors.properties")) {
                                p.load(is);
                            } catch (Exception ex) {
                                throw new IllegalArgumentException(ex);
                            }
                            for (Map.Entry<Object, Object> e : p.entrySet()) {
                                String k = _colorName(((String) e.getKey()).trim());
                                if(k!=null) {
                                    if(!cachedColors.containsKey(k)) {
                                        Color v = _createColor((String) e.getValue());
                                        if (v != null) {
                                            cachedColors.put(k, v);
                                        }
                                    }
                                }
                            }
                            loadedColors = true;
                        }
                        Color cc = cachedColors.get(s);
                        if(cc!=null){
                            return cc;
                        }
                    }
                }
            }
        }
        return defaultColor;
    }

    private static String _colorName(String s){
        if(s==null){
            return null;
        }
        s=s.replace(" ","").toLowerCase().trim();
        if(s.length()>0){
            return s;
        }
        return null;
    }

    private static Color _createColor(String s){
        if(s!=null) {
            if (s.startsWith("#")) {
                try {
                    return new Color(Integer.parseInt(s.substring(1), 16));
                } catch (Exception ex) {
                    return null;
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
        return null;
    }
}
