package kyne.xpessence.tileentities;

import kyne.xpessence.blocks.BlockInfuser;
import kyne.xpessence.containers.ContainerInfuser;
import kyne.xpessence.items.base.ItemBlockXPFuel;
import kyne.xpessence.items.base.ItemXPFuel;
import kyne.xpessence.recipes.ModInfusingRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class TileEntityInfuser extends TileEntityLockable implements ISidedInventory, ITickable {

    public static final int INPUT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    private static final int[] slotsTop = new int[]{INPUT_SLOT};
    private static final int[] slotsBottom = new int[]{FUEL_SLOT};
    private static final int[] slotsSides = new int[]{OUTPUT_SLOT};

    private static final int ITEM_BURN_TIME = 200;

    private ItemStack[] infuserItemStacks = new ItemStack[3];

    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;

    private String infuserCustomName;

    /**
     * This controls whether the tile entity gets replaced whenever the block state is changed.
     * Normally only want this when block actually is replaced.
     */
    @Override
    public boolean shouldRefresh(final World world, final BlockPos pos, final IBlockState oldState,
                                 final IBlockState newSate) {
        return (oldState.getBlock() != newSate.getBlock());
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return infuserItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(final int index) {
        return infuserItemStacks[index];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(final int index, final int count) {
        if (infuserItemStacks[index] != null) {
            final ItemStack itemstack;

            if (infuserItemStacks[index].stackSize <= count) {
                itemstack = infuserItemStacks[index];
                infuserItemStacks[index] = null;
                return itemstack;
            } else {
                itemstack = infuserItemStacks[index].splitStack(count);

                if (infuserItemStacks[index].stackSize == 0) {
                    infuserItemStacks[index] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    public ItemStack removeStackFromSlot(final int index) {
        if (this.infuserItemStacks[index] != null) {
            final ItemStack itemstack = this.infuserItemStacks[index];
            this.infuserItemStacks[index] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(final int index, final ItemStack stack) {
        final boolean isSameItemStackAlreadyInSlot = stack != null && stack.isItemEqual(infuserItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, infuserItemStacks[index]);
        infuserItemStacks[index] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }

        if (index == INPUT_SLOT && !isSameItemStackAlreadyInSlot) {
            this.totalCookTime = ITEM_BURN_TIME;
            this.cookTime = 0;
            markDirty();
        }
    }

    @Override
    public String getName() {
        return "container.xp_infuser";
    }

    /**
     * Returns true if this thing is named
     */
    @Override
    public boolean hasCustomName() {
        return infuserCustomName != null && infuserCustomName.length() > 0;
    }

    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        final NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.infuserItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            final NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            final int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.infuserItemStacks.length) {
                this.infuserItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

        this.furnaceBurnTime = compound.getShort("BurnTime");
        this.cookTime = compound.getShort("CookTime");
        this.totalCookTime = compound.getShort("CookTimeTotal");
        this.currentItemBurnTime = ITEM_BURN_TIME;

        if (compound.hasKey("CustomName", 8)) {
            this.infuserCustomName = compound.getString("CustomName");
        }
    }

    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setShort("BurnTime", (short) this.furnaceBurnTime);
        compound.setShort("CookTime", (short) this.cookTime);
        compound.setShort("CookTimeTotal", (short) this.totalCookTime);
        final NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.infuserItemStacks.length; ++i) {
            if (this.infuserItemStacks[i] != null) {
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.infuserItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.infuserCustomName);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isInfusing() {
        return furnaceBurnTime > 0;
    }

    @Override
    public void update() {

        boolean needsUpdate = false;
        final boolean wasInfusing = isInfusing();

        final ItemStack inputItem = infuserItemStacks[INPUT_SLOT];
        final ItemStack fuelItem = infuserItemStacks[FUEL_SLOT];
        final ItemStack outputItem = infuserItemStacks[OUTPUT_SLOT];

        if (this.isInfusing()) {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote) {
            if (this.isInfusing() || inputItem != null && fuelItem != null) {
                if (!this.isInfusing() && this.canInfuseInputToOutput(inputItem, outputItem)) {
                    this.currentItemBurnTime = this.furnaceBurnTime = getInfusingPower(fuelItem);

                    if (this.isInfusing()) {
                        needsUpdate = true;
                        fuelItem.stackSize--;
                        if (fuelItem.stackSize == 0) {
                            this.infuserItemStacks[FUEL_SLOT] = null;
                        }
                    }
                }

                if (this.isInfusing() && this.canInfuseInputToOutput(inputItem, outputItem)) {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getInfusingPower(fuelItem);
                        this.infuseItem(inputItem, outputItem);
                        needsUpdate = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isInfusing() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (wasInfusing != this.isInfusing()) {
                needsUpdate = true;
                BlockInfuser.setState(this.isInfusing(), this.worldObj, this.pos);
            }
        }

        if (needsUpdate) {
            this.markDirty();
        }
    }

    private int getInfusingPower(final ItemStack fuelItemStack) {
        if(fuelItemStack != null) {
            if (fuelItemStack.getItem() instanceof ItemXPFuel) {
                return ((ItemXPFuel) fuelItemStack.getItem()).getInfusingPower();
            } else if (fuelItemStack.getItem() instanceof ItemBlockXPFuel) {
                return ((ItemBlockXPFuel) fuelItemStack.getItem()).getInfusingPower();
            }
        }
        return 0;
    }

    private boolean canInfuseInputToOutput(final ItemStack input, final ItemStack output) {
        if (input == null) {
            return false;
        } else {
            final ItemStack futureInfusedItem = ModInfusingRecipes.getInfusingResults(infuserItemStacks[INPUT_SLOT]);
            if (futureInfusedItem == null) {
                return false;
            }
            if (output == null) {
                return true;
            }
            if (!output.isItemEqual(futureInfusedItem)) {
                return false;
            }
            final int result = output.stackSize + futureInfusedItem.stackSize;
            return result <= getInventoryStackLimit() && result <= output.getMaxStackSize();
        }
    }

    public void infuseItem(final ItemStack inputItem, final ItemStack outputItem) {
        if (canInfuseInputToOutput(inputItem, outputItem)) {
            final ItemStack futureInfusedItem = ModInfusingRecipes.getInfusingResults(infuserItemStacks[INPUT_SLOT]);
            if (futureInfusedItem != null) {
                if (outputItem == null) {
                    infuserItemStacks[OUTPUT_SLOT] = futureInfusedItem.copy();
                } else if (outputItem.getItem() == futureInfusedItem.getItem()) {
                    infuserItemStacks[OUTPUT_SLOT].stackSize += futureInfusedItem.stackSize; // Forge BugFix: Results may have multiple items
                }

                --inputItem.stackSize;
                if (inputItem.stackSize <= 0) {
                    infuserItemStacks[INPUT_SLOT] = null;
                }
            }
        }
    }

    @Override
    public boolean isUseableByPlayer(final EntityPlayer playerIn) {
        return worldObj.getTileEntity(pos) == this && playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D,
                pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(final EntityPlayer playerIn) {
    }

    @Override
    public void closeInventory(final EntityPlayer playerIn) {
    }

    @Override
    public boolean isItemValidForSlot(final int index, final ItemStack stack) {
        return index == INPUT_SLOT;
    }

    @Override
    public int[] getSlotsForFace(final EnumFacing side) {
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
    }

    @Override
    public boolean canInsertItem(final int index, final ItemStack itemStackIn, final EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(final int parSlotIndex, final ItemStack parStack, final EnumFacing parFacing) {
        return true;
    }

    @Override
    public String getGuiID() {
        return "xpessence:xp_infuser";
    }

    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerInfuser(playerInventory, this);
    }

    @Override
    public int getField(final int id) {
        switch (id) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(final int id, final int value) {
        switch (id) {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    @Override
    public void clear() {
        for (int i = 0; i < infuserItemStacks.length; ++i) {
            infuserItemStacks[i] = null;
        }
    }
}