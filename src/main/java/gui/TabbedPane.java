package gui;

import javax.swing.*;
import java.awt.*;

    public class TabbedPane extends JPanel {
        public TabbedPane(){
            super(new BorderLayout());
            /* for now, this tabbed pane is meant to act as the switch between each editor type
            So far there will be textEditor and blockEditor. If other editors and widgets need to be added then
            they will be added.
             */
            JTabbedPane tabbedPane = new JTabbedPane();
            //abstract away these components
            JComponent panel1 = new TextEditComponent();
            //but this line is important to adding to the list
            tabbedPane.addTab("Tab 1", panel1);
            JComponent panel2 = makeTextPanel("Panel #2");
            tabbedPane.addTab("Tab 2", panel2);
            JComponent panel3 = makeTextPanel("Panel #3");
            tabbedPane.addTab("Tab 2", panel3);
            add(tabbedPane);
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        }
        //abstract this into its own class
        protected JComponent makeTextPanel(String text) {
            JPanel panel = new JPanel(false);
            JLabel filler = new JLabel(text);
            filler.setHorizontalAlignment(JLabel.CENTER);
            panel.setLayout(new GridLayout(1, 1));
            panel.add(filler);
            return panel;
        }
    }
