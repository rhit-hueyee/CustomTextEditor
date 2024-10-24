package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class NoteTest {

    private Note note;
    private final String selectedText = "Test selected text";
    private String noteText = "Test note text";
    private int startOffset = 0;
    private int endOffset = 10;
    private Color highlightColor = Color.YELLOW;

    @BeforeEach
    void setUp() {
        note = new Note(selectedText, noteText, startOffset, endOffset, highlightColor);
    }

    @Test
    void testGetSelectedText() {
        assertEquals(selectedText, note.getSelectedText(), "Selected text should match");
    }

    @Test
    void testGetNote() {
        assertEquals(noteText, note.getNote(), "Note text should match the initialized value");
    }

    @Test
    void testSetNote() {
        String newNoteText = "Updated note text";
        note.setNote(newNoteText);
        assertEquals(newNoteText, note.getNote(), "Note text should update correctly");
    }

    @Test
    void testGetStartOffset() {
        assertEquals(startOffset, note.getStartOffset(), "Start offset should match the initialized value");
    }

    @Test
    void testSetStartOffset() {
        int newStartOffset = 5;
        note.setStartOffset(newStartOffset);
        assertEquals(newStartOffset, note.getStartOffset(), "Start offset should update correctly");
    }

    @Test
    void testGetEndOffset() {
        assertEquals(endOffset, note.getEndOffset(), "End offset should match the initialized value");
    }

    @Test
    void testSetEndOffset() {
        int newEndOffset = 15;
        note.setEndOffset(newEndOffset);
        assertEquals(newEndOffset, note.getEndOffset(), "End offset should update correctly");
    }

    @Test
    void testGetHighlightColor() {
        assertEquals(highlightColor, note.getHighlightColor(), "Highlight color should match the initialized value");
    }

    @Test
    void testSetHighlightColor() {
        Color newColor = Color.RED;
        note.setHighlightColor(newColor);
        assertEquals(newColor, note.getHighlightColor(), "Highlight color should update correctly");
    }

    @Test
    void testToString() {
        assertEquals(noteText, note.toString(), "toString should return the note text");
    }
}
