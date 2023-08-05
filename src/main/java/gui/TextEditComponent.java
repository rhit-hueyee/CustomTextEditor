package gui;

import javax.swing.*;
import java.awt.*;

public class TextEditComponent extends JPanel {
    public TextEditComponent(String text){
        super();
        //Text Editing Menu
        //Text Editor -> this will probably have to be a class that extends JTextComponent, for now, I'm doing a JTextArea
        JMenu menu = new JMenu();
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Item 1");
        menu.add(menuItem);

        this.add(menu);
        JTextArea textArea = new JTextArea("balls", 100, 100);
        this.add(textArea);


//        JLabel filler = new JLabel(text);
//        filler.setHorizontalAlignment(JLabel.CENTER);
//        this.setLayout(new GridLayout(1, 1));
//        this.add(filler);
    }

}
