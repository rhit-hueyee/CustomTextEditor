package gui;

import javax.swing.*;
import java.util.Locale;

public class Launcher {
    //public static final String RESOURCE_DIR ="src/main/resources";
    private static final String[] LANGUAGES ={"English", "Spanish", "Japanese"};//this is not sustainable
    private static final Locale[] LOCALES ={Locale.US, new Locale("es", "ES"), new Locale("ja", "JP")};
    public static void main(String[] args){
        //UIManager.put("OptionsPane.isYesLast", true); //no idea what this does lmao
        Locale locale = promptLanguage();
        Text.init(locale);
        JOptionPane.setDefaultLocale(locale);
        SwingUtilities.invokeLater(() -> {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                new App().launchMainUi();
        });
    }

    private static Locale promptLanguage() {
        int choice = JOptionPane.showOptionDialog(
                null,
                "Language:",
                "CTE",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                LANGUAGES,
                LANGUAGES[0]
        );
        if (choice == -1) {System.exit(0);}
        return LOCALES[choice];
    }

}
