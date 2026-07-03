package com.minecheats.modules.player;

import com.minecheats.module.Category;
import com.minecheats.module.Module;

/**
 * Flag module. The actual speed multiplier is applied by the event handler
 * in {@code PlayerEvent.BreakSpeed} while this module is enabled.
 */
public class FastBreak extends Module {

    public static final float MULTIPLIER = 5.0f;

    public FastBreak() {
        super("FastBreak", "Mine blocks much faster.", Category.PLAYER,
                org.lwjgl.glfw.GLFW.GLFW_KEY_B);
    }
}
