/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.tutorials;

import jadam.*;

import static jadam.lib.*;

/**
 * @author vpc
 */
public class ExamplePoint {
    public static void main(String[] args) {
        point(0, 0);
        setPointColor(red);
        setLabel("Point A");

        point(20, 0);
        setPointColor(darkGreen);
        setLabel("Point B");

        point(0, 20);
        setPointColor(darkBlue);
        setPointStyle(PointStyle.DOT);
        setLabel("Point C");

        point(20, 20);
        setPointColor(darkRed);
        setPointStyle(PointStyle.PLUS);
        setLabel("Point D");


    }
}
