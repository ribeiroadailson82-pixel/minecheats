package com.minecheats.modules.movement;

import com.minecheats.TestProfile;
import com.minecheats.module.Category;
import com.minecheats.module.Module;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

/**
 * Boosts horizontal ground movement speed in the direction the player is
 * actually trying to move.
 *
 * <p>Raw mode: {@value #RAW_SPEED} blocks/tick (~12 b/s), far above vanilla
 * sprint (~5.6 b/s) — trivially flagged by a per-tick distance check.
 *
 * <p>Anti-detect test mode ({@link TestProfile#antiDetect}): caps to
 * {@value #LEGIT_SPEED} b/tick, only slightly above vanilla sprint.
 * Detection signals to test against: sustained horizontal speed above the
 * sprint ceiling, and speed uncorrelated with sprint/effect state.
 */
public class Speed extends Module {

    private static final double RAW_SPEED = 0.6;   // blocks per tick
    private static final double LEGIT_SPEED = 0.32; // just above vanilla sprint

    public Speed() {
        super("Speed", "Move faster along the ground.", Category.MOVEMENT,
                org.lwjgl.glfw.GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        LocalPlayer p = mc.player;
        if (p == null) {
            return;
        }
        Vec2 move = p.input.getMoveVector();
        float strafe = move.x;
        float forward = move.y;
        if (strafe == 0.0f && forward == 0.0f) {
            return;
        }

        float yaw = (float) Math.toRadians(p.getYRot());
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);

        double dirX = forward * -sin + strafe * cos;
        double dirZ = forward * cos + strafe * sin;
        double len = Math.sqrt(dirX * dirX + dirZ * dirZ);
        if (len < 1.0e-4) {
            return;
        }
        double speed = TestProfile.antiDetect ? LEGIT_SPEED : RAW_SPEED;
        dirX = dirX / len * speed;
        dirZ = dirZ / len * speed;

        Vec3 m = p.getDeltaMovement();
        p.setDeltaMovement(dirX, m.y, dirZ);
    }
}
