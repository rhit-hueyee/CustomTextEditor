package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame{
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;
    public static final int PADDING = 10;
    public static final Color BG_HIGHLIGHT = new Color(0, 0, 0, 0.2f);

    public MainFrame() {
        //set logic
        //set title
        this.setTitle("CTE");
        //set up CloseHandle
        this.setUpCloseHandler();
        //glass - what is glass?
        //this.setUpGlass();
        //set up components
        this.createComponents();
        //display window
        this.displayWindow();
    }

    private void setUpCloseHandler(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //TODO: Implement a prompt that asks whether you want to save your work or not.
    }

    private void setUpGlass(){
        this.setGlassPane(new JComponent() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        });
    }

    private void createComponents(){
        //These are all other classes for the most part
    }




    private void displayWindow(){
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }




}
