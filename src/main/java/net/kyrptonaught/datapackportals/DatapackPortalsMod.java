package net.kyrptonaught.datapackportals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.block.Block;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;

public class DatapackPortalsMod implements ModInitializer {
    public static final String MOD_ID = "datapackportals";
    public static HashSet<Block> datapackPortals = new HashSet<>();

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new PortalLinkDataPackLoader());
    }

    public static void removeOldPortalsFromRegistry() {
        for (Block block : datapackPortals) {
            if (CustomPortalApiRegistry.portals.containsKey(block))
                CustomPortalApiRegistry.portals.remove(block);
        }
        datapackPortals.clear();
    }

    public static void registerDatapackPortal(PortalLink portalLink) {
        Block blockId = Registry.BLOCK.get(portalLink.block);
        if (!datapackPortals.contains(blockId))
            datapackPortals.add(blockId);
        CustomPortalApiRegistry.addPortal(blockId, portalLink);
    }
}