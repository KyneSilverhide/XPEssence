package kyne.xpessence.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockEssenceTorch extends ItemBlock {

    public ItemBlockEssenceTorch(final Block block) {
        super(block);
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
        list.add("An alternative to the vanilla torch");
    }
}
