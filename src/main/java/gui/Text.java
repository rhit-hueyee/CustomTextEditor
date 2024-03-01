package gui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Text {
    private static Locale appLocale;
    private static ResourceBundle bundle = null;

    public static void init(Locale locale){
        Text.appLocale = locale;
        Text.bundle = ResourceBundle.getBundle("text.MenuItems", appLocale);
    }

    public static String getTextFromLocale(String key, Object... args) {
        if (Text.bundle == null) {throw new IllegalStateException("Text is not initialized");}
        //Does this error message need to be i18n'd?
        return MessageFormat.format(Text.bundle.getString(key), args);
    }


}
