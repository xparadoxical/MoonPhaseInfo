package com.paradoxical.moonphaseinfo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class MoonPhaseInfoMod implements ClientModInitializer {
    public static final String MOD_ID = "moonphaseinfo";

    PhaseIcon icon;

    @Override
    public void onInitializeClient() {
        icon = new PhaseIcon();
        HudRenderCallback.EVENT.register(delta -> icon.drawPhaseIcon());
    }
}
