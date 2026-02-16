package net.firemuffin303.muffinseffectrenderapi.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EffectRendererImpl {
    private static final Font font = Minecraft.getInstance().font;

    private static final ResourceLocation EFFECT_TEXTURE = new ResourceLocation("muffins_era","textures/custom_effects/custom_effect.png");
    private static final ResourceLocation PLACEHOLDER = new ResourceLocation("muffins_era","textures/custom_effects/icons/placeholder.png");


    public static void specialEffectRenderBackground(GuiGraphics guiGraphics,int x ,int y, ResourceLocation texture){
        specialEffectRenderBackground(guiGraphics,x,y,texture,0,0,120,32);

    }

    public static void specialEffectRenderBackground(GuiGraphics guiGraphics, int x, int y, ResourceLocation texture,int u,int v,int textureWidth,int textureHeight){
        if(texture == null){
            texture = EFFECT_TEXTURE;
        }

        guiGraphics.blit(texture,x,y,u,v,textureWidth,textureHeight);
    }


    public static void specialEffectRenderLabel(GuiGraphics guiGraphics, int x, int y, Component component){
        Component defaultDetail = Component.empty();

        if(component != null){
            defaultDetail = component;
        }

        guiGraphics.drawString(font, defaultDetail,x + 10 + 18, y + 6, 16777215);
    }

    public static void specialEffectRenderDetail(GuiGraphics guiGraphics, int x, int y, CustomEffectRenderer customEffectRenderer){
        Component defaultDetail = Component.empty();

        if(customEffectRenderer.getDetail() != null){
            defaultDetail = customEffectRenderer.getDetail();
        }

        guiGraphics.drawString(font,defaultDetail,x + 10 + 18, y+6+10,8355711);

        //customEffectRenderer.renderDetail(font,guiGraphics,x + 10 + 18, y + 6 + 10,8355711);
    }

    public static void specialEffectRenderIcon(GuiGraphics guiGraphics,int x,int y,ResourceLocation resourceLocation,boolean wide){
        if(resourceLocation == null){
            resourceLocation = PLACEHOLDER;
        }

        guiGraphics.blit(resourceLocation,x + (wide ? 6 : 7), y + 7, 0,0,18, 18,18,18);
    }

    public static void specialEffectRenderTooltips(GuiGraphics guiGraphics,int x,int y,CustomEffectRenderer customEffectRenderer){

        List<Component> tooltipData = new ArrayList<>();

        if(customEffectRenderer.getName() != null){
            tooltipData.add(customEffectRenderer.getName());
        }

        if(customEffectRenderer.getDetail() != null){
            tooltipData.add(customEffectRenderer.getDetail());
        }

        guiGraphics.renderTooltip(font,tooltipData, Optional.empty(),x,y);
    }

    public static void specialEffectRenderHUD(GuiGraphics guiGraphics,int x,int y,CustomEffectRenderer customEffectRenderer){
        ResourceLocation hudBG = customEffectRenderer.backgroundTextureHUD();
        ResourceLocation icon = customEffectRenderer.iconTexture();
        if(hudBG == null){
            hudBG = EFFECT_TEXTURE;
        }

        if(icon == null){
            icon = PLACEHOLDER;
        }

        guiGraphics.blit(hudBG,x,y,120,0,24,24);
        guiGraphics.blit(icon,x+3,y+3,0,0,18,18,18,18);
    }
}
