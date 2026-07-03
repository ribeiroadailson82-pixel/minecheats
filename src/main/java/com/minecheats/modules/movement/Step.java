package com.minecheats.modules.movement;

import com.minecheats.module.Category;
import com.minecheats.module.Module;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

/** Lets the player walk up full blocks like a single step. */
public class Step extends Module {

    private static final double STEP_HEIGHT = 1.0;
    private static final double DEFAULT_HEIGHT = 0.6;

    public Step() {
        super("Step", "Walk up full blocks automatically.", Category.MOVEMENT,
                org.lwjgl.glfw.GLFW.GLFW_KEY_J);
    }

    @Override
    protected void onEnable() {
        setHeight(STEP_HEIGHT);
    }

    @Override
    protected void onDisable() {
        setHeight(DEFAULT_HEIGHT);
    }

    private void setHeight(double value) {
        if (mc.player == null) {
            return;
        }
        AttributeInstance attr = mc.player.getAttribute(Attributes.STEP_HEIGHT);
        if (attr != null) {
            attr.setBaseValue(value);
        }
    }
}
