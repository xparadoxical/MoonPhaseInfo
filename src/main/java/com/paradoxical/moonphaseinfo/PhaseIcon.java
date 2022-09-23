package com.paradoxical.moonphaseinfo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PhaseIcon extends DrawableHelper {
    private static final Identifier BACKGROUND = new Identifier("textures/gui/container/inventory.png");
    private static final Identifier ICONS = new Identifier(MoonPhaseInfo.MOD_ID, "textures/environment/moon_phases_icons.png");

    private static final Formatting[] FULLNESS_COLOR = new Formatting[]
            {Formatting.RED, Formatting.GOLD, Formatting.YELLOW, Formatting.GREEN, Formatting.DARK_GREEN};

    void drawPhaseIcon(MatrixStack matrixStack, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        int windowWidth = mc.getWindow().getScaledWidth();
        int windowHeight = mc.getWindow().getScaledHeight();

        int baseX, baseY;
        switch (Config.loc) {
            case 0:
                baseX = 30;
                baseY = 1;
                break;
            case 1:
                baseX = windowWidth / 2;
                baseY = 1;
                break;
            case 2:
                if (Config.detailedMode) baseX = windowWidth - 78;
                else baseX = windowWidth - 30;
                baseY = 1;
                break;
            case 3:
                baseX = windowWidth / 2;
                baseY = windowHeight - 30;
                break;
            case 4:
                if (Config.detailedMode) baseX = windowWidth - 78;
                else baseX = windowWidth - 30;
                baseY = windowHeight - 30;
                break;
            default:
                baseX = 30;
                baseY = 1;
        }

        int oldShaderTexture = RenderSystem.getShaderTexture(0);

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int a = 24;
        int x = baseX - a - 1;
        int y = baseY;
        drawTexture(matrixStack, x, y, 141, 166, a, a);

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, ICONS);

        int a2 = 18;
        int d = a / 2 - a2 / 2;
        x += d;
        y += d;

        int phase = mc.world.getMoonPhase();
        int u = ((4 - phase % 4) % 4) * a2; //{0, 3, 2, 1, 0, 3, 2, 1}[phase] * side length
        int v = phase >= 1 && phase <= 4 ? a2 : 0;
        drawTexture(matrixStack, x, y, this.getZOffset(), (float) u, (float) v, a2, a2, 72, 36);
        RenderSystem.disableBlend();

        if (Config.detailedMode) {
            //fullness
            int sizePercent = (int) (mc.world.getMoonSize() * 100f);
            mc.textRenderer.drawWithShadow(matrixStack, String.format("%s%d%%%s",
                            FULLNESS_COLOR[sizePercent / 25],
                            sizePercent,
                            phase <= 3 ? Formatting.RED + "↓" : Formatting.DARK_GREEN + "↑"),
                    baseX + 1, baseY + 4, 0xffffff);

            //time left
            long daySecondsLeft = (24000 - mc.world.getTimeOfDay() % 24000) / 20;
            long dayMinutesLeft = daySecondsLeft / 60;
            boolean atLeast60s = daySecondsLeft >= 60;
            mc.textRenderer.drawWithShadow(matrixStack, atLeast60s ? dayMinutesLeft + "min" : daySecondsLeft + "s",
                    baseX + 1, baseY + 4 + mc.textRenderer.fontHeight, Formatting.DARK_AQUA.getColorValue());
        }

        RenderSystem.setShaderTexture(0, oldShaderTexture);
    }
}
