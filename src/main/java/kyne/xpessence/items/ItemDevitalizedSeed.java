package kyne.xpessence.items;

import kyne.xpessence.tab.ModTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemDevitalizedSeed extends Item {

    public ItemDevitalizedSeed() {
        this.setUnlocalizedName("devitalized_seed");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List<String> tooltip, final boolean advanced) {
        tooltip.add("These seeds could be revitalized in the infuser");
    }
}
