package jadam;

public enum PointStyle {
    PLUS,
    CROSS,
    DOT;

    public static PointStyle resolveDefault(Object c){
        if(c instanceof PointStyle) {
            return ((PointStyle)c);
        }
        if(c instanceof String) {
            try {
                return PointStyle.valueOf(((String) c).toUpperCase());
            }catch (Exception ex){
                return PointStyle.PLUS;
            }
        }
        if(c instanceof Number) {
            return PointStyle.values()[
                    Math.abs(((Number) c).intValue())
                    % PointStyle.values().length
                    ];
        }
        return PLUS;
    }
}
