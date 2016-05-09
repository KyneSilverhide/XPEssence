package kyne.xpessence.gui;

import kyne.xpessence.containers.ContainerInfuser;
import kyne.xpessence.tileentities.TileEntityInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
                                      final int y, final int z) {
        final BlockPos xyz = new BlockPos(x, y, z);
        final TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity != null) {
            if (ID == GUI.INFUSER) {
                return new ContainerInfuser(player.inventory, (IInventory) tileEntity);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
                                      final int y, final int z) {
        final BlockPos xyz = new BlockPos(x, y, z);
        final TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity != null) {
            if (ID == GUI.INFUSER) {
                final TileEntityInfuser tileEntityInfuser = (TileEntityInfuser) tileEntity;
                return new GUIInfuser(player.inventory, tileEntityInfuser);
            }
        }
        return null;
    }

}