package com.example.view;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"Title", "Autorzy",},
                {"1.", "Rafał Strzałkowski ",},
                {"2.", "Filip Warchoł "}
        };
    }
}
