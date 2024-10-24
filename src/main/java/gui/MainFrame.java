package gui;

import controller.NoteManager;
import model.Note;
import model.Settings;

import javax.swing.*;
import java.awt.*;
//import java.awt.*;

public class MainFrame extends JFrame implements UIChangeListener {
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;

    private NoteManager sharedNoteManager;
    private JTextPane sharedTextPane;

    public MainFrame() {
        // Initialize shared JTextPane and NoteManager
        sharedTextPane = new JTextPane();
        DefaultListModel<Note> sharedNoteListModel = new DefaultListModel<>();
        sharedNoteManager = new NoteManager(sharedNoteListModel, sharedTextPane.getStyledDocument(), sharedTextPane);

        // Set logic
        Settings.addUIChangeListener(this);
        // Set title
        this.setTitle(Text.getTextFromLocale("Title"));
        // Set up CloseHandle
        this.setUpCloseHandler();
        // Set up components
        this.createComponents();
        // Display window
        this.displayWindow();
    }

    private void setUpCloseHandler() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createComponents() {
        // Create and pass shared instances of NoteManager and JTextPane to components

        // Menu
        this.add(new TextEditWrapper(sharedNoteManager, sharedTextPane), BorderLayout.NORTH);

        // Text Editor Component
        this.add(new TabbedPane(sharedNoteManager, sharedTextPane), BorderLayout.CENTER);
    }

    private void displayWindow() {
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void updateLangUI() {
        this.setTitle(Text.getTextFromLocale("Title"));
    }
}

