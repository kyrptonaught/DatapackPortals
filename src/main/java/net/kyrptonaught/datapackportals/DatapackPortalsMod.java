package net.kyrptonaught.datapackportals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.kyrptonaught.customportalapi.PerWorldPortals;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.kyrptonaught.customportalapi.util.ColorUtil;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class DatapackPortalsMod implements ModInitializer {
    public static final String MOD_ID = "datapackportals";
    public static List<PortalTypeRecord> PortalTypeRegisters = new ArrayList<>();

    @Override
    public void onInitialize() {
        registerPortalType("portals", DefaultPortalData.class);
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

    public static class DefaultPortalData implements PortalData {
        public String block;
        public String ignitionType;
        public String ignitionSource;
        public String dim;
        public String returnDim;
        public int r, g, b;

        @Override
        public PortalLink toLink(Identifier identifier) {
            if (block == null) DatapackPortalsMod.logerror(identifier + " missing field: block");
            if (ignitionType == null) DatapackPortalsMod.logerror(identifier + " missing field: ignitionType");
            if (ignitionSource == null) DatapackPortalsMod.logerror(identifier + " missing field: ignitionSource");
            if (dim == null) DatapackPortalsMod.logerror(identifier + " missing field: dim");

            PortalLink link = new PortalLink(new Identifier(block), new Identifier(dim), ColorUtil.getColorFromRGB(r, g, b));
            if (ignitionType.equalsIgnoreCase("block"))
                link.portalIgnitionSource = PortalIgnitionSource.FIRE;
            else if (ignitionType.equalsIgnoreCase("fluid"))
                link.portalIgnitionSource = PortalIgnitionSource.FluidSource(Registry.FLUID.get(new Identifier(ignitionSource)));
            else if (ignitionType.equalsIgnoreCase("item"))
                link.portalIgnitionSource = PortalIgnitionSource.ItemUseSource(Registry.ITEM.get(new Identifier(ignitionSource)));

            if (returnDim != null) link.returnDimID = new Identifier(returnDim);
            return link;
        }
    }
}
