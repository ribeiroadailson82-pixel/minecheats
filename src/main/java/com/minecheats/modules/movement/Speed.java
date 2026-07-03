package com.minecheats.modules.movement;

import com.minecheats.module.Category;
import com.minecheats.module.Module;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

/**
 * Boosts horizontal ground movement speed in the direction the player is
 * actually trying to move.
 */
public class Speed extends Module {

    private final double speed = 0.6; // blocks per tick when moving

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
        dirX = dirX / len * speed;
        dirZ = dirZ / len * speed;

        Vec3 m = p.getDeltaMovement();
        p.setDeltaMovement(dirX, m.y, dirZ);
    }
}
