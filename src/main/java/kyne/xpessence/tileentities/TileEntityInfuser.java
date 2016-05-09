package kyne.xpessence.tileentities;

import kyne.xpessence.Constants;
import kyne.xpessence.blocks.BlockInfuser;
import kyne.xpessence.containers.ContainerInfuser;
import kyne.xpessence.items.base.ItemBlockXPFuel;
import kyne.xpessence.items.base.ItemXPFuel;
import kyne.xpessence.recipes.ModInfusingRecipes;
import kyne.xpessence.tileentities.base.BasicTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;


public class TileEntityInfuser extends BasicTileEntity {

    public static final int INPUT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    public static final int INFUSING_FUEL_LEFT = 0;
    public static final int CURRENT_ITEM_BURN_TIME = 1;
    public static final int ITEM_INFUSING_STATUS = 2;
    public static final int TIME_TO_INFUSE_ITEM = 3;
    public static final int ITEM_INFUSING_TIME = 200;

    private static final int[] slotsTop = new int[]{INPUT_SLOT};
    private static final int[] slotsBottom = new int[]{FUEL_SLOT};
    private static final int[] slotsSides = new int[]{OUTPUT_SLOT};

    private int infusingFuelLeft;
    private int currentItemBurnTime;
    private int itemInfusingStatus;
    private int timeToInfuseItem;

    public TileEntityInfuser() {
        super(new ItemStack[3]);
    }

    @Override
    public void setInventorySlotContents(final int index, final ItemStack stack) {
        final boolean isSameItemStackAlreadyInSlot = stack != null && stack.isItemEqual(
                getTileEntityItemStacks()[index]) && ItemStack.areItemStackTagsEqual(stack,
                getTileEntityItemStacks()[index]);
        getTileEntityItemStacks()[index] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }

        if (index == INPUT_SLOT && !isSameItemStackAlreadyInSlot) {
            this.timeToInfuseItem = ITEM_INFUSING_TIME;
            this.itemInfusingStatus = 0;
            markDirty();
        }
    }

    @Override
    public String getName() {
        return "container.xp_infuser.name";
    }

    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        final NBTTagList nbttaglist = compound.getTagList("Items", 10);
        setTileEntityItemStacks(new ItemStack[this.getSizeInventory()]);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            final NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            final int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.getTileEntityItemStacks().length) {
                this.getTileEntityItemStacks()[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

        this.infusingFuelLeft = compound.getShort("InfusingFuelLeft");
        this.itemInfusingStatus = compound.getShort("ItemInfusingStatus");
        this.timeToInfuseItem = compound.getShort("TimeToInfuseItem");
        this.currentItemBurnTime = ITEM_INFUSING_TIME;

        if (compound.hasKey("CustomName", 8)) {
            setCustomName(compound.getString("CustomName"));
        }
    }

    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setShort("InfusingFuelLeft", (short) this.infusingFuelLeft);
        compound.setShort("ItemInfusingStatus", (short) this.itemInfusingStatus);
        compound.setShort("TimeToInfuseItem", (short) this.timeToInfuseItem);
        final NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.getTileEntityItemStacks().length; ++i) {
            if (this.getTileEntityItemStacks()[i] != null) {
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.getTileEntityItemStacks()[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Items", nbttaglist);
        if (this.hasCustomName()) {
            compound.setString("CustomName", this.getCustomName());
        }
    }

    @Override
    public void update() {

        boolean needsUpdate = false;
        final boolean wasInfusing = isInfusing();

        final ItemStack inputItem = getTileEntityItemStacks()[INPUT_SLOT];
        final ItemStack fuelItem = getTileEntityItemStacks()[FUEL_SLOT];
        final ItemStack outputItem = getTileEntityItemStacks()[OUTPUT_SLOT];

        if (this.isInfusing()) {
            --this.infusingFuelLeft;
        }

        if (!this.worldObj.isRemote) {
            if (this.isInfusing() || inputItem != null && fuelItem != null) {
                if (!this.isInfusing() && this.canInfuseInputToOutput(inputItem, outputItem)) {
                    this.currentItemBurnTime = this.infusingFuelLeft = getInfusingPower(fuelItem);

                    if (this.isInfusing()) {
                        needsUpdate = true;
                        fuelItem.stackSize--;
                        if (fuelItem.stackSize == 0) {
                            this.getTileEntityItemStacks()[FUEL_SLOT] = null;
                        }
                    }
                }

                if (this.isInfusing() && this.canInfuseInputToOutput(inputItem, outputItem)) {
                    this.itemInfusingStatus++;

                    if (this.itemInfusingStatus == this.timeToInfuseItem) {
                        this.itemInfusingStatus = 0;
                        this.timeToInfuseItem = ITEM_INFUSING_TIME;
                        this.infuseItem(inputItem, outputItem);
                        needsUpdate = true;
                    }
                } else {
                    this.itemInfusingStatus = 0;
                }
            } else if (!this.isInfusing() && this.itemInfusingStatus > 0) {
                this.itemInfusingStatus = MathHelper.clamp_int(this.itemInfusingStatus - 2, 0, this.timeToInfuseItem);
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

    public boolean isInfusing() {
        return infusingFuelLeft > 0;
    }

    private boolean canInfuseInputToOutput(final ItemStack input, final ItemStack output) {
        if (input == null) {
            return false;
        } else {
            final ItemStack futureInfusedItem = ModInfusingRecipes.getInfusingResults(input);
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

    private int getInfusingPower(final ItemStack fuelItemStack) {
        if (fuelItemStack != null) {
            if (fuelItemStack.getItem() instanceof ItemXPFuel) {
                return ((ItemXPFuel) fuelItemStack.getItem()).getInfusingPower();
            } else if (fuelItemStack.getItem() instanceof ItemBlockXPFuel) {
                return ((ItemBlockXPFuel) fuelItemStack.getItem()).getInfusingPower();
            }
        }
        return 0;
    }

    public void infuseItem(final ItemStack inputItem, final ItemStack outputItem) {
        if (canInfuseInputToOutput(inputItem, outputItem)) {
            final ItemStack futureInfusedItem = ModInfusingRecipes.getInfusingResults(
                    getTileEntityItemStacks()[INPUT_SLOT]);
            if (futureInfusedItem != null) {
                if (outputItem == null) {
                    getTileEntityItemStacks()[OUTPUT_SLOT] = futureInfusedItem.copy();
                } else if (outputItem.getItem() == futureInfusedItem.getItem()) {
                    getTileEntityItemStacks()[OUTPUT_SLOT].stackSize += futureInfusedItem.stackSize;
                    // Forge BugFix: Results may have multiple items
                }

                inputItem.stackSize--;
                if (inputItem.stackSize <= 0) {
                    getTileEntityItemStacks()[INPUT_SLOT] = null;
                }
            }
        }
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
    public boolean isItemValidForSlot(final int index, final ItemStack stack) {
        return index == INPUT_SLOT;
    }

    @Override
    public boolean canExtractItem(final int parSlotIndex, final ItemStack parStack, final EnumFacing parFacing) {
        return true;
    }

    @Override
    public String getGuiID() {
        return Constants.MODID + ":xp_infuser";
    }

    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerInfuser(playerInventory, this);
    }

    @Override
    public int getField(final int id) {
        switch (id) {
            case INFUSING_FUEL_LEFT:
                return this.infusingFuelLeft;
            case CURRENT_ITEM_BURN_TIME:
                return this.currentItemBurnTime;
            case ITEM_INFUSING_STATUS:
                return this.itemInfusingStatus;
            case TIME_TO_INFUSE_ITEM:
                return this.timeToInfuseItem;
            default:
                return 0;
        }
    }

    @Override
    public void setField(final int id, final int value) {
        switch (id) {
            case INFUSING_FUEL_LEFT:
                this.infusingFuelLeft = value;
                break;
            case CURRENT_ITEM_BURN_TIME:
                this.currentItemBurnTime = value;
                break;
            case ITEM_INFUSING_STATUS:
                this.itemInfusingStatus = value;
                break;
            case TIME_TO_INFUSE_ITEM:
                this.timeToInfuseItem = value;
        }
    }
}