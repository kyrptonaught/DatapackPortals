package net.kyrptonaught.datapackportals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
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
        DatapackPortalsMod.removeOldPortalsFromRegistry();
        Collection<Identifier> resources = manager.findResources("portals", (string) -> string.endsWith(".json"));
        for (Identifier id : resources) {
            try {
                JsonParser JsonParser = new JsonParser();
                JsonObject jsonObj = (JsonObject) JsonParser.parse(new InputStreamReader(manager.getResource(id).getInputStream()));
                PortalLink portalLink = GSON.fromJson(jsonObj, PortalData.class).toLink();

                DatapackPortalsMod.registerDatapackPortal(portalLink);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class PortalData {
        public String block;
        public String ignitionBlock;
        public String dim;
        public String returnDim;
        public int r, g, b;

        public PortalLink toLink() {
            PortalLink link = new PortalLink(new Identifier(block), new Identifier(dim), CustomPortalApiRegistry.getColorFromRGB(r, g, b));
            if (ignitionBlock != null) link.ignitionBlock = new Identifier(ignitionBlock);
            if (returnDim != null) link.returnDimID = new Identifier(returnDim);
            return link;
        }
    }
}
