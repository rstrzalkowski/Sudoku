package com.example.view.exception;

import com.example.view.Messages;
import java.util.Locale;

public class BuilderException extends Exception {

    private final String messageKey;
    private final Locale locale;

    public BuilderException(String msgKey) {
        messageKey = msgKey;
        locale = Locale.getDefault();
    }

    public BuilderException(String msgKey, Locale locale) {
        messageKey = msgKey;
        this.locale = locale;
    }

    public String getLocalizedMessage() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append(", ").append(Messages.getMessageForLocale(messageKey, locale))
                .toString();
    }
}
