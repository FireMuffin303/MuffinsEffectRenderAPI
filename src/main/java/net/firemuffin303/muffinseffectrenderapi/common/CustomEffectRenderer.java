package net.firemuffin303.muffinseffectrenderapi.common;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface CustomEffectRenderer {

    boolean shouldRender();

    Component getName();

    Component getDetail();

    ResourceLocation backgroundTextureWide();

    ResourceLocation backgroundTextureShort();

    ResourceLocation backgroundTextureHUD();

    ResourceLocation iconTexture();

    void renderTooltip();

    void renderBG(GuiGraphics guiGraphics,int x,int y,boolean wide);

    void renderIcon();

    void renderDetail(Font font, GuiGraphics guiGraphics, int x, int y, int color);

}
