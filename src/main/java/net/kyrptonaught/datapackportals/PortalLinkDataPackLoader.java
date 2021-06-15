package net.kyrptonaught.datapackportals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class PortalLinkDataPackLoader implements SimpleSynchronousResourceReloadListener {
    public static final Identifier ID = new Identifier(DatapackPortalsMod.MOD_ID, "portal_json");
    private static final Gson GSON = (new GsonBuilder()).create();

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void apply(ResourceManager manager) {
        Collection<Identifier> resources = manager.findResources("portals", (string) -> string.endsWith(".json"));
        for (Identifier id : resources) {
            try {
                JsonParser JsonParser = new JsonParser();
                JsonObject jsonObj = (JsonObject) JsonParser.parse(new InputStreamReader(manager.getResource(id).getInputStream()));
                PortalLink portalLink = GSON.fromJson(jsonObj, PortalData.class).toLink(id);

                DatapackPortalsMod.registerDatapackPortal(portalLink);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class PortalData {
        public String block;
        public String ignitionType;
        public String ignitionSource;
        public String dim;
        public String returnDim;
        public int r, g, b;

        public PortalLink toLink(Identifier identifier) {
            if (block == null) DatapackPortalsMod.logerror(identifier + " missing field: block");
            if (ignitionType == null) DatapackPortalsMod.logerror(identifier + " missing field: ignitionType");
            if (ignitionSource == null) DatapackPortalsMod.logerror(identifier + " missing field: ignitionSource");
            if (dim == null) DatapackPortalsMod.logerror(identifier + " missing field: dim");

            PortalLink link = new PortalLink(new Identifier(block), new Identifier(dim), CustomPortalApiRegistry.getColorFromRGB(r, g, b));
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
