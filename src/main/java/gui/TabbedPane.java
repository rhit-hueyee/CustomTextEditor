package gui;

import controller.NoteManager;
import model.Note;

import javax.swing.*;
import java.awt.*;

public class TabbedPane extends JPanel {
    public TabbedPane(NoteManager sharedNoteManager, JTextPane sharedTextPane) {
        super(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pass the shared note manager and text pane to each component
        JComponent panel1 = new TextEditComponent();  // Example: No note needed for this one
        tabbedPane.addTab("Tab 1", panel1);

        JComponent panel2 = new TestTextEditorComponent();  // Example: No note needed for this one
        tabbedPane.addTab("Tab 2", panel2);

        JComponent panel3 = new TextEditorWithNoteSystem(sharedNoteManager, sharedTextPane);  // Note-enabled editor
        tabbedPane.addTab("Text Editor + Notes", panel3);

        add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}

