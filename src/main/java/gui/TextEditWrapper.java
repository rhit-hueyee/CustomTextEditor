package gui;

import controller.NoteManager;
import model.LocaleManager;
import model.Note;
import model.Settings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;

public class TextEditWrapper extends JPanel implements UIChangeListener {
    private final LocaleManager localeManager;
    private final Settings settings;
    private final JMenuBar textEditMenuBar;
    private final NoteManager sharedNoteManager;
    private final JTextPane sharedTextPane;

    public TextEditWrapper(NoteManager sharedNoteManager, JTextPane sharedTextPane) {
        super(new BorderLayout());
        this.sharedNoteManager = sharedNoteManager;
        this.sharedTextPane = sharedTextPane;

        settings = Settings.getInstance();
        localeManager = LocaleManager.getInstance();
        Settings.addUIChangeListener(this);
        this.textEditMenuBar = new JMenuBar();
        configureMenus();
    }

    private void configureMenus() {
        removeAllMenus(this.textEditMenuBar);

        JMenu fileMenu = new JMenu(Text.getTextFromLocale("GeneralMenuFile"));

        // Adding Save and Load options to the file menu
        JMenuItem saveMenuItem = new JMenuItem("Save as XML");
        JMenuItem loadMenuItem = new JMenuItem("Load from XML");

        // Save action
        saveMenuItem.addActionListener(e -> saveDocumentAsXML());
        // Load action
        loadMenuItem.addActionListener(e -> loadDocumentFromXML());

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);

        JMenu textEditMenu = new JMenu(Text.getTextFromLocale("GeneralMenuEdit"));
        JMenu settingsMenu = new JMenu(Text.getTextFromLocale("GeneralMenuSettings"));
        JMenu settingsLanguage = new JMenu(Text.getTextFromLocale("GeneralMenuLanguage"));
        configureLanguages(settingsLanguage);
        settingsMenu.add(settingsLanguage);

        this.textEditMenuBar.add(fileMenu);
        this.textEditMenuBar.add(textEditMenu);
        this.textEditMenuBar.add(settingsMenu);
        this.add(this.textEditMenuBar, BorderLayout.CENTER);
    }

    private void saveDocumentAsXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "CustomTextEditor files (*.cte)", "cte"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Ensure the file has the custom extension ".cte"
            if (!file.getName().toLowerCase().endsWith(".cte")) {
                file = new File(file.getAbsolutePath() + ".cte");
            }

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("document");
                doc.appendChild(rootElement);
                Element textElement = doc.createElement("text");
                textElement.appendChild(doc.createTextNode(sharedTextPane.getText()));
                rootElement.appendChild(textElement);

                Element notesElement = doc.createElement("notes");
                rootElement.appendChild(notesElement);

                for (int i = 0; i < sharedNoteManager.getNoteListModel().size(); i++) {
                    Note note = sharedNoteManager.getNoteListModel().get(i);

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
                    colorElement.appendChild(doc.createTextNode(String.format(
                            "#%06X", (0xFFFFFF & note.getHighlightColor().getRGB()))));
                    noteElement.appendChild(colorElement);

                    notesElement.appendChild(noteElement);
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult resultFile = new StreamResult(file);
                transformer.transform(source, resultFile);

                JOptionPane.showMessageDialog(this, "Document saved as .cte file.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving document as .cte file.");
            }
        }
    }


    private void loadDocumentFromXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser
                .FileNameExtensionFilter("CustomTextEditor files (*.cte)", "cte"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                // Parse the XML file (same as before)
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();

                // Clear current content and load the document (same as before)
                sharedTextPane.setText("");
                sharedNoteManager.getNoteListModel().clear();

                String textContent = doc.getElementsByTagName("text").item(0).getTextContent();
                sharedTextPane.setText(textContent);

                NodeList noteElements = doc.getElementsByTagName("note");
                for (int i = 0; i < noteElements.getLength(); i++) {
                    Element noteElement = (Element) noteElements.item(i);

                    int startOffset = Integer.parseInt(noteElement.getElementsByTagName("startOffset")
                            .item(0).getTextContent());
                    int endOffset = Integer.parseInt(noteElement.getElementsByTagName("endOffset")
                            .item(0).getTextContent());
                    String noteText = noteElement.getElementsByTagName("text").item(0).getTextContent();
                    String colorCode = noteElement.getElementsByTagName("highlightColor")
                            .item(0).getTextContent();
                    Color highlightColor = Color.decode(colorCode);

                    // Create and add the note (same as before)
                    Note note = new Note(noteText, "", startOffset, endOffset, highlightColor);
                    sharedNoteManager.getNoteListModel().addElement(note);
                    sharedNoteManager.addExistingNote(note);  // Reapply note highlights
                }

                JOptionPane.showMessageDialog(this, "Document loaded from .cte file.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading document from .cte file.");
            }
        }
    }


    private void configureLanguages(JMenu settingsLanguage) {
        localeManager.getLanguageMap().forEach((language, locale) -> {
            JMenuItem languageItem = new JMenuItem(language);
            languageItem.addActionListener(e -> {
                settings.setLocale(locale);
                settings.echoUpdateUI();
                this.revalidate();
                this.repaint();
            });
            settingsLanguage.add(languageItem);
            repaint();
        });
    }

    private void removeAllMenus(JMenuBar jMenuBar) {
        int count = jMenuBar.getMenuCount();
        for (int i = count - 1; i >= 0; i--) {
            jMenuBar.remove(i);
        }
    }

    @Override
    public void updateLangUI() {
        configureMenus();
    }
}

