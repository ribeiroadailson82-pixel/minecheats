package com.minecheats;

import com.minecheats.gui.ClickGuiScreen;
import com.minecheats.module.Module;
import com.minecheats.module.ModuleManager;
import com.minecheats.modules.player.FastBreak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/** Bridges NeoForge game-bus events onto the module system. */
public class EventHandler {

    private static final int COLOR_TITLE = 0xFF55FFFF;
    private static final int COLOR_MODULE = 0xFFFFFFFF;

    private final ModuleManager modules;

    public EventHandler(ModuleManager modules) {
        this.modules = modules;
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            return;
        }
        for (Module m : modules.getModules()) {
            if (m.isEnabled()) {
                m.onTick();
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (event.getAction() != GLFW.GLFW_PRESS) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) {
            return;
        }
        if (event.getKey() == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            mc.setScreen(new ClickGuiScreen(modules));
            return;
        }
        modules.onKey(event.getKey());
    }

    @SubscribeEvent
    public void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.screen instanceof ClickGuiScreen) {
            return;
        }
        GuiGraphics gg = event.getGuiGraphics();
        int width = gg.guiWidth();
        int y = 2;
        gg.drawString(mc.font, "MineCheats", 2, y, COLOR_TITLE);
        y += 12;
        List<Module> enabled = modules.getEnabled();
        for (Module m : enabled) {
            String name = m.getName();
            int w = mc.font.width(name);
            gg.drawString(mc.font, name, width - w - 3, y, COLOR_MODULE);
            y += 10;
        }
    }

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Module fastBreak = modules.getByName("FastBreak");
        if (fastBreak != null && fastBreak.isEnabled()) {
            event.setNewSpeed(event.getOriginalSpeed() * FastBreak.MULTIPLIER);
        }
    }
}
