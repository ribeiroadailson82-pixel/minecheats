package com.minecheats.modules.movement;

import com.minecheats.module.Category;
import com.minecheats.module.Module;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

/**
 * Cancels fall damage by telling the server we are on the ground once we
 * have fallen far enough to take damage.
 */
public class NoFall extends Module {

    public NoFall() {
        super("NoFall", "Removes fall damage.", Category.MOVEMENT,
                org.lwjgl.glfw.GLFW.GLFW_KEY_N);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.player.getAbilities().flying) {
            return;
        }
        if (mc.player.fallDistance > 2.0f && mc.player.getDeltaMovement().y < 0.0) {
            mc.player.connection.send(
                    new ServerboundMovePlayerPacket.StatusOnly(true, mc.player.horizontalCollision));
        }
    }
}
