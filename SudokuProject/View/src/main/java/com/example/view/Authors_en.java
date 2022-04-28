package com.example.view;

import java.util.ListResourceBundle;

public class Authors_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"Title", "Authors",},
                {"1.", "Raphael Strzalkowski ",},
                {"2.", "Philip Warchol "}
        };
    }
}
