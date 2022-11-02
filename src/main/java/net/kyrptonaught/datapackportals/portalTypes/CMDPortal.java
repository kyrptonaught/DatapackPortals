package net.kyrptonaught.datapackportals.portalTypes;

import net.kyrptonaught.customportalapi.util.PortalLink;
import net.kyrptonaught.customportalapi.util.SHOULDTP;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CMDPortal extends DefaultPortal {
    public String command;

    @Override
    public PortalLink toLink(Identifier identifier) {
        this.dim = "minecraft:overworld";
        PortalLink link = super.toLink(identifier);

        link.getBeforeTPEvent().register(entity -> {
            if (entity instanceof ServerPlayerEntity player) {
                player.getServer().getCommandManager().executeWithPrefix(player.getCommandSource(), command);
            }
            return SHOULDTP.CANCEL_TP;
        });
        return link;
    }
}