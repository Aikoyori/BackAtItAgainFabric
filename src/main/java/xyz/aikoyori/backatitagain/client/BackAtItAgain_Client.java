package xyz.aikoyori.backatitagain.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import xyz.aikoyori.backatitagain.utils.RegistryHelper;

public class BackAtItAgain_Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(RegistryHelper.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(RegistryHelper.makeID("old_timey"),  modContainer, ResourcePackActivationType.NORMAL);
        });
    }
}
