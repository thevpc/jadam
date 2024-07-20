/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.tutorials;

import jadam.PointStyle;

import static jadam.lib.*;

/**
 * @author vpc
 */
public class ExampleGroup {
    public static void main(String[] args) {
        point(0, 0);
        setPointColor(red);
        setLabel("Point A");

//        startGroup();

        point(20, 0);
        setPointColor(darkGreen);
        setLabel("Point B");


        goTo(20, 20);
        circle(10);
        setLabel("My Circle");

        closeGroup();
        run(() -> {
            while (true) {
                rotateBy(1,1);
            }
        });
        moveTo(50, 50, 2);
        moveTo(-50, 0, 2);
        moveTo(-50, -50, 2);
    }
}
