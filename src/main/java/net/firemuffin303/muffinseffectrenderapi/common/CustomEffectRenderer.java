package net.firemuffin303.muffinseffectrenderapi.common;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface CustomEffectRenderer {

    boolean shouldRender(LocalPlayer player);

    Component getName(LocalPlayer player);

    Component getDetail(LocalPlayer player);

    ResourceLocation backgroundTextureWide(LocalPlayer player);

    ResourceLocation backgroundTextureShort(LocalPlayer player);

    ResourceLocation backgroundTextureHUD(LocalPlayer player);

    ResourceLocation iconTexture(LocalPlayer player);

    default int color(LocalPlayer player){
        return 0xFFFFFF;
    }

}
