package com.minecheats.module;

import net.minecraft.client.Minecraft;

/**
 * Base class for every cheat/utility module.
 * Subclasses override the lifecycle hooks they need.
 */
public abstract class Module {

    protected final Minecraft mc = Minecraft.getInstance();

    private final String name;
    private final String description;
    private final Category category;
    private int key;
    private boolean enabled;

    protected Module(String name, String description, Category category, int key) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.key = key;
    }

    protected Module(String name, String description, Category category) {
        this(name, description, category, -1);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean state) {
        if (this.enabled == state) {
            return;
        }
        this.enabled = state;
        if (state) {
            onEnable();
        } else {
            onDisable();
        }
    }

    // ---- Lifecycle hooks (override as needed) ----

    /** Called once when the module is turned on. */
    protected void onEnable() {
    }

    /** Called once when the module is turned off. */
    protected void onDisable() {
    }

    /** Called every client tick while enabled (in-game only). */
    public void onTick() {
    }
}
