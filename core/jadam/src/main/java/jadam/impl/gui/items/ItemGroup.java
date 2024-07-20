package jadam.impl.gui.items;

import jadam.impl.gui.*;
import jadam.impl.util.DrawContextUtils;
import jadam.impl.util.GeomUtils;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ItemGroup extends AbstractDrawItem {
    java.util.List<DrawItem> items = new ArrayList<>();
    private static enum Status{
        BEGIN,
        END,
    }
    private Status status;
    public ItemGroup(boolean start) {
        super("group");
        status=start? Status.BEGIN: Status.END;
    }

    @Override
    public Rectangle2D modelBounds() {
        Rectangle2D r=null;
        for (DrawItem item : items) {
            r=GeomUtils.union(r,((AbstractDrawItem)item).modelBounds());
        }
        return r;
    }

    @Override
    public Rectangle2D bounds(DrawContext drawContext) {
        Rectangle2D r=null;
        for (DrawItem item : items) {
            r=GeomUtils.union(r,((AbstractDrawItem)item).bounds(drawContext));
        }
        return r;
    }

    @Override
    public void drawImpl(DrawContext drawContext) {
        ItemProps a = getProperties();
        boolean drawLine = a.isDrawBorder();
        boolean fill = a.isFill();
        Rectangle2D bounds = bounds(drawContext);
        if (fill) {
            DrawContextUtils.fillBackground(getProperties(), drawContext, c -> c.graphics().fillRect(
                    (int)bounds.getMinX(),
                    (int)bounds.getMinY(),
                    (int)bounds.getWidth(),
                    (int)bounds.getHeight()
            ));
        }
        for (DrawItem item : items) {
            item.draw(drawContext);
        }
        if (drawLine) {
            DrawContextUtils.drawLine(getProperties(), drawContext, c -> c.graphics().drawRect(
                    (int)bounds.getMinX(),
                    (int)bounds.getMinY(),
                    (int)bounds.getWidth(),
                    (int)bounds.getHeight()
            ));
        }
        DrawContextUtils.drawLabel(getProperties(), a.getLabel(), (int) bounds.getCenterX(), (int) bounds.getCenterY(), a.getLineColor(), drawContext);
    }

    @Override
    public void onAdd(ItemBuildContext buildContext) {
        super.onAdd(buildContext);
        switch (status){
            case END -> {
                DrawItem[] items = buildContext.items();
                java.util.List<DrawItem> items2 = new ArrayList<>();
                ItemGroup bg=null;
                int toRemove=0;
                for (int i = items.length - 2; i >= 0; i--) {
                    if (items[i] instanceof ItemGroup && ((ItemGroup) items[i]).status==Status.BEGIN) {
                        bg=(ItemGroup) items[i];
                        toRemove++;
                        break;
                    }else{
                        toRemove++;
                        items2.add(0,items[i]);
                    }
                }
                if (items2.size()<=1) {
                    buildContext.replaceHead(toRemove, null);
                }else{
                    this.items=new ArrayList<>(items2);
                    if(bg!=null){
                        props=bg.props;
                    }
                    Rectangle2D mb = modelBounds();
                    props.setPosition(mb.getMinX(),mb.getMinY());
                    buildContext.replaceHead(toRemove + 1, this);
                }
            }
            case BEGIN -> {
                // do nothing...
            }
        }
    }

    @Override
    public void moveTo(double x, double y) {
        Point2D p0 = getPosition();
        double dx = x-p0.getX();
        double dy = y-p0.getY();
        if(dx!=0 && dy!=0){
            for (DrawItem item : items) {
                ((DrawItemAny)item).moveBy(dx, dy);
            }
            Rectangle2D mb = modelBounds();
            props.setPosition(mb.getMinX(),mb.getMinY());
        }
    }
}
