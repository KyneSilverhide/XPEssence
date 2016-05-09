package kyne.xpessence.slots;

import kyne.xpessence.recipes.ModInfusingRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class InfusingSlot extends Slot {

    public InfusingSlot(final IInventory inventoryIn, final int index, final int xPosition, final int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(final ItemStack stack) {
        return ModInfusingRecipes.getInfusingResults(stack) != null;
    }
}
