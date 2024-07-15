/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.tutorials;

import static jadam.lib.*;

/**
 * @author vpc
 */
public class ExamplePlotFunction {
    public static void main(String[] args) {
        setGridInterval(10);
        setGridTic(1);
        drawGrid();
        plot(x->sin(x));
        setLineColor(red);
        plot(x->tan(x));
        setLineColor(blue);
    }
}
