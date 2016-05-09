package kyne.xpessence.blocks;

import kyne.xpessence.tab.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXPGemGlass extends Block {

    public BlockXPGemGlass() {
        super(Material.glass);
        this.setUnlocalizedName("xp_gemglass_block");
        this.setCreativeTab(ModTabs.creativeTab);
        this.setLightLevel(0.7F);
        this.setHardness(1F);
        this.setStepSound(Block.soundTypeGlass);
        this.setResistance(5F);
        this.setLightOpacity(0);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        final IBlockState iblockstate = worldIn.getBlockState(pos);
        final Block block = iblockstate.getBlock();
        // Taken from BlockBreakable implementation (ex: Glass)
        return worldIn.getBlockState(pos.offset(
                side.getOpposite())) != iblockstate || block != this && block != this && super.shouldSideBeRendered(
                worldIn, pos, side);

    }
}
