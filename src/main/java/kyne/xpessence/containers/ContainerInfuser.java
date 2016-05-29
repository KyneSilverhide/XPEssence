package kyne.xpessence.containers;

import kyne.xpessence.containers.base.BasicContainer;
import kyne.xpessence.recipes.ModInfusingRecipes;
import kyne.xpessence.slots.SlotDefinitions;
import kyne.xpessence.slots.base.BasicSlot;
import kyne.xpessence.tileentities.InfuserContentConfig;
import kyne.xpessence.utils.FuelUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerInfuser extends BasicContainer {

    public ContainerInfuser(final InventoryPlayer playerInventory, final IInventory tileEntity) {
        super(tileEntity);

        this.addSlotToContainer(new BasicSlot(tileEntity, InfuserContentConfig.INPUT_SLOT, SlotDefinitions.infusingDefinition, 56, 17));
        this.addSlotToContainer(new BasicSlot(tileEntity, InfuserContentConfig.FUEL_SLOT, SlotDefinitions.fuelDefinition, 56, 53));
        this.addSlotToContainer(new BasicSlot(tileEntity, InfuserContentConfig.OUTPUT_SLOT, SlotDefinitions.outputDefinition, 116, 35));

        addPlayerInventory(playerInventory);
        addPlayerToolbar(playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int slotIndex) {
        final int sizeInventory = getSizeInventory();

        ItemStack itemStack1 = null;
        final Slot slot = inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemStack2 = slot.getStack();
            itemStack1 = itemStack2.copy();

            if (slotIndex == InfuserContentConfig.OUTPUT_SLOT) {
                if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory + 36, true)) {
                    return null;
                }
                slot.onSlotChange(itemStack2, itemStack1);
            } else if (slotIndex != InfuserContentConfig.INPUT_SLOT && slotIndex != InfuserContentConfig.FUEL_SLOT) {
                if (ModInfusingRecipes.getInfusingResults(itemStack2) != null) {
                    if (!mergeItemStack(itemStack2, 0, 1, false)) {
                        return null;
                    }
                } else if(FuelUtils.isFuel(itemStack2)) {
                    if (!mergeItemStack(itemStack2, 1, 2, false)) {
                        return null;
                    }
                } else if (slotIndex >= sizeInventory && slotIndex < sizeInventory + 27) {
                    if (!mergeItemStack(itemStack2, sizeInventory + 27, sizeInventory + 36, false)) {
                        return null;
                    }
                } else if (slotIndex >= sizeInventory + 27 && slotIndex < sizeInventory + 36 && !mergeItemStack(
                        itemStack2, sizeInventory + 1, sizeInventory + 27, false)) {
                    return null;
                }
            } else if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory + 36, false)) {
                return null;
            }

            if (itemStack2.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
            if (itemStack2.stackSize == itemStack1.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(playerIn, itemStack2);
        }
        return itemStack1;
    }
}