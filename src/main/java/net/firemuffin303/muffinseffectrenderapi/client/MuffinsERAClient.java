package net.firemuffin303.muffinseffectrenderapi.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.firemuffin303.muffinseffectrenderapi.api.CustomEffectRegistry;

public class MuffinsERAClient implements ClientModInitializer {

    public static boolean IS_JEI_INSTALLED = false;
    public static boolean IS_EMI_INSTALLED = false;


    @Override
    public void onInitializeClient() {
        IS_JEI_INSTALLED = FabricLoader.getInstance().isModLoaded("jei");
        IS_EMI_INSTALLED = FabricLoader.getInstance().isModLoaded("emi");

        CustomEffectRegistry.init();
    }


}
