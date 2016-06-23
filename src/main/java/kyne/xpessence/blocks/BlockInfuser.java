package kyne.xpessence.blocks;

import kyne.xpessence.blocks.base.BasicMachine;
import kyne.xpessence.gui.GUIConstants;
import kyne.xpessence.tileentities.TileEntityInfuser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockInfuser extends BasicMachine {

    public BlockInfuser(final boolean infusing) {
        super(infusing, "xp_infuser", GUIConstants.INFUSER);
        this.setTickRandomly(true);
        this.setLightLevel(isActive() ? 0.875F : 0);
    }

    @Override
    public ItemStack getPickBlock(final IBlockState state, final RayTraceResult target, final World world, final BlockPos pos,
                                  final EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(ModBlocks.xpCrucibleOff));
    }

    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityInfuser();
    }
}