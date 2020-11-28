package net.kyrptonaught.customportals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class PortalLinkDataPackLoader implements SimpleSynchronousResourceReloadListener {
    public static final Identifier ID = new Identifier(CustomPortalsMod.MOD_ID, "portal_json");
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
                PortalLink portalLink = GSON.fromJson(jsonObj, PortalData.class).toLink();
                net.kyrptonaught.customportalapi.CustomPortalsMod.portals.put(Registry.BLOCK.get(portalLink.block), portalLink);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class PortalData {
        String block;
        String dim;
        int color;

        public PortalLink toLink() {
            return new PortalLink(new Identifier(block), new Identifier(dim), color);
        }
    }
}
