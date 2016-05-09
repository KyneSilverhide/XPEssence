package kyne.xpessence.slots;

import kyne.xpessence.utils.FuelUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FuelSlot extends Slot {

    public FuelSlot(final IInventory inventoryIn, final int index, final int xPosition, final int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(final ItemStack stack) {
        return FuelUtils.isFuel(stack);
    }
}
