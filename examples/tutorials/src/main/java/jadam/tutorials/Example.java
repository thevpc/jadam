/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.tutorials;
import java.awt.*;

import static jadam.lib.*;

/**
 * @author vpc
 */
public class Example {
    public static void main(String[] args) {
        drawGrid();
        for (Color color : colors) {
            println("Hello world");
            setTextColor(color);
        }

        //        String e = readln();
//        println("tu as écrit "+e);
//        moveTo(10,0);
//        lineTo(30,10);
//        setLineStyleDashed();

        point(5, 5);
        setLabel("this is a point");

        goTo(-30, -30);
        lineTo(40, 10);
        lineTo(-20, 90);
        lineTo(-50, 60);
        closePolygon();
        setLabel("This is a polygon");
        fill(yellow);
        drawBorder(red);

        goTo(-30, 30);
        arrowTo(-20, 60);
        setLineStyleDashed();
        setLineColor(pink);
        setLabel("look here");

        goTo(30, 30);
        circle(10);
        setLabel("This is a circle");

        plot(x -> sin(x * x / 100) * 10);
        setLabel("f(x)=sin(x)");
        setLineColor(darkOrange);

        plot(x -> log((x * x - 10) / 100) * 10 + 5);
        setLabel("f(x)=sin(x)");
        setLineColor(darkCyan);
        setLineStyleDashed();


        goTo(0, 0);
        image("image.png");
        setLabel("Example 1");


        run(() -> {
            while (true) {
                goTo(-50, -50);
                move(50, 50, 1);
                flipH();
                quadCurve();
                move(-50, 50, 1);
                flipV();
            }
        });
        run(() -> {
            while (true) {
                setAngle(0);
                rotate(0, 360, 30);
            }
        });

    }
}
