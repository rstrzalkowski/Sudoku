package com.example.view.exception;

import com.example.view.Messages;
import java.util.Locale;

public class InstanceCreationException extends Exception {

    private final String messageKey;
    private final Locale locale;


    public InstanceCreationException(String msgKey, Locale locale) {
        messageKey = msgKey;
        this.locale = locale;
    }

    public InstanceCreationException(String msgKey) {
        this(msgKey, Locale.getDefault());
    }


    public String getLocalizedMessage() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append(", ").append(Messages.getMessageForLocale(messageKey, locale))
                .toString();
    }
}
