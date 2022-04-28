package com.example.view;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("com.example.view.messages", locale)
                .getString(messageKey);
    }

}
