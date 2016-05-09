package kyne.xpessence.tileentities.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;


public abstract class BasicTileEntity extends TileEntityLockable implements ISidedInventory, ITickable {

    private ItemStack[] tileEntityItemStacks;
    private String customName;

    public BasicTileEntity(final ItemStack[] tileEntityItemStacks) {
        this.tileEntityItemStacks = tileEntityItemStacks;
        this.customName = null;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(final String customName) {
        this.customName = customName;
    }

    public ItemStack[] getTileEntityItemStacks() {
        return tileEntityItemStacks;
    }

    public void setTileEntityItemStacks(final ItemStack[] tileEntityItemStacks) {
        this.tileEntityItemStacks = tileEntityItemStacks;
    }

    @Override
    public boolean shouldRefresh(final World world, final BlockPos pos, final IBlockState oldState,
                                 final IBlockState newSate) {
        return (oldState.getBlock() != newSate.getBlock());
    }

    @Override
    public int getSizeInventory() {
        return tileEntityItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(final int index) {
        return tileEntityItemStacks[index];
    }

    @Override
    public ItemStack decrStackSize(final int index, final int count) {
        if (tileEntityItemStacks[index] != null) {
            final ItemStack itemstack;

            if (tileEntityItemStacks[index].stackSize <= count) {
                itemstack = tileEntityItemStacks[index];
                tileEntityItemStacks[index] = null;
                return itemstack;
            } else {
                itemstack = tileEntityItemStacks[index].splitStack(count);

                if (tileEntityItemStacks[index].stackSize == 0) {
                    tileEntityItemStacks[index] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void openInventory(final EntityPlayer player) {

    }

    @Override
    public void closeInventory(final EntityPlayer player) {

    }

    @Override
    public ItemStack removeStackFromSlot(final int index) {
        if (this.tileEntityItemStacks[index] != null) {
            final ItemStack itemstack = this.tileEntityItemStacks[index];
            this.tileEntityItemStacks[index] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public abstract void setInventorySlotContents(int index, ItemStack stack);

    @Override
    public abstract String getName();

    @Override
    public boolean hasCustomName() {
        return customName != null && customName.length() > 0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(final EntityPlayer playerIn) {
        return worldObj.getTileEntity(pos) == this && playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D,
                pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public abstract int getField(int id);

    @Override
    public abstract void setField(int id, int value);

    @Override
    public abstract int[] getSlotsForFace(EnumFacing side);

    @Override
    public boolean canInsertItem(final int index, final ItemStack itemStackIn, final EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public abstract boolean isItemValidForSlot(int index, ItemStack stack);

    @Override
    public abstract boolean canExtractItem(final int parSlotIndex, final ItemStack parStack,
                                           final EnumFacing parFacing);

    @Override
    public int getFieldCount() {
        return tileEntityItemStacks.length;
    }

    @Override
    public void clear() {
        for (int i = 0; i < tileEntityItemStacks.length; ++i) {
            tileEntityItemStacks[i] = null;
        }
    }

    @Override
    public abstract void update();

    @Override
    public abstract Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn);

    @Override
    public abstract String getGuiID();
}