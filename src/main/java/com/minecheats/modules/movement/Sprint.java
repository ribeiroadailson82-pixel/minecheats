package com.minecheats.modules.movement;

import com.minecheats.module.Category;
import com.minecheats.module.Module;

/** Keeps the player sprinting whenever they are moving forward. */
public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Always sprint while moving.", Category.MOVEMENT,
                org.lwjgl.glfw.GLFW.GLFW_KEY_U);
    }

    @Override
    public void onTick() {
        if (mc.player == null) {
            return;
        }
        boolean movingForward = mc.player.input.getMoveVector().y > 0.0f;
        if (movingForward && !mc.player.isShiftKeyDown() && !mc.player.horizontalCollision) {
            mc.player.setSprinting(true);
        }
    }
}
