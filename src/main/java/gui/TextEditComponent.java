package gui;

import javax.swing.*;
import java.awt.*;

public class TextEditComponent extends JPanel {


    public TextEditComponent(){
        super(new BorderLayout());
        /* Text Editing Menu ->
        This is where cut, copy, paste, redo, undo, find, replace, zoom in, zoom out, reset zoom,
        show all characters, toggle line wrap, toggle line number, toggle indent display, etcetera live
        This will be done with a JToolBar
         */
        JToolBar toolbar = new JToolBar("Tools");
        addToolBarButtons(toolbar);
        //set preferred size
        //add toolbar to layout
        //add scroll pane? (not sure if needed)

        JMenuBar textEditMenuBar = new JMenuBar();
        JMenu textEditMenu = new JMenu("Edit");
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

    protected void addToolBarButtons(JToolBar toolbar){


        //create first button
        JButton button = makeToolbarButton();

        toolbar.add(button);
    }

    protected JButton makeToolbarButton() {
       return new JButton();
    }




}
