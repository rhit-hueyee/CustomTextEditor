package gui;



import javax.swing.*;
import java.awt.*;

public class TestTextEditorComponent extends JPanel {

    public TestTextEditorComponent() {
        super(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setText("Text Editor\n\nLorem ipsum dolor sit amet,...");
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);

        // Create a JPanel to simulate the widgets
        JPanel widgetsPanel = new JPanel();
        widgetsPanel.setLayout(new BoxLayout(widgetsPanel, BoxLayout.Y_AXIS));
        widgetsPanel.add(createWidgetPanel("Widget 1: Lorem ipsum..."));
        widgetsPanel.add(createWidgetPanel("Widget 2: Lorem ipsum..."));
        widgetsPanel.add(createWidgetPanel("Widget 3: Lorem ipsum..."));
        JScrollPane widgetsScrollPane = new JScrollPane(widgetsPanel);

        // Create a JSplitPane to hold the text area and the widgets panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                textAreaScrollPane, widgetsScrollPane);
        splitPane.setDividerLocation(400);

        this.add(splitPane, BorderLayout.CENTER);

    }

    private static JPanel createWidgetPanel(String text) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(new JLabel(text));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75)); // set max size to control height
        return panel;
    }



}
