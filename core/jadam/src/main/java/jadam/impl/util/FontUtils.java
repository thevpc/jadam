package jadam.impl.util;

import javax.swing.*;
import java.awt.*;

public class FontUtils {
    public static Font resolveOrDefault(Object a) {
        if (a instanceof Font) {
            return (Font) a;
        }
        if (a instanceof Integer) {
            return new Font(Font.MONOSPACED, Font.PLAIN, (Integer) a);
        }
        if (a instanceof String) {
            return new Font((String) a, Font.PLAIN, 16);
        }
        Font font = new JLabel().getFont();
        if(font!=null){
            return font;
        }
        return new Font(Font.MONOSPACED, Font.PLAIN, 16);
    }

}
