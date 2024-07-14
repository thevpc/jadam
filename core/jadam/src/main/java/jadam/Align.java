package jadam;

public enum Align {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
    TOP, BOTTOM, LEFT, RIGHT, CENTER;
    public static Align resolveOrDefault(Object s){
        if(s == null){
            return TOP_LEFT;
        }
        if(s instanceof Align){
            return (Align) s;
        }
        if(s instanceof String){
            return resolveOrDefault((String) s);
        }
        if(s instanceof Integer){
            return values()[Math.abs((int)s)%values().length];
        }
        return TOP_LEFT;
    }

    public static Align resolveOrDefault(String s){
        if(s==null){
            s="";
        }
        s=s.trim().toLowerCase().replace("-","").replace("_","");
        switch (s){
            case "top":return TOP;
            case "bottom":return BOTTOM;
            case "left":return LEFT;
            case "right":return RIGHT;
            case "center":return CENTER;
            case "topleft":return TOP_LEFT;
            case "topright":return TOP_RIGHT;
            case "bottomleft":return BOTTOM_LEFT;
            case "bottomright":return BOTTOM_RIGHT;
        }
        return TOP_LEFT;
    }
}
