package kyne.xpessence.containers;

import kyne.xpessence.containers.base.BasicContainer;
import kyne.xpessence.slots.SlotDefinitions;
import kyne.xpessence.slots.base.BasicSlot;
import kyne.xpessence.tileentities.CrucibleContentConfig;
import kyne.xpessence.utils.FuelUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrucible extends BasicContainer {

    private final int sizeInventory;

    public ContainerCrucible(final InventoryPlayer playerInventory, final IInventory tileEntity) {
        super(tileEntity);
        this.sizeInventory = tileEntity.getSizeInventory();
        this.addSlotToContainer(new BasicSlot(tileEntity, CrucibleContentConfig.INPUT_SLOT, SlotDefinitions.fuelDefinition, 22, 35));
        this.addSlotToContainer(new BasicSlot(tileEntity, CrucibleContentConfig.BUCKET_SLOT, SlotDefinitions.bucketDefinition, 138, 35));

        addPlayerInventory(playerInventory);
        addPlayerToolbar(playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int slotIndex) {
        ItemStack itemStack1 = null;
        final Slot slot = inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemStack2 = slot.getStack();
            itemStack1 = itemStack2.copy();

            if (slotIndex == CrucibleContentConfig.BUCKET_SLOT) {
                if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory + 36, true)) {
                    return null;
                }
                slot.onSlotChange(itemStack2, itemStack1);
            } else if (slotIndex != CrucibleContentConfig.INPUT_SLOT ) {
                if (FuelUtils.isFuel(itemStack2)) {
                    if (!mergeItemStack(itemStack2, 0, 1, false)) {
                        return null;
                    }
                } else if(Items.BUCKET == itemStack2.getItem()) {
                    if (!mergeItemStack(itemStack2, 1, 2, false)) {
                        return null;
                    }
                } else {
                    if (mergeToolbarAndInventory(slotIndex, itemStack2))
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