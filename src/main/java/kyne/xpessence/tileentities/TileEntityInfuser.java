package kyne.xpessence.tileentities;

import kyne.xpessence.blocks.BlockInfuser;
import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.containers.ContainerInfuser;
import kyne.xpessence.items.base.ItemBlockXPFuel;
import kyne.xpessence.items.base.ItemXPFuel;
import kyne.xpessence.recipes.ModInfusingRecipes;
import kyne.xpessence.tileentities.base.BasicTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;


public class TileEntityInfuser extends BasicTileEntity {

    public static final int ITEM_INFUSING_TIME = 200;

    private static final int[] slotsTop = new int[]{InfuserContentConfig.INPUT_SLOT};
    private static final int[] slotsBottom = new int[]{InfuserContentConfig.FUEL_SLOT};
    private static final int[] slotsSides = new int[]{InfuserContentConfig.OUTPUT_SLOT};

    public TileEntityInfuser() {
        super(new InfuserContentConfig(), "xp_infuser");
    }

    @Override
    public void setInventorySlotContents(final int index, final ItemStack stack) {
        super.setInventorySlotContents(index, stack);

        if (index == InfuserContentConfig.INPUT_SLOT && !isSameItemStackAlreadyInSlot(stack, index)) {
            getTileEntityContentConfig().setTimeToInfuseItem(ITEM_INFUSING_TIME);
            getTileEntityContentConfig().setItemInfusingStatus(0);
            markDirty();
        }
    }

    @Override
    public InfuserContentConfig getTileEntityContentConfig() {
        return (InfuserContentConfig) tileEntityContentConfig;
    }

    @Override
    public void update() {
        final InfuserContentConfig tileEntityContentConfig = getTileEntityContentConfig();
        boolean needsUpdate = false;
        final boolean wasInfusing = isInfusing();

        final ItemStack inputItem = tileEntityContentConfig.getInputSlot();
        final ItemStack fuelItem = tileEntityContentConfig.getFuelSlot();
        final ItemStack outputItem = tileEntityContentConfig.getOutputSlot();

        final int timeToInfuseItem = tileEntityContentConfig.getTimeToInfuseItem();
        if (this.isInfusing()) {
            tileEntityContentConfig.decreaseInfusingFuelLeft();
        }

        if (!this.worldObj.isRemote) {
            if (this.isInfusing() || inputItem != null && fuelItem != null) {
                if (!this.isInfusing() && this.canInfuseInputToOutput(inputItem, outputItem)) {
                    final int infusingPower = getInfusingPower(fuelItem);
                    tileEntityContentConfig.setCurrentItemBurnTime(infusingPower);
                    tileEntityContentConfig.setInfusingFuelLeft(infusingPower);

                    if (this.isInfusing()) {
                        needsUpdate = true;
                        fuelItem.stackSize--;
                        if (fuelItem.stackSize == 0) {
                            tileEntityContentConfig.setFuelSlot(null);
                        }
                    }
                }

                if (this.isInfusing() && this.canInfuseInputToOutput(inputItem, outputItem)) {
                    tileEntityContentConfig.increaseItemInfusingStatus();

                    if (tileEntityContentConfig.getItemInfusingStatus() == timeToInfuseItem) {
                        tileEntityContentConfig.setItemInfusingStatus(0);
                        tileEntityContentConfig.setTimeToInfuseItem(ITEM_INFUSING_TIME);
                        this.infuseItem(inputItem, outputItem);
                        needsUpdate = true;
                    }
                } else {
                    tileEntityContentConfig.setItemInfusingStatus(0);
                }
            } else if (!this.isInfusing() && tileEntityContentConfig.getItemInfusingStatus() > 0) {
                final int infusingStatus = MathHelper.clamp_int(tileEntityContentConfig.getItemInfusingStatus() - 2, 0, timeToInfuseItem);
                tileEntityContentConfig.setItemInfusingStatus(infusingStatus);
            }

            if (wasInfusing != this.isInfusing()) {
                needsUpdate = true;
                BlockInfuser.setState(this.isInfusing(), this.worldObj, this.pos, ModBlocks.xpInfuserOn, ModBlocks.xpInfuserOff);
            }
        }

        if (needsUpdate) {
            this.markDirty();
        }
    }

    public boolean isInfusing() {
        return getTileEntityContentConfig().getInfusingFuelLeft() > 0;
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
                    getTileEntityContentConfig().getInputSlot());
            if (futureInfusedItem != null) {
                if (outputItem == null) {
                    getTileEntityContentConfig().setOutputSlot(futureInfusedItem.copy());
                } else if (outputItem.getItem() == futureInfusedItem.getItem()) {
                    getTileEntityContentConfig().getOutputSlot().stackSize += futureInfusedItem.stackSize;
                    // Forge BugFix: Results may have multiple items
                }

                inputItem.stackSize--;
                if (inputItem.stackSize <= 0) {
                    getTileEntityContentConfig().setInputSlot(null);
                }
            }
        }
    }

    @Override
    public int[] getSlotsForFace(final EnumFacing side) {
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
    }

    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerInfuser(playerInventory, this);
    }
}