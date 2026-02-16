package net.firemuffin303.muffinseffectrenderapi.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.firemuffin303.muffinseffectrenderapi.common.EffectRendererImpl;
import net.firemuffin303.muffinseffectrenderapi.api.CustomEffectRegistry;
import net.firemuffin303.muffinseffectrenderapi.common.CustomEffectRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow private int screenWidth;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "renderEffects",at = @At(value = "HEAD"))
    public void muffins$renderSpecialEffect(GuiGraphics guiGraphics, CallbackInfo ci){
        Collection<CustomEffectRenderer> clist = CustomEffectRegistry.getEffects().stream().filter(CustomEffectRenderer::shouldRender).collect(Collectors.toSet());

        if(!clist.isEmpty() && !(this.minecraft.screen instanceof EffectRenderingInventoryScreen<?> effectRenderingInventoryScreen && effectRenderingInventoryScreen.canSeeEffects()) ){
            RenderSystem.enableBlend();
            int yPos = 53;
            int xOffset = 0;
            for(CustomEffectRenderer customEffectRenderer: clist){
                int xPos = this.screenWidth;
                xOffset++;
                xPos -= 25 * xOffset;
                EffectRendererImpl.specialEffectRenderHUD(guiGraphics,xPos,yPos,customEffectRenderer);
            }
        }
    }
}
