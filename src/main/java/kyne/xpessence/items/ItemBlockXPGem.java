package kyne.xpessence.items;

import kyne.xpessence.items.base.ItemBlockXPFuel;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockXPGem extends ItemBlockXPFuel {

    public ItemBlockXPGem(final Block block) {
        super(block);
    }

    @Override
    public int getInfusingPower() {
        return 2000;
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
        list.add("Can replace bookshelves near enchanting tables");
        list.add("Provides a bit of light");
    }
}
