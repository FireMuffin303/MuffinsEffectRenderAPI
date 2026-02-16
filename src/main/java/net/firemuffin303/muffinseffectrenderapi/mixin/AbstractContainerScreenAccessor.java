package net.firemuffin303.muffinseffectrenderapi.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessor {

    @Accessor("leftPos")
    int getLeftPos();

    @Accessor("imageWidth")
    int getImageWidth();

    @Accessor("topPos")
    int getTopPos();

}
