package kyne.xpessence.tileentities.base;

import kyne.xpessence.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;


public abstract class BasicTileEntity extends TileEntityLockable implements ISidedInventory, ITickable {

    protected final TileEntityContentConfig tileEntityContentConfig;
    private final String name;

    public BasicTileEntity(final TileEntityContentConfig tileEntityContentConfig, final String name) {
        this.tileEntityContentConfig = tileEntityContentConfig;
        this.name = name;
    }

    @Override
    public String getName() {
        return "container." + name + ".name";
    }

    @Override
    public ItemStack getStackInSlot(final int index) {
        return tileEntityContentConfig.getStackAtIndex(index);
    }

    public TileEntityContentConfig getTileEntityContentConfig() {
        return tileEntityContentConfig;
    }

    @Override
    public ItemStack decrStackSize(final int index, final int count) {
        final ItemStack stackAtIndex = tileEntityContentConfig.getStackAtIndex(index);
        if (stackAtIndex != null) {
            final ItemStack itemstack;
            if (stackAtIndex.stackSize <= count) {
                itemstack = stackAtIndex;
                tileEntityContentConfig.setItemStackAtIndex(index, null);
                return itemstack;
            } else {
                itemstack = stackAtIndex.splitStack(count);
                if (stackAtIndex.stackSize == 0) {
                    tileEntityContentConfig.setItemStackAtIndex(index, null);
                }
                return itemstack;
            }
        } else {
            return null;
        }
    }

    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        final NBTTagList nbttaglist = compound.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            final NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            final int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < getSizeInventory()) {
                tileEntityContentConfig.setItemStackAtIndex(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
            }
        }

        for (final Integer index : tileEntityContentConfig.getFieldIndexes()) {
            final String fieldName = tileEntityContentConfig.getFieldName(index);
            tileEntityContentConfig.setFieldWithIndex(index, compound.getShort(fieldName));
        }
    }

    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);

        for (final Integer index : tileEntityContentConfig.getFieldIndexes()) {
            final String fieldName = tileEntityContentConfig.getFieldName(index);
            compound.setShort(fieldName, (short) tileEntityContentConfig.getFieldWithIndex(index));
        }
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (tileEntityContentConfig.getStackAtIndex(i) != null) {
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                tileEntityContentConfig.getStackAtIndex(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Items", nbttaglist);
        return compound;
    }

    @Override
    public boolean canExtractItem(final int parSlotIndex, final ItemStack parStack, final EnumFacing parFacing) {
        return true;
    }

    @Override
    public void openInventory(final EntityPlayer player) { /* Do nothing */}

    @Override
    public void closeInventory(final EntityPlayer player) { /* Do nothing */ }

    @Override
    public boolean canInsertItem(final int index, final ItemStack itemStackIn, final EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public int getField(final int index) {
        return tileEntityContentConfig.getFieldWithIndex(index);
    }

    @Override
    public void setField(final int id, final int value) {
        tileEntityContentConfig.setFieldWithIndex(id, value);
    }

    @Override
    public ItemStack removeStackFromSlot(final int index) {
        final ItemStack itemStack = tileEntityContentConfig.getStackAtIndex(index);
        tileEntityContentConfig.setItemStackAtIndex(index, null);
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(final int index, final ItemStack stack) {
        tileEntityContentConfig.setItemStackAtIndex(index, stack);
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    protected boolean isSameItemStackAlreadyInSlot(final ItemStack stack, final int index) {
        return stack != null && stack.isItemEqual(
                tileEntityContentConfig.getStackAtIndex(index)) && ItemStack.areItemStackTagsEqual(stack,
                tileEntityContentConfig.getStackAtIndex(index));
    }

    @Override
    public boolean isItemValidForSlot(final int index, final ItemStack stack) {
        return tileEntityContentConfig.getSlotDefinition(index) != null && tileEntityContentConfig.getSlotDefinition(index).isItemValid(stack);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(final EntityPlayer playerIn) {
        return worldObj.getTileEntity(pos) == this && playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D,
                pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public abstract int[] getSlotsForFace(EnumFacing side);

    @Override
    public int getFieldCount() {
        return tileEntityContentConfig.getFieldIndexes().size();
    }

    @Override
    public void clear() {
        for (int i = 0; i < getSizeInventory(); ++i) {
            tileEntityContentConfig.setItemStackAtIndex(i, null);
        }
    }

    @Override
    public int getSizeInventory() {
        return tileEntityContentConfig.getInventorySize();
    }

    @Override
    public abstract void update();

    @Override
    public abstract Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn);

    @Override
    public String getGuiID() {
        return Constants.MODID + ":" + name;
    }
}