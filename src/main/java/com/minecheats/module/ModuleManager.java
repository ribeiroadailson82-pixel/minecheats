package com.minecheats.module;

import com.minecheats.modules.combat.AutoClicker;
import com.minecheats.modules.combat.KillAura;
import com.minecheats.modules.movement.Flight;
import com.minecheats.modules.movement.NoFall;
import com.minecheats.modules.movement.Speed;
import com.minecheats.modules.movement.Sprint;
import com.minecheats.modules.movement.Step;
import com.minecheats.modules.player.FastBreak;
import com.minecheats.modules.player.FastPlace;
import com.minecheats.modules.render.FullBright;

import java.util.ArrayList;
import java.util.List;

/**
 * Owns every module instance and provides lookup / dispatch helpers.
 */
public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Combat
        add(new KillAura());
        add(new AutoClicker());
        // Movement
        add(new Flight());
        add(new Speed());
        add(new Sprint());
        add(new NoFall());
        add(new Step());
        // Player
        add(new FastBreak());
        add(new FastPlace());
        // Render
        add(new FullBright());
    }

    private void add(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModules(Category category) {
        List<Module> out = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == category) {
                out.add(m);
            }
        }
        return out;
    }

    public List<Module> getEnabled() {
        List<Module> out = new ArrayList<>();
        for (Module m : modules) {
            if (m.isEnabled()) {
                out.add(m);
            }
        }
        return out;
    }

    public Module getByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    /** Toggle any module whose bound key matches the given GLFW key code. */
    public void onKey(int keyCode) {
        if (keyCode < 0) {
            return;
        }
        for (Module m : modules) {
            if (m.getKey() == keyCode) {
                m.toggle();
            }
        }
    }
}
