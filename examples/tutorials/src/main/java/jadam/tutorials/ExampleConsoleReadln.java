/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.tutorials;

import static jadam.lib.*;

public class ExampleConsoleReadln {
    public static void main(String[] args) {
        while(true) {
            println("What is your name?");
            String name = readln();
            println("Hello " + name + ". Nice to meet you!");
        }
    }
}
