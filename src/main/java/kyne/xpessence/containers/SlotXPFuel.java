package kyne.xpessence.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotXPFuel extends Slot {

    public SlotXPFuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
//        System.out.println("stack = [" + stack + "]");
//        System.out.println(stack.getItem() instanceof ItemXPFuel || stack.getItem() instanceof ItemBlockXPFuel);
//        return stack.getItem() instanceof ItemXPFuel || stack.getItem() instanceof ItemBlockXPFuel;
        return true;
    }
}
