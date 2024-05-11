package xyz.aikoyori.backatitagain.utils;

import net.minecraft.util.Identifier;

public class RegistryHelper {
    public static String MOD_ID = "backatitagain";
    public static Identifier makeID(String location){
        return new Identifier(MOD_ID,location);
    }
}
