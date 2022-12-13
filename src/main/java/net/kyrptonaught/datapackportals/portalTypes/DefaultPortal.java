package net.kyrptonaught.datapackportals.portalTypes;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
public class DefaultPortal implements PortalData {
    public String block;
    public String ignitionType;
    public String ignitionSource;
    public String dim;
    public String returnDim;
    public String portalType;
    public int r, g, b;

    @Override
    public PortalLink toLink(Identifier identifier) {
        PortalLink link = new PortalLink();
        CustomPortalBuilder builder = CustomPortalBuilder.beginPortal(link)
                .frameBlock(new Identifier(block))
                .destDimID(new Identifier(dim))
                .tintColor(r, g, b);

        if (ignitionType.equalsIgnoreCase("fluid"))
            builder.lightWithFluid(Registries.FLUID.get(new Identifier(ignitionSource)));
        else if (ignitionType.equalsIgnoreCase("item"))
            builder.lightWithItem(Registries.ITEM.get(new Identifier(ignitionSource)));

        if (returnDim != null)
            builder.returnDim(new Identifier(returnDim), false);

        if (portalType != null && portalType.equalsIgnoreCase("flat"))
            builder.flatPortal();

        return link;
    }
}