package gui;

import javax.swing.*;
import java.awt.*;

public class TextEditComponent extends JPanel {
    public TextEditComponent(String text){
        super(true);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        this.setLayout(new GridLayout(1, 1));
        this.add(filler);
    }

}
