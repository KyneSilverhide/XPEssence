package kyne.xpessence.blocks;

import kyne.xpessence.blocks.base.BasicMachine;
import kyne.xpessence.gui.GUI;
import kyne.xpessence.tileentities.TileEntityCrucible;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrucible extends BasicMachine {

    public BlockCrucible(final boolean active) {
        super(active, "xp_crucible", GUI.CRUCIBLE);
        this.setTickRandomly(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(final World worldIn, final BlockPos pos) {
        return Item.getItemFromBlock(ModBlocks.xpCrucibleOff);
    }

    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityCrucible();
    }

    @Override
    public int getLightValue(final IBlockAccess world, final BlockPos pos) {
        return isActive() ? ModBlocks.xpGemBlock.getLightValue() : 0;
    }
}