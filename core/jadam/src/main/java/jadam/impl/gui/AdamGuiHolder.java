/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jadam.impl.gui;

import jadam.impl.AdamLib;

import javax.swing.*;
import java.awt.*;

/**
 * @author vpc
 */
public class AdamGuiHolder {

    JFrame currentFrame;
    AdamDrawComponent draw;
    AdamGUI lib= new AdamGUI(this);


    public AdamGUI lib() {
        return lib;
    }

    private boolean ensureFrame() {
        if (currentFrame == null) {
            currentFrame = new JFrame();
            currentFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    currentFrame.setTitle("JAdam Console");
                    draw = new AdamDrawComponent();
                    currentFrame.setContentPane(draw);
                    currentFrame.setSize(new Dimension(1024, 800));
                }
            });
            return true;
        }
        return false;
    }

    public void getFrame() {
        if (ensureFrame()) {
            currentFrame.setVisible(true);
        }
    }

    public static class AdamGUI implements AdamLib {
        private AdamGuiHolder a;

        public AdamGUI(AdamGuiHolder a) {
            this.a = a;
        }

        @Override
        public String readln() {
            a.getFrame();
            return a.draw.readln();
        }

        @Override
        public String readln(Object message) {
            a.getFrame();
            return a.draw.readln(message);
        }

        @Override
        public String read(Object message) {
            a.getFrame();
            return a.draw.read(message);
        }

        @Override
        public void println(Object message) {
            a.getFrame();
            a.draw.println(message);
        }

        @Override
        public void print(Object message) {
            a.getFrame();
            a.draw.printMessage(message);
        }

        public AdamDrawComponent base() {
            a.getFrame();
            return a.draw;
        }

    }
}
