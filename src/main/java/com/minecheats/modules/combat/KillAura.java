package com.minecheats.modules.combat;

import com.minecheats.module.Category;
import com.minecheats.module.Module;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Automatically attacks the nearest living entity within reach, respecting
 * the vanilla attack-cooldown so hits deal full damage.
 */
public class KillAura extends Module {

    private final double range = 4.2;

    public KillAura() {
        super("KillAura", "Auto-attacks nearby entities.", Category.COMBAT,
                org.lwjgl.glfw.GLFW.GLFW_KEY_R);
    }

    @Override
    public void onTick() {
        Player p = mc.player;
        if (p == null || mc.level == null || mc.gameMode == null) {
            return;
        }
        if (p.getAttackStrengthScale(0.0f) < 1.0f) {
            return;
        }

        Entity best = null;
        double bestDist = range;
        for (Entity e : mc.level.entitiesForRendering()) {
            if (!(e instanceof LivingEntity living) || e == p) {
                continue;
            }
            if (!living.isAlive() || living.isDeadOrDying()) {
                continue;
            }
            double d = p.distanceTo(e);
            if (d <= bestDist) {
                bestDist = d;
                best = e;
            }
        }

        if (best != null) {
            mc.gameMode.attack(p, best);
            p.swing(InteractionHand.MAIN_HAND);
        }
    }
}
