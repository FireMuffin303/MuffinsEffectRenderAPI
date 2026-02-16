package net.firemuffin303.muffinseffectrenderapi.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.firemuffin303.muffinseffectrenderapi.client.MuffinsERAClient;
import net.firemuffin303.muffinseffectrenderapi.common.EffectRendererImpl;
import net.firemuffin303.muffinseffectrenderapi.api.CustomEffectRegistry;
import net.firemuffin303.muffinseffectrenderapi.common.CustomEffectRenderer;
import net.firemuffin303.muffinseffectrenderapi.integrations.EMIIntegrations;
import net.firemuffin303.muffinseffectrenderapi.integrations.JEIPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(EffectRenderingInventoryScreen.class)
public class EffectRenderingInventoryScreenMixin {

    @Unique private int toastHeight = 33;
    @Unique private int specialEffectAmount = 0;

    @Inject(method = "renderEffects",at = @At(value = "INVOKE", target = "Ljava/util/Collection;isEmpty()Z"))
    public void muffins$specialEffectRender(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci,
                                            @Local Collection<MobEffectInstance> effects,@Local(ordinal = 2) int xPos,@Local(ordinal = 3) int screenWidth){
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>)(Object)this;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        int topPos = ((AbstractContainerScreenAccessor)screen).getTopPos();

        int yPos = topPos;

        boolean wide = screenWidth >= 120;
        boolean shouldRenderTop = false;

        if(MuffinsERAClient.IS_JEI_INSTALLED){
            wide = JEIPlugin.checkOverlay(wide);
        }

        Collection<CustomEffectRenderer> clist = CustomEffectRegistry.getEffects().stream().filter(customEffectRenderer -> customEffectRenderer.shouldRender(localPlayer)).collect(Collectors.toSet());

        if(MuffinsERAClient.IS_EMI_INSTALLED){
            clist = EMIIntegrations.shouldHidden(clist);
            wide = EMIIntegrations.checkCompressed(wide);
            shouldRenderTop = EMIIntegrations.shouldRenderTop();
        }

        this.specialEffectAmount = clist.size();

        if(shouldRenderTop){
            wide = clist.size() == 1;
            int xOff = 34;
            if (wide) {
                xOff = 122;
            } else if (clist.size() > 5) {
                xOff = (((AbstractContainerScreenAccessor)screen).getImageWidth() - 32) / (clist.size() - 1);
            }

            int width = (clist.size() - 1) * xOff + (wide ? 120 : 32);
            yPos -= 61;
            xPos = ((AbstractContainerScreenAccessor)screen).getLeftPos() + (((AbstractContainerScreenAccessor)screen).getImageWidth() - width) / 2;

            if(MuffinsERAClient.IS_EMI_INSTALLED){
                yPos = EMIIntegrations.yAdjusting(yPos,screen);
            }

            if(!clist.isEmpty()){
                for(CustomEffectRenderer customEffectRenderer: clist){
                    if(wide){
                        EffectRendererImpl.specialEffectRenderWideBackground(guiGraphics,xPos,yPos,customEffectRenderer.color(localPlayer));
                    }else{
                        EffectRendererImpl.specialEffectRenderShortBackground(guiGraphics,xPos,yPos,customEffectRenderer.color(localPlayer));

                    }
                    EffectRendererImpl.specialEffectRenderIcon(guiGraphics,xPos,yPos,customEffectRenderer.iconTexture(localPlayer),wide);
                    if(wide){
                        EffectRendererImpl.specialEffectRenderLabel(guiGraphics,xPos,yPos,customEffectRenderer.getName(localPlayer));
                        EffectRendererImpl.specialEffectRenderDetail(guiGraphics,xPos,yPos,customEffectRenderer,localPlayer);
                    }

                    if(mouseX >= xPos && mouseX <= xPos + 33){
                        if (mouseY >= yPos && mouseY <= yPos + 32) {
                            EffectRendererImpl.specialEffectRenderTooltips(guiGraphics,mouseX,mouseY,customEffectRenderer,localPlayer);
                        }
                    }

                    xPos += xOff;
                }
            }



        }else{

            if( (clist.size() + effects.size()) > 5){
                this.toastHeight = 132 / ((clist.size() + effects.size() - 1) );
            }else{
                this.toastHeight = 33;
            }

            if(!clist.isEmpty()){
                for(CustomEffectRenderer customEffectRenderer : clist){
                    if(wide){
                        EffectRendererImpl.specialEffectRenderWideBackground(guiGraphics,xPos,yPos,customEffectRenderer.color(localPlayer));
                    }else{
                        EffectRendererImpl.specialEffectRenderShortBackground(guiGraphics,xPos,yPos,customEffectRenderer.color(localPlayer));
                    }

                    EffectRendererImpl.specialEffectRenderIcon(guiGraphics,xPos,yPos,customEffectRenderer.iconTexture(localPlayer),wide);
                    if(wide){
                        EffectRendererImpl.specialEffectRenderLabel(guiGraphics,xPos,yPos,customEffectRenderer.getName(localPlayer));
                        EffectRendererImpl.specialEffectRenderDetail(guiGraphics,xPos,yPos,customEffectRenderer,localPlayer);
                    }
                    yPos += this.toastHeight;
                }



                if(!wide){
                    int n = topPos;
                    if(mouseX >= xPos && mouseX <= xPos + 33){
                        for(CustomEffectRenderer customEffectRenderer : clist){
                            if (mouseY >= n && mouseY <= n + this.toastHeight) {
                                EffectRendererImpl.specialEffectRenderTooltips(guiGraphics,mouseX,mouseY,customEffectRenderer,localPlayer);
                            }

                            n += this.toastHeight;
                        }
                    }
                }



            }
        }


    }

    @WrapOperation(method = "renderEffects",at = @At(value = "INVOKE", target = "Ljava/util/Collection;size()I",ordinal = 0))
    public int muffins$modifyConditions(Collection instance, Operation<Integer> original){
        boolean isRenderTop = false;
        if(MuffinsERAClient.IS_EMI_INSTALLED){
            isRenderTop = EMIIntegrations.shouldRenderTop();
        }

        if(this.specialEffectAmount > 0 && !isRenderTop){
            return original.call(instance) + this.specialEffectAmount;
        }

        return original.call(instance);
    }

    @WrapOperation(method = "renderEffects",at = @At(value = "INVOKE", target = "Ljava/util/Collection;size()I",ordinal = 1))
    public int muffins$modifyCollectionSize(Collection instance, Operation<Integer> original){
        boolean isRenderTop = false;
        if(MuffinsERAClient.IS_EMI_INSTALLED){
            isRenderTop = EMIIntegrations.shouldRenderTop();
        }

        if(this.specialEffectAmount > 0 && !isRenderTop){
            return original.call(instance) + this.specialEffectAmount;
        }

        return original.call(instance);
    }

    @ModifyExpressionValue(method = "renderBackgrounds",at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;topPos:I"))
    public int muffins$modifyRenderBGPosition(int original){
        boolean isRenderTop = false;
        if(MuffinsERAClient.IS_EMI_INSTALLED){
            isRenderTop = EMIIntegrations.shouldRenderTop();
        }

        if(this.specialEffectAmount > 0 && !isRenderTop){
            return original + (this.toastHeight * this.specialEffectAmount);

        }
        return original;
    }

    @ModifyExpressionValue(method = "renderIcons",at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;topPos:I"))
    public int muffins$modifyRenderIconPosition(int original){
        boolean isRenderTop = false;
        if(MuffinsERAClient.IS_EMI_INSTALLED){
            isRenderTop = EMIIntegrations.shouldRenderTop();
        }

        if(this.specialEffectAmount > 0 && !isRenderTop){
            return original + (this.toastHeight * this.specialEffectAmount);

        }
        return original;
    }

    @ModifyExpressionValue(method = "renderLabels",at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;topPos:I"))
    public int muffins$modifyRenderLabelPosition(int original){
        boolean isRenderTop = false;
        if(MuffinsERAClient.IS_EMI_INSTALLED){
            isRenderTop = EMIIntegrations.shouldRenderTop();
        }

        if(this.specialEffectAmount > 0 && !isRenderTop){
            return original + (this.toastHeight * this.specialEffectAmount);

        }
        return original;
    }

    @ModifyExpressionValue(method = "renderEffects", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;topPos:I"))
    public int muffins$modifyTooltipPos(int value){
        boolean isRenderTop = false;
        if(MuffinsERAClient.IS_EMI_INSTALLED){
            isRenderTop = EMIIntegrations.shouldRenderTop();
        }

        if(this.specialEffectAmount > 0 && !isRenderTop){
            return value + (this.toastHeight * this.specialEffectAmount);
        }

        return value;
    }
}
