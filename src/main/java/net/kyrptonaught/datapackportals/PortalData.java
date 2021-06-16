package net.kyrptonaught.datapackportals;

import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.util.Identifier;

public interface PortalData {
    PortalLink toLink(Identifier identifier);
}
