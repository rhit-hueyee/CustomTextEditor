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
        this.add(toolbar, BorderLayout.NORTH);



        //This is where the tabs per each file/block (unsure which I'll choose yet) go

        /*Text Editor ->
        this will probably have to be a class that extends JTextComponent, for now, I'm doing a JTextArea.
        This is where the user define blocks
         */
        //JTextArea textArea = new JTextArea("balls", super.getHeight(), super.getWidth());
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        String content = "<html><body>" +
                "<table style='width:100%; height:100%;'>" +
                "<tr>" +
                "<td style='width: 50%; vertical-align: top;'>" +
                "<h1>Text Editor</h1>" +
                "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit...</p>" +
                "</td>" +
                "<td style='width: 50%; vertical-align: top;'>" +
                "<h2>Widgets</h2>" +
                "<div style='margin-bottom: 10px; padding: 10px; border: 1px solid black;'>Widget 1: Lorem ipsum...</div>" +
                "<div style='margin-bottom: 10px; padding: 10px; border: 1px solid black;'>Widget 2: Lorem ipsum...</div>" +
                "<div style='padding: 10px; border: 1px solid black;'>Widget 3: Lorem ipsum...</div>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</body></html>";

        editorPane.setText(content);
        JScrollPane scrollPane = new JScrollPane(editorPane);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private static final String[] BUTTON_NAMES = {
            "Cut", "Copy", "Paste", "Redo", "Undo",
            "Find", "Replace", "Zoom In", "Zoom Out",
            "Reset Zoom"//, "Show All Characters", "Toggle Line Wrap",
            //"Toggle Line Number", "Toggle Indent Display"
    };

    protected void addToolBarButtons(JToolBar toolbar){
        for (String name : BUTTON_NAMES) {
            JButton button = makeToolbarButton(name);
            toolbar.add(button);
        }
    }

    protected JButton makeToolbarButton(String name) {
        return new JButton(name);
    }


}
