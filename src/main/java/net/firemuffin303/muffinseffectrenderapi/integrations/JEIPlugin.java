package net.firemuffin303.muffinseffectrenderapi.integrations;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IRuntimeRegistration;
import mezz.jei.api.runtime.IIngredientListOverlay;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JEIPlugin implements IModPlugin {
    private static @Nullable IJeiRuntime runtime;

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }

    public static Optional<IJeiRuntime> getRuntime() {
        return Optional.ofNullable(runtime);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("muffins_era","gui_plugin");
    }

    public static boolean checkOverlay(boolean bl){
        boolean ingredientListDisplayed = JEIPlugin.getRuntime().map(IJeiRuntime::getIngredientListOverlay).map(IIngredientListOverlay::isListDisplayed).orElse(false);
        return ingredientListDisplayed ? false : bl;
    }
}
