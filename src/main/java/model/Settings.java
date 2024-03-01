package model;

import gui.Text;
import gui.UIChangeListener;

import javax.swing.*;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public final class Settings {

    private static Settings instance;

    private static Locale currentLocale;


    private Settings(){}

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }



    private static final List<UIChangeListener> UI_LISTENERS = new ArrayList<>();

    public static void addUIChangeListener(UIChangeListener listener) {
        UI_LISTENERS.add(listener);
    }

    public static void removeUIChangeListener(UIChangeListener listener) {
        UI_LISTENERS.remove(listener);
    }

    public static void notifyUIChange() {
        for (UIChangeListener listener : UI_LISTENERS) {
            listener.updateLangUI();
        }
    }

    public void echoUpdateUI() {
        notifyUIChange();
    }


    public static void setLocale(Locale locale){
        currentLocale = locale;
        Text.init(currentLocale);
        JOptionPane.setDefaultLocale(locale);
        System.out.println("Notifying UI Change");
    }


}
