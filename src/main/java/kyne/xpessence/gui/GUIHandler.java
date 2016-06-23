package kyne.xpessence.gui;

import kyne.xpessence.containers.ContainerCrucible;
import kyne.xpessence.containers.ContainerInfuser;
import kyne.xpessence.tileentities.TileEntityCrucible;
import kyne.xpessence.tileentities.TileEntityInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x,
                                      final int y, final int z) {
        final BlockPos xyz = new BlockPos(x, y, z);
        final TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity != null) {
            if (ID == GUIConstants.INFUSER) {
                return new ContainerInfuser(player.inventory, (IInventory) tileEntity);
            } else if (ID == GUIConstants.CRUCIBLE) {
                return new ContainerCrucible(player.inventory, (IInventory) tileEntity);
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
            if (ID == GUIConstants.INFUSER) {
                final TileEntityInfuser tileEntityInfuser = (TileEntityInfuser) tileEntity;
                return new GUIInfuser(player.inventory, tileEntityInfuser);
            } else if (ID == GUIConstants.CRUCIBLE) {
                final TileEntityCrucible tileEntityCrucible = (TileEntityCrucible) tileEntity;
                return new GUICrucible(player.inventory, tileEntityCrucible);
            }
        }
        return null;
    }

}