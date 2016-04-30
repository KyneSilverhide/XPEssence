package kyne.xpessence.items;

import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.tab.ModTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemXPSeed extends ItemSeeds {

    public ItemXPSeed() {
        super(ModBlocks.xpCropBlock, Blocks.farmland);
        this.setUnlocalizedName("xp_seed");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return true;
    }
}
