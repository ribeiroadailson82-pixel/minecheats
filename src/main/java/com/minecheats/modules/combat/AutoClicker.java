package com.minecheats.modules.combat;

import com.minecheats.TestProfile;
import com.minecheats.module.Category;
import com.minecheats.module.Module;

import java.util.Random;

/**
 * Repeatedly performs the "attack" action on whatever is under the crosshair
 * (mining a block or hitting an entity).
 *
 * <p>Raw mode: fixed 2-tick interval (~10 CPS, perfectly regular).
 * Detection signal: near-zero variance in click intervals.
 *
 * <p>Anti-detect test mode ({@link TestProfile#antiDetect}): jitters the
 * interval between clicks (1-3 ticks, ~7-15 CPS) so the timing distribution
 * looks less mechanical. Detection signals to test against: click-interval
 * standard deviation, autocorrelation, and clicking that never pauses.
 */
public class AutoClicker extends Module {

    private static final int RAW_INTERVAL = 2;
    private final Random random = new Random();

    private int counter;
    private int nextInterval = RAW_INTERVAL;

    public AutoClicker() {
        super("AutoClicker", "Auto left-clicks under the crosshair.", Category.COMBAT,
                org.lwjgl.glfw.GLFW.GLFW_KEY_C);
    }

    @Override
    protected void onEnable() {
        counter = 0;
        nextInterval = RAW_INTERVAL;
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.screen != null) {
            return;
        }
        if (++counter < nextInterval) {
            return;
        }
        counter = 0;
        // startAttack() is made public via the access transformer.
        mc.startAttack();
        nextInterval = TestProfile.antiDetect ? jitteredInterval() : RAW_INTERVAL;
    }

    /** 1-3 ticks, weighted toward 2, for a ~7-15 CPS spread with variance. */
    private int jitteredInterval() {
        int roll = random.nextInt(4); // 0..3
        return switch (roll) {
            case 0 -> 1;
            case 3 -> 3;
            default -> 2;
        };
    }
}
