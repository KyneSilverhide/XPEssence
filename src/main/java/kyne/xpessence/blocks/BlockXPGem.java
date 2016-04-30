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

public class BlockXPGem extends Block {

    public BlockXPGem() {
        super(Material.ice);
        this.setUnlocalizedName("xp_gem_block")
                .setCreativeTab(ModTabs.creativeTab)
                .setLightLevel(0.7F)
                .setHardness(3F)
                .setStepSound(Block.soundTypeMetal)
                .setResistance(15F)
                .setLightOpacity(8)
                .setHarvestLevel("pickaxe", 1);
    }
}
