package com.project.btl.pages;

public enum MainMenu {
    ZCHUYOT("זכויות והטבות"),
    KITZBAOT("קצבאות"),
    TASH_LUMIM("תשלומים"),
    SNIFIM("סניפים וערוצי שירות");

    private final String text;

    MainMenu(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
