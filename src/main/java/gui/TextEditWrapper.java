package gui;

import model.LocaleManager;
import model.Settings;

import javax.swing.*;
import java.awt.*;

public class TextEditWrapper extends JPanel implements UIChangeListener{
    private final LocaleManager localeManager;
    private final Settings settings;
    private final JMenuBar textEditMenuBar;


    public TextEditWrapper(){
        super(new BorderLayout());
        settings = Settings.getInstance();
        localeManager = LocaleManager.getInstance();
        Settings.addUIChangeListener(this);
        this.textEditMenuBar = new JMenuBar();
        configureMenus();

    }

    private void configureMenus() {
        removeAllMenus(this.textEditMenuBar);

        JMenu fileMenu = new JMenu();
        fileMenu.setText(Text.getTextFromLocale("GeneralMenuFile"));
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

    private void configureLanguages(JMenu settingsLanguage) {
        localeManager.getLanguageMap().forEach((language, locale) -> {
            JMenuItem languageItem = new JMenuItem(language);
            languageItem.addActionListener(e -> {
                System.out.println("Setting locale to " + locale.getLanguage());
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
