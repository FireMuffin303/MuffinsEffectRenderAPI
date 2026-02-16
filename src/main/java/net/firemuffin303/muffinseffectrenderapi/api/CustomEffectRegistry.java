package net.firemuffin303.muffinseffectrenderapi.api;

import net.firemuffin303.muffinseffectrenderapi.common.CustomEffectRenderer;

import java.util.ArrayList;
import java.util.List;

public class CustomEffectRegistry {

    private static final List<CustomEffectRenderer> EFFECTS = new ArrayList<>();

    public static void init(){}

    public static void register(CustomEffectRenderer customEffectRenderer){
        EFFECTS.add(customEffectRenderer);
    }

    public static List<CustomEffectRenderer> getEffects(){
        return EFFECTS;
    }
}
