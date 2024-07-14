package jadam.impl.gui;

public interface ItemBuildContext {
    GlobalProps globalProps();
    ItemProps typeProps();
    DrawItem[] items();
    DrawItem peek(int pos);
    void replaceHead(int count,DrawItem a);

    void sleep(long millis);

    void refresh();
}
