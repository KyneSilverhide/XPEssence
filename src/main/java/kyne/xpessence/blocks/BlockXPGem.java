package kyne.xpessence.blocks;

import kyne.xpessence.tab.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockXPGem extends Block {

    public BlockXPGem() {
        super(Material.ICE);
        this.setUnlocalizedName("xp_gem_block").setCreativeTab(ModTabs.creativeTab).setLightLevel(0.7F).setHardness(
                3F).setResistance(15F).setLightOpacity(8).setHarvestLevel("pickaxe", 1);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return 1;
    }
}
