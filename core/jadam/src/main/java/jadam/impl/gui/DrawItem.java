/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package jadam.impl.gui;


/**
 * @author vpc
 */
public interface DrawItem {

    String type();


    void draw(DrawContext drawContext);

    Runnable run(String name, Object... args);

    void tic(long tic);

    void onAdd(ItemBuildContext buildContext);
}
