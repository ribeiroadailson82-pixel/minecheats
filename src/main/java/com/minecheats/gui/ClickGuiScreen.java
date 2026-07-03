package com.minecheats.gui;

import com.minecheats.module.Category;
import com.minecheats.module.Module;
import com.minecheats.module.ModuleManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** Minimal click-to-toggle GUI, opened with Right Shift. */
public class ClickGuiScreen extends Screen {

    private static final int COL_WIDTH = 110;
    private static final int BTN_HEIGHT = 14;
    private static final int PADDING = 6;
    private static final int COLOR_WHITE = 0xFFFFFFFF;
    private static final int COLOR_HEADER = 0xFFFFFF55;

    private final ModuleManager modules;

    public ClickGuiScreen(ModuleManager modules) {
        super(Component.literal("MineCheats"));
        this.modules = modules;
    }

    @Override
    protected void init() {
        int x = PADDING;
        for (Category category : Category.values()) {
            int y = 26;
            for (Module m : modules.getModules(category)) {
                Button button = Button.builder(label(m), b -> {
                    m.toggle();
                    rebuildWidgets();
                }).bounds(x, y, COL_WIDTH - PADDING, BTN_HEIGHT).build();
                addRenderableWidget(button);
                y += BTN_HEIGHT + 2;
            }
            x += COL_WIDTH;
        }
    }

    private Component label(Module m) {
        return Component.literal(m.getName() + (m.isEnabled() ? " [ON]" : " [OFF]"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawString(this.font, "MineCheats  -  Right Shift to close", PADDING, 8, COLOR_WHITE);

        int x = PADDING;
        for (Category category : Category.values()) {
            graphics.drawString(this.font, category.display, x, 17, COLOR_HEADER);
            x += COL_WIDTH;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
