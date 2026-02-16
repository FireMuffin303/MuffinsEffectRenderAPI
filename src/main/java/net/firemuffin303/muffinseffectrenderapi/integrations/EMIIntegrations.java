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

    public static Collection<CustomEffectRenderer> shouldHidden(Collection<CustomEffectRenderer> collection){
        if(EmiConfig.effectLocation == EffectLocation.HIDDEN){
            return List.of();
        }

        return collection;
    }

    public static boolean shouldRenderTop(){
        return EmiConfig.effectLocation == EffectLocation.TOP;
    }

    public static boolean checkCompressed(boolean bl) {
        if(EmiConfig.effectLocation == EffectLocation.LEFT_COMPRESSED || EmiConfig.effectLocation == EffectLocation.RIGHT_COMPRESSED){
            return false;
        }

        return bl;
    }

    public static int yAdjusting(int value,Screen screen){
        int a = value;
        if(screen instanceof CreativeModeInventoryScreen || EmiAgnos.isModLoaded("inventorytabs")){
            a -= 28;

            if(screen instanceof CreativeModeInventoryScreen && EmiAgnos.isForge()){
                a -= 22;
            }
        }
        return a;
    }

    public static void renderTop(GuiGraphics raw, int mouseX, int mouseY) {
        Collection<CustomEffectRenderer> clist = CustomEffectRegistry.getEffects().stream().filter(CustomEffectRenderer::shouldRender).collect(Collectors.toSet());

        /*
        if(!clist.isEmpty()){
            for(CustomEffectRenderer customEffectRenderer : clist){
                if(wide){
                    EffectRendererImpl.specialEffectRenderBackground(guiGraphics,xPos,yPos,customEffectRenderer.backgroundTextureWide());
                }else{
                    EffectRendererImpl.specialEffectRenderBackground(guiGraphics,xPos,yPos,customEffectRenderer.backgroundTextureShort(),0,32,32,32);
                }


                customEffectRenderer.renderBG(guiGraphics,xPos,yPos,wide);
                EffectRendererImpl.specialEffectRenderIcon(guiGraphics,xPos,yPos,customEffectRenderer.iconTexture(),wide);
                if(wide){
                    EffectRendererImpl.specialEffectRenderLabel(guiGraphics,xPos,yPos,customEffectRenderer.getName());
                    EffectRendererImpl.specialEffectRenderDetail(guiGraphics,xPos,yPos,customEffectRenderer);
                }
                yPos += this.toastHeight;
            }



            if(!wide){
                int n = topPos;
                if(mouseX >= xPos && mouseX <= xPos + 33){
                    for(CustomEffectRenderer customEffectRenderer : clist){
                        if (mouseY >= n && mouseY <= n + this.toastHeight) {
                            EffectRendererImpl.specialEffectRenderTooltips(guiGraphics,mouseX,mouseY,customEffectRenderer);
                        }

                        n += this.toastHeight;
                    }
                }
            }



        }
    */
    }
}
