package kyne.xpessence.items;

import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.tab.ModTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemXPSeed extends ItemSeeds {

    public ItemXPSeed() {
        super(ModBlocks.xpCropBlock, Blocks.FARMLAND);
        this.setUnlocalizedName("xp_seed");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List<String> tooltip, final boolean advanced) {
        tooltip.add("Similar to other seeds, but grows XP Gems");
    }
}
