package net.firemuffin303.muffinseffectrenderapi.integrations;

import dev.emi.emi.config.EffectLocation;
import dev.emi.emi.config.EmiConfig;
import dev.emi.emi.platform.EmiAgnos;
import net.firemuffin303.muffinseffectrenderapi.api.CustomEffectRegistry;
import net.firemuffin303.muffinseffectrenderapi.common.CustomEffectRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EMIIntegrations {

    public static Collection<CustomEffectRenderer> shouldHidden(Collection<CustomEffectRenderer> collection) {
        if (EmiConfig.effectLocation == EffectLocation.HIDDEN) {
            return List.of();
        }

        return collection;
    }

    public static boolean shouldRenderTop() {
        return EmiConfig.effectLocation == EffectLocation.TOP;
    }

    public static boolean checkCompressed(boolean bl) {
        if (EmiConfig.effectLocation == EffectLocation.LEFT_COMPRESSED || EmiConfig.effectLocation == EffectLocation.RIGHT_COMPRESSED) {
            return false;
        }

        return bl;
    }

    public static int yAdjusting(int value, Screen screen) {
        int a = value;
        if (screen instanceof CreativeModeInventoryScreen || EmiAgnos.isModLoaded("inventorytabs")) {
            a -= 28;

            if (screen instanceof CreativeModeInventoryScreen && EmiAgnos.isForge()) {
                a -= 22;
            }
        }
        return a;
    }
}
