package kyne.xpessence.items.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public abstract class ItemBlockXPFuel extends ItemBlock {

    public ItemBlockXPFuel(final Block block) {
        super(block);
    }

    public abstract int getInfusingPower();
}
