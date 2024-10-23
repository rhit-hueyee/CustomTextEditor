package controller;

import model.Note;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NoteManager {

    private final DefaultListModel<Note> noteListModel;
    private final StyledDocument doc;
    private final JTextPane textPane;

    public NoteManager(DefaultListModel<Note> noteListModel, StyledDocument doc, JTextPane textPane) {
        this.noteListModel = noteListModel;
        this.doc = doc;
        this.textPane = textPane;

        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateNoteOffsets(e.getOffset(), e.getLength(), true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateNoteOffsets(e.getOffset(), e.getLength(), false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text changes
            }
        });
    }

    // Add a new note (this can be called from anywhere, including when loading from XML)
    public void addNote(String selectedText, String noteText, int selectionStart, int selectionEnd, Color highlightColor) {
        SimpleAttributeSet highlightStyle = new SimpleAttributeSet();
        StyleConstants.setBackground(highlightStyle, highlightColor);

        try {
            doc.setCharacterAttributes(selectionStart, selectionEnd - selectionStart, highlightStyle, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        noteListModel.addElement(new Note(selectedText, noteText, selectionStart, selectionEnd, highlightColor));
        sortNotesByStartPosition();
    }

    // Re-apply an existing note (used for XML loading)
    public void addExistingNote(Note note) {
        SimpleAttributeSet highlightStyle = new SimpleAttributeSet();
        StyleConstants.setBackground(highlightStyle, note.getHighlightColor());

        try {
            doc.setCharacterAttributes(note.getStartOffset(), note.getEndOffset() - note.getStartOffset(), highlightStyle, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        noteListModel.addElement(note);
        sortNotesByStartPosition();
    }

    // Update offsets of notes when text is inserted or removed
    private void updateNoteOffsets(int changeOffset, int length, boolean isInsert) {
        for (int i = noteListModel.size() - 1; i >= 0; i--) {
            Note note = noteListModel.get(i);

            if (isInsert) {
                if (changeOffset <= note.getStartOffset()) {
                    note.setStartOffset(note.getStartOffset() + length);
                    note.setEndOffset(note.getEndOffset() + length);
                } else if (changeOffset > note.getStartOffset() && changeOffset <= note.getEndOffset()) {
                    note.setEndOffset(note.getEndOffset() + length);
                }
            } else {
                if (changeOffset < note.getStartOffset()) {
                    int shiftAmount = Math.min(length, note.getStartOffset() - changeOffset);
                    note.setStartOffset(note.getStartOffset() - shiftAmount);
                    note.setEndOffset(note.getEndOffset() - shiftAmount);
                } else if (changeOffset >= note.getStartOffset() && changeOffset < note.getEndOffset()) {
                    note.setEndOffset(note.getEndOffset() - length);
                    if (note.getEndOffset() <= note.getStartOffset()) {
                        noteListModel.remove(i);
                    }
                }
            }
        }
    }

    public void scrollToSelection(Note note) {
        try {
            Rectangle viewRect = textPane.modelToView(note.getStartOffset());
            if (viewRect != null) {
                textPane.scrollRectToVisible(viewRect);
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public Note findNoteByCursorPosition(int cursorPosition) {
        for (int i = 0; i < noteListModel.size(); i++) {
            Note note = noteListModel.get(i);
            if (cursorPosition >= note.getStartOffset() && cursorPosition <= note.getEndOffset()) {
                return note;
            }
        }
        return null;
    }

    public void sortNotesByStartPosition() {
        ArrayList<Note> notes = new ArrayList<>();
        for (int i = 0; i < noteListModel.size(); i++) {
            notes.add(noteListModel.get(i));
        }

        Collections.sort(notes, Comparator.comparingInt(Note::getStartOffset));

        noteListModel.clear();
        for (Note note : notes) {
            noteListModel.addElement(note);
        }
    }

    public void updateNote(Note note, int newStart, int newEnd, Color newColor) {
        doc.setCharacterAttributes(note.getStartOffset(), note.getEndOffset() - note.getStartOffset(), new SimpleAttributeSet(), true);

        note.setStartOffset(newStart);
        note.setEndOffset(newEnd);
        note.setHighlightColor(newColor);

        try {
            SimpleAttributeSet highlightStyle = new SimpleAttributeSet();
            StyleConstants.setBackground(highlightStyle, newColor);
            doc.setCharacterAttributes(newStart, newEnd - newStart, highlightStyle, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sortNotesByStartPosition();
    }
}
