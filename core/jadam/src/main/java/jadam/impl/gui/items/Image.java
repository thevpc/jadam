package jadam.impl.gui.items;

import jadam.impl.gui.DrawContext;
import jadam.impl.gui.ItemProps;
import jadam.impl.util.DrawContextUtils;
import jadam.impl.util.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Image extends DrawItemBoundedBase {
    private String path;
    private java.awt.Image javaImage;
    private boolean flipVertical = false;
    private boolean flipHorizontal = false;
    private double width = -1;
    private double height = -1;

    public Image(String path) {
        super("image");
        this.path = path;
        ArrayList<String> err = new ArrayList<>();
        URL url = resolveURL(path, err);
        if(url != null) {
            try {
                javaImage = ImageIO.read(url);
            } catch (IOException e) {
                err.add(e.toString());
            }
        }
        if(javaImage==null) {
            for (String s : err) {
                System.err.println(s);
            }
        }
    }

    private URL resolveURL(String path, java.util.List<String> errors) {
        try {
            File input = new File(path).getAbsoluteFile();
            if (input.isFile()) {
                return input.toURI().toURL();
            }
            errors.add("Unable to load image file: " + input);
        } catch (IOException e) {
            errors.add("Unable to load image file: " + e.toString());
        }
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL resource = cl.getResource(path);
        if (resource != null) {
            return resource;
        }
        errors.add("Unable to load image resource: " + path);
        if(path.startsWith("/")){
            resource = cl.getResource(path.substring(1));
            if (resource != null) {
                return resource;
            }
            errors.add("Unable to load image resource: " + path.substring(1));
        }
        if(!new File(path).isAbsolute()){
            File p = new File(System.getProperty("user.home"), path);
            if(p.isFile()){
                try {
                    return p.toURI().toURL();
                } catch (MalformedURLException e) {
                    errors.add("Unable to load image file: " + p);
                }
            }
            errors.add("Unable to load image file: " + p);
        }
        return null;
    }

    @Override
    public Dimension preferredSize(DrawContext drawContext, Dimension d) {
        if(javaImage==null){
            return new Dimension(0,0);
        }
        BufferedImage bufferedImage = validImage();
        return new Dimension(
                bufferedImage.getWidth(null),
                bufferedImage.getHeight(null)
        );
    }

    @Override
    public Runnable run(String name, Object... args) {
        Runnable runnable = pen.runnable(name, args);
        if (runnable != null) {
            return runnable;
        }
        switch (name) {
            case "flipV": {
                return () -> this.flipVertical = !this.flipVertical;
            }
            case "flipH": {
                return () -> this.flipHorizontal = !this.flipHorizontal;
            }
            case "flipVH": {
                return () -> {
                    this.flipHorizontal = !this.flipHorizontal;

                    this.flipVertical = !this.flipVertical;
                };
            }
            case "setFlipV": {
                return () -> this.flipVertical = (Boolean) args[0];
            }
            case "setFlipH": {
                return () -> this.flipHorizontal = (Boolean) args[0];
            }
            case "setFlipVH": {
                return () -> {
                    this.flipVertical = (Boolean) args[0];
                    this.flipHorizontal = (Boolean) args[0];
                };
            }
            case "setWidth": {
                return () -> this.width = ((Number) args[0]).doubleValue();
            }
            case "setHeight": {
                return () -> this.height = ((Number) args[0]).doubleValue();
            }
        }
        return runnable;
    }


    private BufferedImage validImage() {
        BufferedImage image = ImageUtils.bufferedImage(javaImage);
        if (flipHorizontal && flipVertical) {
            image = ImageUtils.flipVH(image);
        } else if (flipHorizontal) {
            image = ImageUtils.flipH(image);
        } else if (flipVertical) {
            image = ImageUtils.flipV(image);
        }
        if (width != -1 || height != -1) {
            image = ImageUtils.resizeImage(image,
                    (int) (width < 0 ? image.getWidth(null) : width),
                    (int) (height < 0 ? image.getHeight(null) : height)
            );
        }
        return image;
    }

    @Override
    protected void drawImpl(DrawContext drawContext, Rectangle2D bounds) {
        ItemProps a = getAttrs();
        Graphics2D g = drawContext.graphics();
        boolean drawLine = a.isDrawBorder();
        boolean fill = a.isFill();

        if (false) {
            if (!drawLine && !fill) {
                drawLine = true;
            }
        }
        int x = (int) bounds.getX();
        int y = (int) bounds.getY();
        int w = (int) bounds.getWidth();
        int h = (int) bounds.getHeight();

        if (fill) {
            DrawContextUtils.fillBackground(getAttrs(), drawContext, c -> c.graphics().fillRect(x, y, w, h));
        }
        if(javaImage!=null) {
            g.drawImage(validImage(), x, y, drawContext.imageObserver());
        }
        if (drawLine) {
            DrawContextUtils.drawBorder(getAttrs(), drawContext, c -> c.graphics().drawRect(x, y, w, h));
        }
        DrawContextUtils.drawLabel(getAttrs(), a.getLabel(), (int) bounds.getCenterX(), (int) bounds.getCenterY(), a.getLineColor(), drawContext);
    }

}
