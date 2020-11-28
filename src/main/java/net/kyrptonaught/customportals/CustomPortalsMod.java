package net.kyrptonaught.customportals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class CustomPortalsMod implements ModInitializer {
    public static final String MOD_ID = "customportals";
    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new PortalLinkDataPackLoader());
    }
}