package net.kyrptonaught.datapackportals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.PerWorldPortals;
import net.kyrptonaught.customportalapi.networking.PortalRegistrySync;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.block.Block;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;

public class DatapackPortalsMod implements ModInitializer {
    public static final String MOD_ID = "datapackportals";

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new PortalLinkDataPackLoader());
        PortalRegistrySync.enableSyncOnPlayerJoin();
    }

    public static void registerDatapackPortal(PortalLink portalLink) {
        PerWorldPortals.registerWorldPortal(portalLink);
    }

    public static void logerror(String message) {
        System.out.println("[" + MOD_ID + "]: " + message);
    }
}