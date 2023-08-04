package gui;

import javax.swing.*;
import java.awt.*;
//import java.awt.*;

public class MainFrame extends JFrame{
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;
    //public static final int PADDING = 10;
    //public static final Color BG_HIGHLIGHT = new Color(0, 0, 0, 0.2f);

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
    }

//    private void setUpGlass(){
//        this.setGlassPane(new JComponent() {
//            @Override
//            public void paintComponent(Graphics g) {
//                g.setColor(Color.BLACK);
//                g.fillRect(0, 0, this.getWidth(), this.getHeight());
//            }
//        });
//    }

    private void createComponents(){
        //These are all other classes for the most part
        //Menu
        //Text Editor Component
        this.add(new TabbedPane(), BorderLayout.CENTER);
        //Other Components
        //these also need to be added to the frame
    }




    private void displayWindow(){
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }




}
