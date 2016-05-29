package kyne.xpessence.tileentities;

import kyne.xpessence.blocks.BlockCrucible;
import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.containers.ContainerCrucible;
import kyne.xpessence.items.ModItems;
import kyne.xpessence.items.base.ItemBlockXPFuel;
import kyne.xpessence.items.base.ItemXPFuel;
import kyne.xpessence.tileentities.base.BasicTileEntity;
import kyne.xpessence.utils.FuelUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;


public class TileEntityCrucible extends BasicTileEntity {

    private static final int[] slotsTop = new int[]{CrucibleContentConfig.INPUT_SLOT};
    private static final int[] slotsBottom = new int[]{CrucibleContentConfig.BUCKET_SLOT};
    private static final int[] slotsSides = new int[]{CrucibleContentConfig.BUCKET_SLOT};
    public static final int BUCKET_MB = 1000;

    private final CrucibleContentConfig crucibleContentConfig;

    public TileEntityCrucible() {
        super(new CrucibleContentConfig(), "xp_crucible");
        crucibleContentConfig = (CrucibleContentConfig) tileEntityContentConfig;
    }

    @Override
    public void setInventorySlotContents(final int index, final ItemStack stack) {
        super.setInventorySlotContents(index, stack);

        if (index == CrucibleContentConfig.BUCKET_SLOT && !isSameItemStackAlreadyInSlot(stack, index)) {
            crucibleContentConfig.setTankToBucketStatus(0);
            markDirty();
        }
    }

    @Override
    public void update() {

        boolean needsUpdate = false;
        final boolean wasActive = isActive();

        final ItemStack inputItem = crucibleContentConfig.getInputSlot();
        final ItemStack bucketItem = crucibleContentConfig.getBucketSlot();

        if (!this.worldObj.isRemote) {

            if(isFilling()) {
                crucibleContentConfig.increaseItemToTankStatus();
                if(crucibleContentConfig.getItemToTankStatus() == CrucibleContentConfig.ITEM_TRANSFER_TIME) {
                    crucibleContentConfig.setItemToTankStatus(0);
                    crucibleContentConfig.setTankContentMb(crucibleContentConfig.getTankContentMb() + crucibleContentConfig.getItemLiquidValue());
                }
            } else if(inputItem != null && FuelUtils.isFuel(inputItem) && enoughSpaceInTank(inputItem)){
                crucibleContentConfig.increaseItemToTankStatus();
                decrStackSize(CrucibleContentConfig.INPUT_SLOT, 1);
                crucibleContentConfig.setItemLiquidValue(getLiquidQuantityMB(inputItem));
            }

            if(isEmptying()) {
                crucibleContentConfig.increaseTankToBucketStatus();
                if(crucibleContentConfig.getTankToBucketStatus() == CrucibleContentConfig.ITEM_TRANSFER_TIME) {
                    crucibleContentConfig.setTankToBucketStatus(0);
                    crucibleContentConfig.setTankContentMb(crucibleContentConfig.getTankContentMb() - BUCKET_MB);
                    setInventorySlotContents(CrucibleContentConfig.BUCKET_SLOT, new ItemStack(ModItems.bucketLiquidXP));
                }
                if(bucketItem == null) {
                    crucibleContentConfig.setTankToBucketStatus(0);
                }
            } else if(bucketItem != null && bucketItem.getItem() == Items.bucket) {
                crucibleContentConfig.increaseTankToBucketStatus();
            }

            if (wasActive != this.isActive()) {
                needsUpdate = true;
                BlockCrucible.setState(this.isActive(), this.worldObj, this.pos, ModBlocks.xpCrucibleOn, ModBlocks.xpCrucibleOff);
            }
        }

        if (needsUpdate) {
            this.markDirty();
        }
    }

    private boolean enoughSpaceInTank(final ItemStack inputItem) {
        return crucibleContentConfig.getTankContentMb() + getLiquidQuantityMB(inputItem) <= CrucibleContentConfig.TANK_TOTAL_CAPACITY_MB;
    }

    private int getLiquidQuantityMB(final ItemStack fuelItemStack) {
        if (fuelItemStack != null) {
            if (fuelItemStack.getItem() instanceof ItemXPFuel) {
                return ((ItemXPFuel) fuelItemStack.getItem()).getInfusingPower() * FuelUtils.POWER_TO_MB_RATIO;
            } else if (fuelItemStack.getItem() instanceof ItemBlockXPFuel) {
                return ((ItemBlockXPFuel) fuelItemStack.getItem()).getInfusingPower() * FuelUtils.POWER_TO_MB_RATIO;
            }
        }
        return 0;
    }

    public boolean isActive() {
        return isFilling() || isEmptying();
    }

    public boolean isFilling() {
        return crucibleContentConfig.getItemToTankStatus() > 0;
    }

    public boolean isEmptying() {
        return crucibleContentConfig.getTankToBucketStatus() > 0;
    }

    @Override
    public int[] getSlotsForFace(final EnumFacing side) {
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
    }

    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerCrucible(playerInventory, this);
    }
}