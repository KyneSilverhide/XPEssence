package kyne.xpessence.containers.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BasicContainer extends Container {

    private final IInventory tileFurnace;

    public BasicContainer(final InventoryPlayer playerInventory, final IInventory furnaceInventory) {
        this.tileFurnace = furnaceInventory;
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

    public IInventory getTileFurnace() {
        return tileFurnace;
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
}