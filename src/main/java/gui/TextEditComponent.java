package gui;

import javax.swing.*;

public class TextEditComponent extends JPanel {


    public TextEditComponent(){
        super();
        /* Text Editing Menu ->
        This is where cut, copy, paste, redo, undo, find, replace, zoom in, zoom out, reset zoom,
        show all characters, toggle line wrap, toggle line number, toggle indent display, ece tera live
         */
        JMenuBar textEditMenuBar = new JMenuBar();
        JMenu textEditMenu = new JMenu();
        JMenuItem menuItemFile = new JMenuItem("File");
        textEditMenu.add(menuItemFile);
        textEditMenuBar.add(textEditMenu);
        this.add(textEditMenuBar);
        /* menuItemFile.addActionListener(this);
        this panel needs to implement ActionListener but not sure if that is best practice.
         */

        //This is where the tabs per each file/block (unsure which I'll choose yet) go

        /*Text Editor ->
        this will probably have to be a class that extends JTextComponent, for now, I'm doing a JTextArea.
        This is where the user define blocks
         */
        JTextArea textArea = new JTextArea("balls", super.getWidth(), super.getHeight());
        this.add(textArea);
    }

}
