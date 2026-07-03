package com.minecheats.modules.combat;

import com.minecheats.TestProfile;
import com.minecheats.module.Category;
import com.minecheats.module.Module;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Automatically attacks the nearest living entity, respecting the vanilla
 * attack-cooldown so hits deal full damage.
 *
 * <p>Raw mode: silent aura — attacks up to 4.2 blocks with no rotation.
 * This is trivially detectable server-side (hits with no matching look angle,
 * reach beyond ~3.0).
 *
 * <p>Anti-detect test mode ({@link TestProfile#antiDetect}): clamps reach to a
 * vanilla-plausible 3.0 blocks, smoothly rotates the real client view toward
 * the target (max {@value #MAX_ROTATION_STEP} deg/tick) and only swings when
 * the target is inside the view cone.
 * Detection signals to test against: angular velocity/acceleration spikes,
 * rotation that always leads attacks by a fixed pattern, aim snapping onto
 * hitbox centers, and consistent sub-tick attack timing.
 */
public class KillAura extends Module {

    private static final double RAW_RANGE = 4.2;
    private static final double LEGIT_RANGE = 3.0;
    private static final float MAX_ROTATION_STEP = 30.0f;
    private static final float VIEW_CONE_DEGREES = 22.0f;

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

        boolean legit = TestProfile.antiDetect;
        double range = legit ? LEGIT_RANGE : RAW_RANGE;

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

        if (best == null) {
            return;
        }

        if (!legit) {
            mc.gameMode.attack(p, best);
            p.swing(InteractionHand.MAIN_HAND);
            return;
        }

        // Anti-detect test path: rotate the real view toward the target,
        // then only attack when the target is inside the view cone.
        Vec3 eye = p.getEyePosition();
        Vec3 center = best.getBoundingBox().getCenter();
        double dx = center.x - eye.x;
        double dy = center.y - eye.y;
        double dz = center.z - eye.z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);

        float targetYaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float targetPitch = (float) (-Math.toDegrees(Math.atan2(dy, horizontal)));

        float curYaw = p.getYRot();
        float curPitch = p.getXRot();
        float deltaYaw = Mth.wrapDegrees(targetYaw - curYaw);
        float deltaPitch = Mth.wrapDegrees(targetPitch - curPitch);

        float newYaw = curYaw + Mth.clamp(deltaYaw, -MAX_ROTATION_STEP, MAX_ROTATION_STEP);
        float newPitch = Mth.clamp(curPitch + Mth.clamp(deltaPitch, -MAX_ROTATION_STEP, MAX_ROTATION_STEP),
                -90.0f, 90.0f);
        p.setYRot(newYaw);
        p.setXRot(newPitch);

        float remainingYaw = Math.abs(Mth.wrapDegrees(targetYaw - newYaw));
        float remainingPitch = Math.abs(Mth.wrapDegrees(targetPitch - newPitch));
        if (remainingYaw <= VIEW_CONE_DEGREES && remainingPitch <= VIEW_CONE_DEGREES) {
            mc.gameMode.attack(p, best);
            p.swing(InteractionHand.MAIN_HAND);
        }
    }
}
