package kyne.xpessence.tileentities;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityXPFurnace.class, "xp_furnace");
    }
}


