package kyne.xpessence.containers;

import kyne.xpessence.containers.base.BasicContainer;
import kyne.xpessence.recipes.ModInfusingRecipes;
import kyne.xpessence.slots.FuelSlot;
import kyne.xpessence.slots.InfusingSlot;
import kyne.xpessence.slots.OutputSlot;
import kyne.xpessence.tileentities.TileEntityInfuser;
import kyne.xpessence.utils.FuelUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerInfuser extends BasicContainer {

    private final int sizeInventory;
    private int ticksGrindingItemSoFar;
    private int ticksPerItem;
    private int timeCanGrind;

    public ContainerInfuser(final InventoryPlayer playerInventory, final IInventory furnaceInventory) {
        super(playerInventory, furnaceInventory);
        this.sizeInventory = getTileFurnace().getSizeInventory();
        this.addSlotToContainer(new InfusingSlot(furnaceInventory, 0, 56, 17));
        this.addSlotToContainer(new FuelSlot(furnaceInventory, 1, 56, 53));
        this.addSlotToContainer(new OutputSlot(furnaceInventory, 2, 116, 35));

        addPlayerInventory(playerInventory);
        addPlayerToolbar(playerInventory);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (final ICrafting icrafting : crafters) {
            if (ticksGrindingItemSoFar != getTileFurnace().getField(TileEntityInfuser.ITEM_INFUSING_STATUS)) {
                icrafting.sendProgressBarUpdate(this, TileEntityInfuser.ITEM_INFUSING_STATUS,
                        getTileFurnace().getField(TileEntityInfuser.ITEM_INFUSING_STATUS));
            }

            if (timeCanGrind != getTileFurnace().getField(TileEntityInfuser.INFUSING_FUEL_LEFT)) {
                icrafting.sendProgressBarUpdate(this, TileEntityInfuser.INFUSING_FUEL_LEFT,
                        getTileFurnace().getField(TileEntityInfuser.INFUSING_FUEL_LEFT));
            }

            if (ticksPerItem != getTileFurnace().getField(TileEntityInfuser.TIME_TO_INFUSE_ITEM)) {
                icrafting.sendProgressBarUpdate(this, TileEntityInfuser.TIME_TO_INFUSE_ITEM,
                        getTileFurnace().getField(TileEntityInfuser.TIME_TO_INFUSE_ITEM));
            }
        }

        ticksGrindingItemSoFar = getTileFurnace().getField(TileEntityInfuser.ITEM_INFUSING_STATUS);
        timeCanGrind = getTileFurnace().getField(TileEntityInfuser.INFUSING_FUEL_LEFT);
        ticksPerItem = getTileFurnace().getField(TileEntityInfuser.TIME_TO_INFUSE_ITEM);
    }

    @Override
    public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int slotIndex) {
        ItemStack itemStack1 = null;
        final Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            final ItemStack itemStack2 = slot.getStack();
            itemStack1 = itemStack2.copy();

            if (slotIndex == TileEntityInfuser.OUTPUT_SLOT) {
                if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory + 36, true)) {
                    return null;
                }
                slot.onSlotChange(itemStack2, itemStack1);
            } else if (slotIndex != TileEntityInfuser.INPUT_SLOT && slotIndex != TileEntityInfuser.FUEL_SLOT) {
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