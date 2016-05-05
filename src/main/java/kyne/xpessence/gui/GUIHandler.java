package kyne.xpessence.gui;

import kyne.xpessence.containers.ContainerXPFurnace;
import kyne.xpessence.tileentities.TileEntityXPFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
    private static final int GUIID_MBE_31 = 31;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != getGuiID()) {
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof TileEntityXPFurnace) {
            TileEntityXPFurnace tileInventoryFurnace = (TileEntityXPFurnace) tileEntity;
            return new ContainerXPFurnace(player.inventory, tileInventoryFurnace);
        }
        return null;
    }

    public static int getGuiID() {
        return GUIID_MBE_31;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != getGuiID()) {
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof TileEntityXPFurnace) {
            TileEntityXPFurnace tileInventoryFurnace = (TileEntityXPFurnace) tileEntity;
            return new GUIXPFurnace(player.inventory, tileInventoryFurnace);
        }
        return null;
    }

}