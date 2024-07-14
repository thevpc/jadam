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
public class AdamGui implements AdamLib {

    JFrame currentFrame;
    AdamDrawComponent draw;

    @Override
    public String readln(String message) {
        getFrame();
        return draw.readln();
//        if (message == null) {
//            message = "";
//        }
//        return JOptionPane.showInputDialog(null, message);
    }

    public AdamDrawComponent base() {
        getFrame();
        return draw;
    }


    @Override
    public void println(String message) {
        getFrame();
        draw.println(message);
//        JOptionPane.showMessageDialog(null, message);
    }


    private boolean ensureFrame() {
        if (currentFrame == null) {
            currentFrame = new JFrame();
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
}
