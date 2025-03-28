/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.tutorials;

import static jadam.lib.*;

public class Demo {
    public static void main(String[] args) {
        showGrid();
        goTo(-30, -30);
        lineTo(40, 10);
        lineTo(-20, 90);
        lineTo(-50, 60);
        closePolygon();
        setLabel("This is a polygon");
        fill(yellow);
        drawBorder(red);
        while(true) {
            println("What is your name?");
            String name = readln();
            if(eq("clear",name)){
                clearConsole();
            }else {
                println("Hello " + name + ". Nice to meet you!");
            }
        }
    }
}
