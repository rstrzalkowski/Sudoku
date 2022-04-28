package com.example.view.exception;

import com.example.view.Messages;
import java.io.IOException;
import java.util.Locale;


public class FxmlNotFoundException extends IOException {

    private final String msgKey;
    private final Locale locale;


    public FxmlNotFoundException(String messageKey, Locale locale) {
        msgKey = messageKey;
        this.locale = locale;
    }

    public FxmlNotFoundException(String messageKey) {
        this(messageKey, Locale.getDefault());
    }

    public String getLocalizedMessage() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append(", ").append(Messages.getMessageForLocale(msgKey, locale))
                .toString();
    }

}
