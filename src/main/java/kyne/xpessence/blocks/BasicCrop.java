package kyne.xpessence.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BasicCrop extends BlockBush implements IGrowable {

    public static final Integer MINSTAGE = 0;
    public static final Integer MAXSTAGE = 4;
    public static final PropertyInteger currentStage = PropertyInteger.create("stage", MINSTAGE, MAXSTAGE);

    public BasicCrop() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(currentStage, 0));
        this.setTickRandomly(true);

        final float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setStepSound(soundTypeGrass);
        this.setHardness(0f);
    }

    public Random getRandom(final IBlockAccess world) {
        return world instanceof World ? ((World) world).rand : new Random();
    }

    @Override
    public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        this.randomGrow(worldIn, rand, pos, state);
    }

    private void randomGrow(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state) {
        if (worldIn.getLightFromNeighbors(pos) >= 9) {
            if (state.getValue(currentStage) < MAXSTAGE) {
                final float growthChance = getGrowthChance(this, worldIn, pos);
                final int rNumber = rand.nextInt((int) (25F / growthChance) + 1);
                if (rNumber == 0) {
                    growNow(worldIn, pos, state);
                }
            }
        }
    }

    public float getGrowthChance(final Block blockIn, final World worldIn, final BlockPos pos) {
        float f = 1.0F;
        final BlockPos blockUnder = pos.down();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                final IBlockState iblockstate = worldIn.getBlockState(blockUnder.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(worldIn, blockUnder.add(i, 0, j), EnumFacing.UP, (IPlantable) blockIn)) {
                    f1 = 1.0F;
                    if (iblockstate.getBlock().isFertile(worldIn, blockUnder.add(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }
                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }
                f += f1;
            }
        }

        final BlockPos blockNorth = pos.north();
        final BlockPos blockSouth = pos.south();
        final BlockPos blockWest = pos.west();
        final BlockPos blockEast = pos.east();
        final boolean flag = blockIn == worldIn.getBlockState(blockWest).getBlock() || blockIn == worldIn.getBlockState(blockEast).getBlock();
        final boolean flag1 = blockIn == worldIn.getBlockState(blockNorth).getBlock() || blockIn == worldIn.getBlockState(blockSouth).getBlock();

        if (flag && flag1) {
            f /= 2.0F;
        } else {
            final boolean flag2 = blockIn == worldIn.getBlockState(blockWest.north()).getBlock()
                    || blockIn == worldIn.getBlockState(blockEast.north()).getBlock()
                    || blockIn == worldIn.getBlockState(blockEast.south()).getBlock()
                    || blockIn == worldIn.getBlockState(blockWest.south()).getBlock();

            if (flag2) {
                f /= 2.0F;
            }
        }
        return f;
    }

    private void growNow(final World worldIn, final BlockPos pos, final IBlockState state) {
        worldIn.setBlockState(pos, state.cycleProperty(currentStage), 2);
    }

    @Override
    public void grow(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state) {
        super.updateTick(worldIn, pos, state, rand);
        this.growNow(worldIn, pos, state);
    }

    public boolean fullyGrown(final IBlockState state) {
        return state.getValue(currentStage).equals(MAXSTAGE);
    }

    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(currentStage, meta);
    }

    @Override
    public int getMetaFromState(final IBlockState state) {
        return state.getValue(currentStage);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, currentStage);
    }

    @Override
    public boolean canGrow(final World worldIn, final BlockPos pos, final IBlockState state, final boolean isClient) {
        return state.getValue(currentStage) < MAXSTAGE;
    }

    @Override
    public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state) {
        return true;
    }
}