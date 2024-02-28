package gui;

import model.LocaleManager;
import model.Settings;

import javax.swing.*;
import java.util.Locale;

public class Launcher {
    //public static final String RESOURCE_DIR ="src/main/resources";
    private static LocaleManager localeManager;
    public static void main(String[] args){
        //UIManager.put("OptionsPane.isYesLast", true); //no idea what this does lmao
        Settings settings = Settings.getInstance();
        localeManager = LocaleManager.getInstance();
        Locale locale = promptLanguage();
        settings.setLocale(locale);
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
                localeManager.getLanguages(),
                localeManager.getLanguages()[0]
        );
        if (choice == -1) {System.exit(0);}
        return  localeManager.getLocale(localeManager.getLanguages()[choice]);
    }

}
