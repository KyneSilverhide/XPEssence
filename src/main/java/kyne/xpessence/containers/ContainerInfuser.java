package kyne.xpessence.containers;

import kyne.xpessence.recipes.ModInfusingRecipes;
import kyne.xpessence.tileentities.TileEntityInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerInfuser extends Container {
    private final IInventory tileFurnace;
    private final int sizeInventory;
    private int ticksGrindingItemSoFar;
    private int ticksPerItem;
    private int timeCanGrind;

    public ContainerInfuser(final InventoryPlayer playerInventory, final IInventory furnaceInventory) {
        this.tileFurnace = furnaceInventory;
        this.sizeInventory = tileFurnace.getSizeInventory();
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 56, 17));
        this.addSlotToContainer(new Slot(furnaceInventory, 1, 56, 53));
        this.addSlotToContainer(new SlotXPOutput(furnaceInventory, 2, 116, 35));

        addPlayerInventory(playerInventory);
        addPlayerToolbar(playerInventory);
    }

    private void addPlayerToolbar(final InventoryPlayer playerInventory) {
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    private void addPlayerInventory(final InventoryPlayer playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (final ICrafting icrafting : crafters) {
            if (ticksGrindingItemSoFar != tileFurnace.getField(2)) {
                icrafting.sendProgressBarUpdate(this, 2, tileFurnace.getField(2));
            }

            if (timeCanGrind != tileFurnace.getField(0)) {
                icrafting.sendProgressBarUpdate(this, 0, tileFurnace.getField(0));
            }

            if (ticksPerItem != tileFurnace.getField(3)) {
                icrafting.sendProgressBarUpdate(this, 3, tileFurnace.getField(3));
            }
        }

        ticksGrindingItemSoFar = tileFurnace.getField(2);
        timeCanGrind = tileFurnace.getField(0);
        ticksPerItem = tileFurnace.getField(3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int id, final int data) {
        tileFurnace.setField(id, data);
    }

    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        return tileFurnace.isUseableByPlayer(playerIn);
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
            } else if (slotIndex != TileEntityInfuser.INPUT_SLOT) {
                // check if there is a grinding recipe for the stack
                if (ModInfusingRecipes.getInfusingResults(itemStack2) != null) {
                    if (!mergeItemStack(itemStack2, 0, 1, false)) {
                        return null;
                    }
                } else if (slotIndex >= sizeInventory && slotIndex < sizeInventory + 27) {
                    if (!mergeItemStack(itemStack2, sizeInventory + 27, sizeInventory + 36, false)) {
                        return null;
                    }
                } else if (slotIndex >= sizeInventory + 27 && slotIndex < sizeInventory + 36 && !mergeItemStack(itemStack2,
                        sizeInventory + 1, sizeInventory + 27, false)) {
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