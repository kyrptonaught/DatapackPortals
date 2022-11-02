package net.kyrptonaught.datapackportals.portalTypes;

import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.kyrptonaught.customportalapi.util.ColorUtil;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.kyrptonaught.datapackportals.DatapackPortalsMod;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DefaultPortal implements PortalData {
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