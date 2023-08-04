package gui;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args){
        //UIManager.put("OptionsPane.isYesLast", true); //no idea what this does lmao
        SwingUtilities.invokeLater(() -> {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                new App().launchMainUi();
        });
    }

}
