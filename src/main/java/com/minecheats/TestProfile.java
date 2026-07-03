package com.minecheats;

/**
 * Global switch for the "anti-detect" red-team test profile.
 *
 * <p>This is a testing aid for exercising your own anti-cheat: when enabled,
 * the cheat modules switch to behaviour that tries to look human/plausible so
 * you can measure how far your own detector still catches them. Each evasive
 * behaviour is documented next to the detection signal it is meant to produce
 * (see the module sources and the README).
 */
public final class TestProfile {

    private TestProfile() {
    }

    /** When true, modules use their evasive/"legit-looking" code paths. */
    public static volatile boolean antiDetect = false;
}
