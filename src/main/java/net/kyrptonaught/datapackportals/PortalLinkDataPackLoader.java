package net.kyrptonaught.datapackportals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.kyrptonaught.datapackportals.portalTypes.PortalData;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

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
            Map<Identifier, Resource> resources = manager.findResources(portalType.folderName(), (identifier) -> identifier.getPath().endsWith(".json"));
            for (Identifier id : resources.keySet()) {
                try {
                    JsonObject jsonObj = (JsonObject) JsonParser.parseReader(new InputStreamReader(resources.get(id).getInputStream()));
                    PortalLink portalLink = ((PortalData) GSON.fromJson(jsonObj, portalType.Deserializer())).toLink(id);
                    DatapackPortalsMod.registerDatapackPortal(portalLink);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
