package kyne.xpessence.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockXPGem extends ItemBlock {

    public ItemBlockXPGem(final Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add("Can replace bookshelves near enchanting tables");
        list.add("Provides a bit of light");
    }
}
