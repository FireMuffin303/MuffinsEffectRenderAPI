package net.firemuffin303.muffinseffectrenderapi.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EffectRendererImpl {
    private static final Font font = Minecraft.getInstance().font;

    public static final ResourceLocation WIDE_EFFECT_BACKGROUND = new ResourceLocation("muffins_era","textures/custom_effects/wide_bg.png");
    public static final ResourceLocation WIDE_EFFECT_OVERLAY = new ResourceLocation("muffins_era","textures/custom_effects/wide_bg_overlay.png");

    public static final ResourceLocation SHORT_EFFECT_BACKGROUND = new ResourceLocation("muffins_era","textures/custom_effects/short_bg.png");
    public static final ResourceLocation SHORT_EFFECT_OVERLAY = new ResourceLocation("muffins_era","textures/custom_effects/short_bg_overlay.png");

    public static final ResourceLocation HUD_EFFECT_BACKGROUND = new ResourceLocation("muffins_era","textures/custom_effects/hud_bg.png");
    public static final ResourceLocation HUD_EFFECT_OVERLAY = new ResourceLocation("muffins_era","textures/custom_effects/hud_bg_overlay.png");


    private static final ResourceLocation EFFECT_TEXTURE = new ResourceLocation("muffins_era","textures/custom_effects/custom_effect.png");
    private static final ResourceLocation PLACEHOLDER = new ResourceLocation("muffins_era","textures/custom_effects/icons/placeholder.png");


    public static void specialEffectRenderBackground(GuiGraphics guiGraphics,int x ,int y, ResourceLocation texture){
        specialEffectRenderBackground(guiGraphics,x,y,texture,0,0,120,32,0xFFFFFF);

    }

    public static void specialEffectRenderBackground(GuiGraphics guiGraphics,int x ,int y, ResourceLocation texture,int color){
        specialEffectRenderBackground(guiGraphics,x,y,texture,0,0,120,32,color);

    }

    public static void specialEffectRenderBackground(GuiGraphics guiGraphics, int x, int y, ResourceLocation texture,int u,int v,int textureWidth,int textureHeight){
       specialEffectRenderBackground(guiGraphics, x, y, texture,u,v,textureWidth,textureHeight,0xFFFFFF);
    }


    public static void specialEffectRenderBackground(GuiGraphics guiGraphics, int x, int y, ResourceLocation texture,int u,int v,int textureWidth,int textureHeight,int color){
        if(texture == null){
            texture = EFFECT_TEXTURE;
        }

        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8)  & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        guiGraphics.setColor(r,g,b,1.0f);
        guiGraphics.blit(texture,x,y,u,v,textureWidth,textureHeight);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        guiGraphics.blit(texture,x,y,u,v,textureWidth,textureHeight);
    }

    public static void specialEffectRenderWideBackground(GuiGraphics guiGraphics, int x, int y,int color){
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8)  & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        guiGraphics.blit(WIDE_EFFECT_BACKGROUND,x,y,0,0,120,32,120,32);
        guiGraphics.setColor(r,g,b,1.0f);
        guiGraphics.blit(WIDE_EFFECT_OVERLAY,x,y,0,0,120,32,120,32);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
    }

    public static void specialEffectRenderShortBackground(GuiGraphics guiGraphics, int x, int y,int color){
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8)  & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        guiGraphics.blit(SHORT_EFFECT_BACKGROUND,x,y,0,0,32,32,32,32);
        guiGraphics.setColor(r,g,b,1.0f);
        guiGraphics.blit(SHORT_EFFECT_OVERLAY,x,y,0,0,32,32,32,32);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
    }

    public static void specialEffectRenderHUDBackground(GuiGraphics guiGraphics, int x, int y,int color,ResourceLocation icon){
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8)  & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        guiGraphics.blit(HUD_EFFECT_BACKGROUND,x,y,0,0,24,24,24,24);
        guiGraphics.setColor(r,g,b,1.0f);
        guiGraphics.blit(HUD_EFFECT_OVERLAY,x,y,0,0,24,24,24,24);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);

        if(icon == null){
            icon = PLACEHOLDER;
        }

        guiGraphics.blit(icon,x+3,y+3,0,0,18,18,18,18);
    }


    public static void specialEffectRenderLabel(GuiGraphics guiGraphics, int x, int y, Component component){
        Component defaultDetail = Component.empty();

        if(component != null){
            defaultDetail = component;
        }

        guiGraphics.drawString(font, defaultDetail,x + 10 + 18, y + 6, 16777215);
    }

    public static void specialEffectRenderDetail(GuiGraphics guiGraphics, int x, int y, CustomEffectRenderer customEffectRenderer,LocalPlayer localPlayer){
        Component defaultDetail = Component.empty();

        if(customEffectRenderer.getDetail(localPlayer) != null){
            defaultDetail = customEffectRenderer.getDetail(localPlayer);
        }

        guiGraphics.drawString(font,defaultDetail,x + 10 + 18, y+6+10,8355711);
    }

    public static void specialEffectRenderIcon(GuiGraphics guiGraphics,int x,int y,ResourceLocation resourceLocation,boolean wide){
        if(resourceLocation == null){
            resourceLocation = PLACEHOLDER;
        }

        guiGraphics.blit(resourceLocation,x + (wide ? 6 : 7), y + 7, 0,0,18, 18,18,18);
    }

    public static void specialEffectRenderTooltips(GuiGraphics guiGraphics, int x, int y, CustomEffectRenderer customEffectRenderer, LocalPlayer localPlayer){

        List<Component> tooltipData = new ArrayList<>();

        if(customEffectRenderer.getName(localPlayer) != null){
            tooltipData.add(customEffectRenderer.getName(localPlayer));
        }

        if(customEffectRenderer.getDetail(localPlayer) != null){
            tooltipData.add(customEffectRenderer.getDetail(localPlayer));
        }

        guiGraphics.renderTooltip(font,tooltipData, Optional.empty(),x,y);
    }
}
