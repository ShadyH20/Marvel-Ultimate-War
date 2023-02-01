package view.game_scene;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class Square extends JPanel {

    private JProgressBar bar;
    private ImageIcon icon;
    private  JLabel label = new JLabel();
    private int i;
    private int j;

    public Square(LayoutManager layout) {
        super(layout);
    }

    public JProgressBar getBar() {
        return bar;
    }

    public void setBar(JProgressBar bar) {
        this.bar = bar;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        label.setIcon(icon);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public JLabel getLabel() {
        return label;
    }
}
