package kyne.xpessence.containers.base;

import kyne.xpessence.tileentities.base.BasicTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicContainer extends Container {

    private final BasicTileEntity tileEntity;
    private final int sizeInventory;
    private final Map<Integer, Integer> cachedEntityFields;

    public BasicContainer(final IInventory tileEntity) {
        this.tileEntity = (BasicTileEntity) tileEntity;
        this.sizeInventory = tileEntity.getSizeInventory();
        cachedEntityFields = new HashMap<Integer, Integer>();
    }

    public int getSizeInventory() {
        return sizeInventory;
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        final List<Integer> fieldIndexes = getTileEntity().getTileEntityContentConfig().getFieldIndexes();
        for (final Integer fieldIndex : fieldIndexes) {
            final Integer entityFieldValue = getTileEntity().getField(fieldIndex);
            final Integer cachedFieldValue = getCachedEntityFields().get(fieldIndex);
            if (!entityFieldValue.equals(cachedFieldValue)) {
                for (final IContainerListener icrafting : listeners) {
                    icrafting.sendProgressBarUpdate(this, fieldIndex, entityFieldValue);
                    updateCachedField(fieldIndex, entityFieldValue);
                }
            }
        }
    }

    public BasicTileEntity getTileEntity() {
        return tileEntity;
    }

    public Map<Integer, Integer> getCachedEntityFields() {
        return cachedEntityFields;
    }

    private void updateCachedField(final int fieldIndex, final int fieldValue) {
        cachedEntityFields.put(fieldIndex, fieldValue);
    }

    public void addPlayerInventory(final InventoryPlayer playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    public void addPlayerToolbar(final InventoryPlayer playerInventory) {
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int id, final int data) {
        tileEntity.setField(id, data);
    }

    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        return tileEntity.isUseableByPlayer(playerIn);
    }

    public boolean mergeToolbarAndInventory(final int slotIndex, final ItemStack itemStack2) {
        if (slotIndex >= sizeInventory && slotIndex < sizeInventory + 27) {
            if (!mergeItemStack(itemStack2, sizeInventory + 27, sizeInventory + 36, false)) {
                return true;
            }
        } else if (slotIndex >= sizeInventory + 27 && slotIndex < sizeInventory + 36 && !mergeItemStack(itemStack2,
                sizeInventory + 1, sizeInventory + 27, false)) {
            return true;
        }
        return false;
    }
}