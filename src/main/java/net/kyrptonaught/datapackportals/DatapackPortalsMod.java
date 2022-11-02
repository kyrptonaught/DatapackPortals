package net.kyrptonaught.datapackportals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.kyrptonaught.customportalapi.PerWorldPortals;
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

    }

    public static void registerDatapackPortal(PortalLink portalLink) {
        PerWorldPortals.registerWorldPortal(portalLink);
    }

    public static <T extends PortalData> void registerPortalType(String folderPath, Class<T> deserializer) {
        PortalTypeRegisters.add(new PortalTypeRecord(folderPath, deserializer));
    }

    public static void logerror(String message) {
        System.out.println("[" + MOD_ID + "]: " + message);
    }
}
