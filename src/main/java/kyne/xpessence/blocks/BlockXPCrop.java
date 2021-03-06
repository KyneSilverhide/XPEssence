package kyne.xpessence.blocks;

import kyne.xpessence.blocks.base.BasicCrop;
import kyne.xpessence.items.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockXPCrop extends BasicCrop {

    public BlockXPCrop() {
        this.setUnlocalizedName("xp_crop_block");
    }

    @Override
    public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state,
                                    final int fortune) {
        final List<ItemStack> drops = new ArrayList<ItemStack>();
        if (fullyGrown(state)) {
            drops.add(new ItemStack(ModItems.devitalizedSeeds));
            final Random rand = getRandom(world);
            final int dropAmount = 3 + fortune;
            for (int i = 0; i < dropAmount; i++) {
                if (rand.nextInt(3) < 1) {
                    drops.add(new ItemStack(ModItems.xpGem));
                }
            }
        }
        return drops;
    }
}