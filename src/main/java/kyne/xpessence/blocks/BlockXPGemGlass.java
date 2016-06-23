package kyne.xpessence.blocks;

import kyne.xpessence.tab.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXPGemGlass extends Block {

    public BlockXPGemGlass() {
        super(Material.ICE);
        this.setUnlocalizedName("xp_gemglass_block");
        this.setCreativeTab(ModTabs.creativeTab);
        this.setLightLevel(0.7F);
        this.setHardness(1F);
        this.setSoundType(SoundType.GLASS);
        this.setResistance(5F);
        this.setLightOpacity(0);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube(final IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos,
                                        final EnumFacing side) {
        final IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        final Block block = iblockstate.getBlock();
        return block != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
