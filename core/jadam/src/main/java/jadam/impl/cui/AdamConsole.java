/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.impl.cui;

import jadam.impl.AdamLib;

import java.util.Scanner;

/**
 *
 * @author vpc
 */
public class AdamConsole implements AdamLib {

    private Scanner scanner = new Scanner(System.in);

    public String readln(Object message) {
        if (message != null) {
            System.out.print(message);
        }
        return scanner.nextLine();
    }

    @Override
    public String readln() {
        return scanner.nextLine();
    }

    @Override
    public String read(Object message) {
        if (message != null) {
            System.out.print(message);
        }
        return scanner.nextLine();
    }

    @Override
    public void print(Object message) {
        System.out.print(message);
    }

    @Override
    public void println(Object message) {
        System.out.println(message);
    }

}
