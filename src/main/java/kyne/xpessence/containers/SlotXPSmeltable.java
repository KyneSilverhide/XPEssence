package kyne.xpessence.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotXPSmeltable extends Slot {

    public SlotXPSmeltable(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
//        System.out.println("stack = [" + stack + "]");
//        return TileEntityXPFurnace.isSmeltableItem(stack);
        return true;
    }
}
