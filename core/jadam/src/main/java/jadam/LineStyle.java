package jadam;

public enum LineStyle {
    NORMAL, DASHED;
    public static LineStyle resolveOrDefault(Object s){
        if(s == null){
            return NORMAL;
        }
        if(s instanceof LineStyle){
            return (LineStyle) s;
        }
        if(s instanceof String){
            return resolveOrDefault((String) s);
        }
        if(s instanceof Integer){
            return values()[Math.abs((int)s)%values().length];
        }
        return NORMAL;
    }

    public static LineStyle resolveOrDefault(String s){
        if(s==null){
            s="";
        }
        s=s.trim().toLowerCase().replace("-","").replace("_","");
        switch (s){
            case "normal":return NORMAL;
            case "dashed":return DASHED;
        }
        return NORMAL;
    }
}
