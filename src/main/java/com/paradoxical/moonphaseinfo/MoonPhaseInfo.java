package com.paradoxical.moonphaseinfo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class MoonPhaseInfo implements ClientModInitializer {
    public static final String MOD_ID = "moonphaseinfo";

    PhaseIcon icon;

    @Override
    public void onInitializeClient() {
        new Config();
        icon = new PhaseIcon();
        HudRenderCallback.EVENT.register((matrixStack, delta) -> icon.drawPhaseIcon(matrixStack, delta));
    }
}
