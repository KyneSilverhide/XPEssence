package kyne.xpessence.blocks;

import kyne.xpessence.blocks.base.BasicMachine;
import kyne.xpessence.gui.GUIConstants;
import kyne.xpessence.tileentities.TileEntityInfuser;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockInfuser extends BasicMachine {

    public BlockInfuser(final boolean infusing) {
        super(infusing, "xp_infuser", GUIConstants.INFUSER);
        this.setTickRandomly(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(final World worldIn, final BlockPos pos) {
        return Item.getItemFromBlock(ModBlocks.xpInfuserOff);
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