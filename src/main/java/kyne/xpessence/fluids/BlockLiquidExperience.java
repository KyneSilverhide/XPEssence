package kyne.xpessence.fluids;

import kyne.xpessence.fluids.base.BlockFluidInteractive;
import kyne.xpessence.utils.BlockMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLiquidExperience extends BlockFluidInteractive {

    private static final int BLOCK_UPDATE_AND_SEND_CLIENT = 3;
    public static final int LIQUID_EFFECT_RADIUS = 6;

    public BlockLiquidExperience() {
        super(ModFluids.liquidExperience, Material.water);
        addInteraction(Blocks.dirt, IGNORE_METADATA, Blocks.dirt, 2);
        addInteraction(Blocks.grass, Blocks.farmland);
    }

    @Override
    public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
        applyInteractionsNear(world, pos);
        super.updateTick(world, pos, state, rand);
    }

    protected void applyInteractionsNear(final World world, final BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            final double gaussX = world.rand.nextGaussian() * LIQUID_EFFECT_RADIUS / 2;
            final double gaussZ = world.rand.nextGaussian() * LIQUID_EFFECT_RADIUS / 2;
            if(Math.abs(gaussX) <= LIQUID_EFFECT_RADIUS / 2 && Math.abs(gaussZ) <= LIQUID_EFFECT_RADIUS) {
                final BlockPos candidatePosition = new BlockPos(pos.getX() + gaussX, pos.getY(), pos.getZ() + gaussZ);
                triggerInteractionEffects(world, pos.getX(), pos.getY(), pos.getZ());
                interactWithBlock(world, candidatePosition);
            }
        }
    }

    protected void interactWithBlock(final World world, final BlockPos pos) {

        final Block block = world.getBlockState(pos).getBlock();
        if (block == Blocks.air || block == this) {
            return;
        }

        final IBlockState blockState = world.getBlockState(pos);
        final int bMeta = blockState.getBlock().getMetaFromState(blockState);
        final BlockMetadata resultBlock;

        if (hasInteraction(block, bMeta)) {
            resultBlock = getInteraction(block, bMeta);
            final IBlockState newBlockState = resultBlock.getBlock().getStateFromMeta(resultBlock.getMetadata());
            world.setBlockState(pos, newBlockState, BLOCK_UPDATE_AND_SEND_CLIENT);
        }
    }

    protected void triggerInteractionEffects(final World world, final int x, final int y, final int z) {
        world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, x, y + 1.3D, z, 0.0D, 0.0D, 0.0D);
    }
}
