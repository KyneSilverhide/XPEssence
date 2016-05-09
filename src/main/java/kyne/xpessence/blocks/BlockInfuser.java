package kyne.xpessence.blocks;

import kyne.xpessence.blocks.base.BasicMachine;
import kyne.xpessence.gui.GUI;
import kyne.xpessence.tileentities.TileEntityInfuser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInfuser extends BasicMachine {

    private static boolean keepInventory = false;

    public BlockInfuser(final boolean infusing) {
        super(infusing, "xp_infuser", Item.getItemFromBlock(ModBlocks.xpInfuserOff), GUI.INFUSER);
    }

    public static void setState(final boolean active, final World worldIn, final BlockPos pos) {
        final IBlockState iblockstate = worldIn.getBlockState(pos);
        final TileEntity tileentity = worldIn.getTileEntity(pos);

        keepInventory = true;
        if (active) {
            worldIn.setBlockState(pos,
                    ModBlocks.xpInfuserOn.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        } else {
            worldIn.setBlockState(pos,
                    ModBlocks.xpInfuserOff.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        keepInventory = false;
        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!keepInventory) {
            final TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityInfuser) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityInfuser) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
            super.breakBlock(worldIn, pos, state);
        }
    }

    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityInfuser();
    }

    @Override
    public int getLightValue(final IBlockAccess world, final BlockPos pos) {
        return isActive() ? ModBlocks.xpGemBlock.getLightValue() : 0;
    }
}