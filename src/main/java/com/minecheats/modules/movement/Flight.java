package com.minecheats.modules.movement;

import com.minecheats.module.Category;
import com.minecheats.module.Module;
import net.minecraft.world.entity.player.Abilities;

public class Flight extends Module {

    private final float flySpeed = 1.5f;

    public Flight() {
        super("Flight", "Creative-style flight in survival.", Category.MOVEMENT,
                org.lwjgl.glfw.GLFW.GLFW_KEY_F);
    }

    @Override
    protected void onEnable() {
        if (mc.player == null) {
            return;
        }
        Abilities ab = mc.player.getAbilities();
        ab.mayfly = true;
        ab.flying = true;
        mc.player.onUpdateAbilities();
    }

    @Override
    public void onTick() {
        if (mc.player == null) {
            return;
        }
        Abilities ab = mc.player.getAbilities();
        ab.mayfly = true;
        ab.flying = true;
        ab.setFlyingSpeed(0.05f * flySpeed);
    }

    @Override
    protected void onDisable() {
        if (mc.player == null) {
            return;
        }
        Abilities ab = mc.player.getAbilities();
        boolean creative = ab.instabuild;
        ab.mayfly = creative;
        ab.flying = creative && ab.flying;
        ab.setFlyingSpeed(0.05f);
        mc.player.onUpdateAbilities();
    }
}
