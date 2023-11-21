package gui;

import javax.swing.*;
import java.awt.*;

public class TextEditWrapper extends JPanel {

    public TextEditWrapper(){
        super(new BorderLayout());

        //this is where file inputs can be found

        JMenuBar textEditMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu textEditMenu = new JMenu("Edit");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu settingsLanguage = new JMenu("Language");
        //JMenuItem menuItemFile = new JMenuItem("File");
        //textEditMenu.add(menuItemFile);
        settingsMenu.add(settingsLanguage);
        textEditMenuBar.add(fileMenu);
        textEditMenuBar.add(textEditMenu);
        textEditMenuBar.add(settingsMenu);
        this.add(textEditMenuBar, BorderLayout.CENTER);
        /* menuItemFile.addActionListener(this);
        this panel needs to implement ActionListener but not sure if that is best practice.
         */

        //TextEditComponent textEditComponent = new TextEditComponent();
        //this.add(textEditComponent, BorderLayout.CENTER);
    }



}
