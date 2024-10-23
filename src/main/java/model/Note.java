package model;

import java.awt.*;

public class Note {
    private final String selectedText;
    private String noteText;
    private int startOffset;
    private int endOffset;
    private Color highlightColor;

    public Note(String selectedText, String noteText, int startOffset, int endOffset, Color highlightColor) {
        this.selectedText = selectedText;
        this.noteText = noteText;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.highlightColor = highlightColor;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public String getNote() {
        return noteText;
    }

    public void setNote(String noteText) {
        this.noteText = noteText;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }

    // Override toString to display the note text
    @Override
    public String toString() {
        return noteText; // Display the note text in the JList
    }
}
