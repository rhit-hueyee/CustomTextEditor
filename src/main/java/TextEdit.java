import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


//Main window
    public final class TextEdit extends JFrame implements ActionListener {
       private JTextArea area;
       private JFrame frame;
       private int returnValue = 0;

       public TextEdit() {run();}

       public void run() {
           frame = new JFrame("Text Edit");

           try {
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
           } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException ex) {
               Logger.getLogger(TextEdit.class.getName()).log(Level.SEVERE, null, ex);
           }

           area = new JTextArea();
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.add(area);
           frame.setSize(640, 480);
           frame.setVisible(true);

           //Build the Menu
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

           mainMenu.add(menuFile);

           menuFile.add(menuitemNew);
           menuFile.add(menuitemOpen);
           menuFile.add(menuitemSave);
           menuFile.add(menuitemQuit);

           frame.setJMenuBar(mainMenu);
       }

       @Override
        public void actionPerformed(ActionEvent e){
           StringBuffer ingest = new StringBuffer();
           JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
           jfc.setDialogTitle("Choose Destination.");
           jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

           String ae = e.getActionCommand();
           if (ae.equals("Open")){
               returnValue = jfc.showOpenDialog(null);
               if (returnValue == JFileChooser.APPROVE_OPTION) {
                   File f = new File(jfc.getSelectedFile().getAbsolutePath());
                   try {
                       FileReader read = new FileReader(f);
                       Scanner scan = new Scanner(read);
                       while(scan.hasNextLine()) {
                           String line = scan.nextLine() + "\n";
                           //ingest = ingest + line;
                           ingest.append(line);
                       }
                       area.setText(ingest.toString());
                       read.close();
                   } catch (IOException ex){
                       ex.printStackTrace();
                   }
               }
           } else if (ae.equals("Save")){
               returnValue = jfc.showSaveDialog(null);
               try {
                   File f = new File(jfc.getSelectedFile().getAbsolutePath());
                   FileWriter out = new FileWriter(f);
                   out.write(area.getText());
                   out.close();
               } catch (FileNotFoundException ex) {
                   Component f = null;
                   JOptionPane.showMessageDialog(f, "File not found.");
               } catch (IOException ex) {
                   Component f = null;
                   JOptionPane.showMessageDialog(f, "Error");
               }
           } else if (ae.equals("New")) {
               area.setText("New");
           }  else if (ae.equals("Quit")) {
               frame.dispose();
           }
       }


   }

