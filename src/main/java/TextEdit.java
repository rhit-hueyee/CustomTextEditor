import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

// Main window
public final class TextEdit extends JFrame implements ActionListener {
    private JTextArea area;
    static final int FRAME_WIDTH = 640;
    static final int FRAME_HEIGHT = 480;

    public TextEdit() {
        run();
    }

    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TextEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        area = new JTextArea();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(area);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);

        buildMenu();
    }

    private void buildMenu() {
        JMenuBar mainMenu = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuitemNew = new JMenuItem("New");
        JMenuItem menuitemOpen = new JMenuItem("Open");
        JMenuItem menuitemSave = new JMenuItem("Save");
        JMenuItem menuitemQuit = new JMenuItem("Quit");
        menuitemNew.addActionListener(this);
        menuitemOpen.addActionListener(this);
        menuitemSave.addActionListener(this);
        menuitemQuit.addActionListener(this);
        menuFile.add(menuitemNew);
        menuFile.add(menuitemOpen);
        menuFile.add(menuitemSave);
        menuFile.add(menuitemQuit);
        mainMenu.add(menuFile);
        setJMenuBar(mainMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuffer ingest = new StringBuffer();
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose Destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        String ae = e.getActionCommand();
        if (ae.equals("Open")) {
            handleOpen(ingest, jfc);
        } else if (ae.equals("Save")) {
            handleSave(jfc);
        } else if (ae.equals("New")) {
            handleNew();
        } else if (ae.equals("Quit")) {
            handleQuit();
        }
    }

    private void handleOpen(StringBuffer ingest, JFileChooser jfc) {
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            try (FileReader read = new FileReader(f, StandardCharsets.UTF_8);
                 Scanner scan = new Scanner(read)) {
                while (scan.hasNextLine()) {
                    String line = scan.nextLine() + "\n";
                    ingest.append(line);
                }
                area.setText(ingest.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleSave(JFileChooser jfc) {
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            try (FileWriter out = new FileWriter(f, StandardCharsets.UTF_8)) {
                out.write(area.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleNew() {
        area.setText("New");
    }

    private void handleQuit() {
        dispose();
    }
}
