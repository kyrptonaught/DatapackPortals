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
    public void reload(ResourceManager manager) {
        for (PortalTypeRecord portalType : DatapackPortalsMod.PortalTypeRegisters) {
            Collection<Identifier> resources = manager.findResources(portalType.folderName(), (string) -> string.endsWith(".json"));
            for (Identifier id : resources) {
                try {
                    JsonParser JsonParser = new JsonParser();
                    JsonObject jsonObj = (JsonObject) JsonParser.parse(new InputStreamReader(manager.getResource(id).getInputStream()));
                    PortalLink portalLink = ((PortalData) GSON.fromJson(jsonObj, portalType.Deserializer())).toLink(id);
                    DatapackPortalsMod.registerDatapackPortal(portalLink);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
