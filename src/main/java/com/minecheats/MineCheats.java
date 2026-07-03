package com.minecheats;

import com.minecheats.module.ModuleManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Entry point. Client-side only. Builds the module system and registers the
 * game-bus event bridge. Toggle modules with their hotkeys or the Right-Shift
 * ClickGUI.
 */
@Mod(value = MineCheats.MOD_ID, dist = Dist.CLIENT)
public class MineCheats {

    public static final String MOD_ID = "minecheats";

    private static MineCheats instance;
    private final ModuleManager moduleManager;

    public MineCheats(IEventBus modBus) {
        instance = this;
        this.moduleManager = new ModuleManager();
        NeoForge.EVENT_BUS.register(new EventHandler(moduleManager));
    }

    public static MineCheats getInstance() {
        return instance;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
