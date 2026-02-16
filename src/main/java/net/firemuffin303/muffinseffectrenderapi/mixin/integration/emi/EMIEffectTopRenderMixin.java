package net.firemuffin303.muffinseffectrenderapi.mixin.integration.emi;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.emi.emi.runtime.EmiDrawContext;
import net.firemuffin303.muffinseffectrenderapi.api.CustomEffectRegistry;
import net.firemuffin303.muffinseffectrenderapi.common.CustomEffectRenderer;
import net.firemuffin303.muffinseffectrenderapi.common.EffectRendererImpl;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.stream.Collectors;

@Debug(export = true)
@Pseudo
@Mixin(value = EffectRenderingInventoryScreen.class,priority = 1500,remap = false)
public abstract class EMIEffectTopRenderMixin {

    @TargetHandler(mixin = "dev.emi.emi.mixin.AbstractInventoryScreenMixin",name = "emi$drawCenteredEffects")
    @Inject(method = "@MixinSquared:Handler",at = @At(value = "INVOKE", target = "Ljava/util/Collection;size()I"),remap = false)
    public void muffins$createList(GuiGraphics raw, int mouseX, int mouseY, CallbackInfo ci, @Share(value = "clist")LocalRef<Collection<CustomEffectRenderer>> customEffectRenderers){
        Collection<CustomEffectRenderer> clist = CustomEffectRegistry.getEffects().stream().filter(CustomEffectRenderer::shouldRender).collect(Collectors.toSet());
        customEffectRenderers.set(clist);
    }


    @TargetHandler(mixin = "dev.emi.emi.mixin.AbstractInventoryScreenMixin",name = "emi$drawCenteredEffects")
    @Inject(method = "@MixinSquared:Handler",at = @At(value = "CONSTANT", args = "nullValue=true"),remap = false)
    public void muffins$modifyTopRenderer(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci,
                                          @Local Collection<MobEffectInstance> effects, @Local boolean wide,
                                          @Local EmiDrawContext context,@Local(ordinal = 6) int xPos,
                                          @Share(value = "clist")LocalRef<Collection<CustomEffectRenderer>> customEffectRenderers){

        Collection<CustomEffectRenderer> clist = customEffectRenderers.get();

        if(!clist.isEmpty()){
            for(CustomEffectRenderer customEffectRenderer : clist){
                if(wide){
                    EffectRendererImpl.specialEffectRenderBackground(context.raw(),xPos,32,customEffectRenderer.backgroundTextureWide());
                }else{
                    EffectRendererImpl.specialEffectRenderBackground(context.raw(),xPos,32,customEffectRenderer.backgroundTextureShort(),0,32,32,32);
                }


                customEffectRenderer.renderBG(context.raw(),xPos,32,wide);
                EffectRendererImpl.specialEffectRenderIcon(context.raw(),xPos,32,customEffectRenderer.iconTexture(),wide);
                if(wide){
                    EffectRendererImpl.specialEffectRenderLabel(context.raw(),xPos,32,customEffectRenderer.getName());
                    EffectRendererImpl.specialEffectRenderDetail(context.raw(),xPos,32,customEffectRenderer);
                }
            }



            if(!wide){
                int n = 32;
                if(mouseX >= xPos && mouseX <= xPos + 33){
                    for(CustomEffectRenderer customEffectRenderer : clist){
                        if (mouseY >= n && mouseY <= n + 32) {
                            EffectRendererImpl.specialEffectRenderTooltips(guiGraphics,mouseX,mouseY,customEffectRenderer);
                        }
                    }
                }
            }



        }

    }

    @TargetHandler(mixin = "dev.emi.emi.mixin.AbstractInventoryScreenMixin",name = "emi$drawCenteredEffects")
    @ModifyVariable(method = "@MixinSquared:Handler",at = @At(value = "STORE",ordinal = 0),name = "size")
    public int muffins$modifySize(int value,@Share(value = "clist")LocalRef<Collection<CustomEffectRenderer>> customEffectRenderers){
        if(!customEffectRenderers.get().isEmpty()){
            return value + customEffectRenderers.get().size();
        }
        return value;
    }

}
