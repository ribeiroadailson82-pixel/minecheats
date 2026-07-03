package com.minecheats.modules.testing;

import com.minecheats.TestProfile;
import com.minecheats.module.Category;
import com.minecheats.module.Module;

/**
 * Red-team test profile. While enabled, the other modules switch to their
 * evasive ("legit-looking") behaviour so you can throw them at your own
 * anti-cheat and see what still gets flagged.
 *
 * <p>This is NOT a guarantee of undetectability — it is a set of known
 * evasion techniques whose expected detection signals are documented in each
 * module and in the README. The point is to test the detector, not to beat
 * someone else's server.
 */
public class AntiDetect extends Module {

    public AntiDetect() {
        super("AntiDetect", "Switch modules to evasive test behaviour.", Category.TESTING,
                org.lwjgl.glfw.GLFW.GLFW_KEY_K);
    }

    @Override
    protected void onEnable() {
        TestProfile.antiDetect = true;
    }

    @Override
    protected void onDisable() {
        TestProfile.antiDetect = false;
    }
}
