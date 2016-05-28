package kyne.xpessence.slots.base;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BasicSlot extends Slot {

    private final SlotDefinition slotDefinition;

    public BasicSlot(final IInventory inventoryIn, final int index, final SlotDefinition slotDefinition, final int posX, final int posY) {
        super(inventoryIn, index, posX, posY);
        this.slotDefinition = slotDefinition;
    }

    @Override
    public boolean isItemValid(final ItemStack stack) {
        return slotDefinition.isItemValid(stack);
    }
}
