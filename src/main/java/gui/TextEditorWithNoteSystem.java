package gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import org.w3c.dom.NodeList;


import controller.NoteManager;
import model.Note;

public class TextEditorWithNoteSystem extends JPanel {
    private final JTextPane textPane;
    private final DefaultListModel<Note> noteListModel;
    private final NoteManager noteManager;

    public TextEditorWithNoteSystem() {
        super(new BorderLayout());

        // Initialize components and layout (same as before)
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);

        // Toolbar and other UI components
        JToolBar toolbar = new JToolBar();
        JButton addNoteButton = new JButton("Add Note");
        toolbar.add(addNoteButton);

        this.add(toolbar, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        // Note manager setup
        noteListModel = new DefaultListModel<>();
        noteManager = new NoteManager(noteListModel, textPane.getStyledDocument(), textPane);
        JList<Note> noteList = new JList<>(noteListModel);
        JScrollPane noteScrollPane = new JScrollPane(noteList);
        this.add(noteScrollPane, BorderLayout.EAST);

        // Add ActionListener for the Add Note button
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

        // Save and Load buttons for XML
        JButton saveButton = new JButton("Save as XML");
        JButton loadButton = new JButton("Load from XML");

        toolbar.add(saveButton);
        toolbar.add(loadButton);

        saveButton.addActionListener(e -> saveDocumentAsXML());
        loadButton.addActionListener(e -> loadDocumentFromXML());
    }

    // Method to save the document and notes as an XML file
    private void saveDocumentAsXML() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                // Create a new XML Document
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                // Root element
                Element rootElement = doc.createElement("document");
                doc.appendChild(rootElement);

                // Add the text content as an element
                Element textElement = doc.createElement("text");
                textElement.appendChild(doc.createTextNode(textPane.getText()));
                rootElement.appendChild(textElement);

                // Add the notes
                Element notesElement = doc.createElement("notes");
                rootElement.appendChild(notesElement);

                for (int i = 0; i < noteListModel.size(); i++) {
                    Note note = noteListModel.get(i);

                    Element noteElement = doc.createElement("note");

                    Element startElement = doc.createElement("startOffset");
                    startElement.appendChild(doc.createTextNode(String.valueOf(note.getStartOffset())));
                    noteElement.appendChild(startElement);

                    Element endElement = doc.createElement("endOffset");
                    endElement.appendChild(doc.createTextNode(String.valueOf(note.getEndOffset())));
                    noteElement.appendChild(endElement);

                    Element noteTextElement = doc.createElement("text");
                    noteTextElement.appendChild(doc.createTextNode(note.getNote()));
                    noteElement.appendChild(noteTextElement);

                    Element colorElement = doc.createElement("highlightColor");
                    colorElement.appendChild(doc.createTextNode(String.format("#%06X", (0xFFFFFF & note.getHighlightColor().getRGB()))));
                    noteElement.appendChild(colorElement);

                    notesElement.appendChild(noteElement);
                }

                // Write the content to the file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult resultFile = new StreamResult(file);
                transformer.transform(source, resultFile);

                JOptionPane.showMessageDialog(this, "Document saved as XML.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving document as XML.");
            }
        }
    }

    // Method to load the document and notes from an XML file
    private void loadDocumentFromXML() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                // Parse the XML file
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();

                // Clear current document content and notes
                textPane.setText("");
                noteListModel.clear();

                // Load the text content
                String textContent = doc.getElementsByTagName("text").item(0).getTextContent();
                textPane.setText(textContent);
                // Load the notes
                NodeList noteElements = doc.getElementsByTagName("note");
                for (int i = 0; i < noteElements.getLength(); i++) {
                    Element noteElement = (Element) noteElements.item(i);

                    int startOffset = Integer.parseInt(noteElement.getElementsByTagName("startOffset").item(0).getTextContent());
                    int endOffset = Integer.parseInt(noteElement.getElementsByTagName("endOffset").item(0).getTextContent());
                    String noteText = noteElement.getElementsByTagName("text").item(0).getTextContent();
                    String colorCode = noteElement.getElementsByTagName("highlightColor").item(0).getTextContent();
                    Color highlightColor = Color.decode(colorCode);

                    // Create and add the note
                    Note note = new Note(noteText, "", startOffset, endOffset, highlightColor);
                    noteListModel.addElement(note);
                    noteManager.addExistingNote(note);  // Reapply note highlights
                }

                JOptionPane.showMessageDialog(this, "Document loaded from XML.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading document from XML.");
            }
        }
    }
}
