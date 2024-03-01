package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public final class LocaleManager {
    private static LocaleManager instance;
    private static final String[] LANGUAGES ={"English", "Spanish", "Japanese"};
    private static final Locale[] LOCALES ={Locale.US, new Locale("es", "ES"), new Locale("ja", "JP")};
    private static final HashMap<String, Locale> LANGUAGE_MAP = new HashMap<>();

    private LocaleManager() {
        for(int i = 0; i < LANGUAGES.length; i++){
            LANGUAGE_MAP.put(LANGUAGES[i], LOCALES[i]);
        }
    }

    public static LocaleManager getInstance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance;
    }

    public static Locale getLocale(String key) {
        return LANGUAGE_MAP.get(key);
    }

    public static String[] getLanguages(){ return Arrays.copyOf(LANGUAGES, LANGUAGES.length); }

    public static HashMap<String, Locale> getLanguageMap(){
        return new HashMap<>(LANGUAGE_MAP);
    }

}
