/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.tutorials;

import static jadam.lib.*;

/**
 * @author vpc
 */
public class ExampleGrid {
    public static void main(String[] args) {
        setGridIntervalX(-100,100);
        setGridIntervalY(-100,50);
        drawGrid();
        point(20,20);
        setLabel("(20,20)");

        sleep(2000);
        setGridInterval(-50, 50);

        sleep(2000);
        hideGrid();

        sleep(2000);
        showGrid();

        sleep(2000);
        setGridInterval(-10, 10);
        setGridTic(2);
    }
}
