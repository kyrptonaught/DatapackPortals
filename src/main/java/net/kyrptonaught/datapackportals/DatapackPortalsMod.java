package net.kyrptonaught.datapackportals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.PerWorldPortals;
import net.kyrptonaught.customportalapi.networking.PortalRegistrySync;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.kyrptonaught.datapackportals.portalTypes.CMDPortal;
import net.kyrptonaught.datapackportals.portalTypes.DefaultPortal;
import net.kyrptonaught.datapackportals.portalTypes.PortalData;
import net.minecraft.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class DatapackPortalsMod implements ModInitializer {
    public static final String MOD_ID = "datapackportals";
    public static List<PortalTypeRecord> PortalTypeRegisters = new ArrayList<>();

    @Override
    public void onInitialize() {
        registerPortalType("portals", DefaultPortal.class);
        DatapackPortalsMod.registerPortalType("cmdportals", CMDPortal.class);
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new PortalLinkDataPackLoader());

        /*
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            for (PortalLink link : CustomPortalApiRegistry.getAllPortalLinks()) {
                sender.sendPacket(PortalRegistrySync.createPacket(link));
            }
        });

         */
    }

    public static void registerDatapackPortal(PortalLink portalLink) {
        PerWorldPortals.registerWorldPortal(portalLink);
    }

    public static <T extends PortalData> void registerPortalType(String folderPath, Class<T> deserializer) {
        PortalTypeRegisters.add(new PortalTypeRecord(folderPath, deserializer));
    }

}
