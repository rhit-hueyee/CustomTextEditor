package gui;

import javax.swing.*;
import java.awt.*;


import controller.NoteManager;
import model.Note;

public class TextEditorWithNoteSystem extends JPanel {
    private final JTextPane textPane;
    private final DefaultListModel<Note> noteListModel;
    private final NoteManager noteManager;

    public TextEditorWithNoteSystem(NoteManager sharedNoteManager, JTextPane sharedTextPane) {
        super(new BorderLayout());
        this.textPane = sharedTextPane;
        JScrollPane scrollPane = new JScrollPane(textPane);
        JToolBar toolbar = new JToolBar();
        JButton addNoteButton = new JButton("Add Note");
        toolbar.add(addNoteButton);
        this.add(toolbar, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        this.noteManager = sharedNoteManager;
        this.noteListModel = sharedNoteManager.getNoteListModel(); // This will now work
        JList<Note> noteList = new JList<>(noteListModel);
        JScrollPane noteScrollPane = new JScrollPane(noteList);
        this.add(noteScrollPane, BorderLayout.EAST);
        addNoteButton.addActionListener(e -> {
            String selectedText = textPane.getSelectedText();
            if (selectedText != null && !selectedText.isEmpty()) {
                int selectionStart = textPane.getSelectionStart();
                int selectionEnd = textPane.getSelectionEnd();
                String noteText = JOptionPane.showInputDialog(this, "Enter a note for the selected text:");
                if (noteText != null && !noteText.isEmpty()) {
                    noteManager.addNote(selectedText, noteText, selectionStart, selectionEnd, Color.YELLOW);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No text selected.");
            }
        });
    }
}


