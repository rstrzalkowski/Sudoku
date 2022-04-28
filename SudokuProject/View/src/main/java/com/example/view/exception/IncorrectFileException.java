package com.example.view.exception;

import com.example.view.Messages;
import java.io.IOException;
import java.util.Locale;

public class IncorrectFileException extends IOException {

    private final String messageKey;
    private final Locale locale;

    public IncorrectFileException() {
        messageKey = "incfileexc";
        locale = Locale.getDefault();
    }

    public IncorrectFileException(String msgKey) {
        this(msgKey,Locale.getDefault());
    }

    public IncorrectFileException(String msgKey, Locale locale) {
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
