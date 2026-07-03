package com.minecheats.modules.player;

import com.minecheats.module.Category;
import com.minecheats.module.Module;

/** Removes the vanilla delay between right-click item/block placements. */
public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", "Removes the right-click placement delay.", Category.PLAYER,
                org.lwjgl.glfw.GLFW.GLFW_KEY_V);
    }

    @Override
    public void onTick() {
        // rightClickDelay is made public via the access transformer.
        mc.rightClickDelay = 0;
    }
}
