package com.minecheats.modules.combat;

import com.minecheats.module.Category;
import com.minecheats.module.Module;

/**
 * Repeatedly performs the "attack" action on whatever is under the crosshair
 * (mining a block or hitting an entity) at a fixed interval.
 */
public class AutoClicker extends Module {

    private static final int INTERVAL_TICKS = 2; // ~10 cps
    private int counter;

    public AutoClicker() {
        super("AutoClicker", "Auto left-clicks under the crosshair.", Category.COMBAT,
                org.lwjgl.glfw.GLFW.GLFW_KEY_C);
    }

    @Override
    protected void onEnable() {
        counter = 0;
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.screen != null) {
            return;
        }
        if (++counter < INTERVAL_TICKS) {
            return;
        }
        counter = 0;
        // startAttack() is made public via the access transformer.
        mc.startAttack();
    }
}
