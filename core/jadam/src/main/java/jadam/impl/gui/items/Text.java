package jadam.impl.gui.items;

import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;
import jadam.impl.util.DrawContextUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Text extends DrawItemBoundedBase {
    private String text;

    public Text(String text) {
        super("text");
        this.text = text == null ? "" : text;
    }

    @Override
    public Dimension preferredSize(DrawContext drawContext, Dimension d) {
        Graphics2D g = drawContext.graphics();
        g.setFont(getProperties().getFont());
        Rectangle2D b = g.getFontMetrics().getStringBounds(text, g);
        return new Dimension((int) b.getWidth(), (int) b.getHeight());
    }

    @Override
    protected void drawImpl(DrawContext drawContext, Rectangle2D r) {
        ItemProps a = getProperties();
        Graphics2D g = drawContext.graphics();
        boolean drawLine = a.isDrawBorder();
        boolean fill = a.isFill();

        int x = (int) r.getX();
        int y = (int) r.getY();
        int w = (int) r.getWidth();
        int h = (int) r.getHeight();

        if (false) {
            if (!drawLine && !fill) {
                drawLine = true;
            }
        }
        if (fill) {
            DrawContextUtils.fillBackground(getProperties(), drawContext, c -> c.graphics().fillRect(x, y, w, h));
        }

        g.setFont(a.getFont());
        g.drawString(text, x, y);

        if (drawLine) {
            DrawContextUtils.drawBorder(getProperties(), drawContext, c -> c.graphics().drawRect(x, y, w, h));
        }
    }

}
