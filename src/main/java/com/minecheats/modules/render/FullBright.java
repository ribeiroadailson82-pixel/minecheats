package com.minecheats.modules.render;

import com.minecheats.module.Category;
import com.minecheats.module.Module;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

/**
 * Applies a client-side, permanent Night Vision effect so caves and nights
 * are fully lit. Removed cleanly when disabled.
 */
public class FullBright extends Module {

    public FullBright() {
        super("FullBright", "See in the dark (permanent night vision).", Category.RENDER,
                org.lwjgl.glfw.GLFW.GLFW_KEY_H);
    }

    @Override
    public void onTick() {
        if (mc.player == null) {
            return;
        }
        mc.player.addEffect(
                new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, false), null);
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }
}
