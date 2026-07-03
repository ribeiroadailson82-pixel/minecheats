package com.minecheats.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    TESTING("Testing");

    public final String display;

    Category(String display) {
        this.display = display;
    }
}
