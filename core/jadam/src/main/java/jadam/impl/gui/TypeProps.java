package jadam.impl.gui;

import java.util.HashMap;
import java.util.Map;

public class TypeProps implements Cloneable{
    private Map<String, ItemProps> values =new HashMap<>();
    public ItemProps get(String type) {
        return values.get(type);
    }
    public void put(String type,ItemProps v) {
        values.put(type,v);
    }

    public TypeProps copy(){
        TypeProps p=new TypeProps();
        for (Map.Entry<String, ItemProps> e : values.entrySet()) {
            p.values.put(e.getKey(),e.getValue().copy());
        }
        return p;
    }
}
