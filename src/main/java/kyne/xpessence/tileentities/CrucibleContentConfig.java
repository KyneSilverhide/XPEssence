package kyne.xpessence.tileentities;

import kyne.xpessence.slots.SlotDefinitions;
import kyne.xpessence.tileentities.base.TileEntityContentConfig;
import net.minecraft.item.ItemStack;

public class CrucibleContentConfig extends TileEntityContentConfig {

    public static final int INPUT_SLOT = 0;
    public static final int BUCKET_SLOT = 1;

    public static final int TANK_CONTENT_MB = 0;
    public static final int ITEM_TO_TANK_STATUS = 1;
    public static final int TANK_TO_BUCKET_STATUS = 2;
    public static final int ITEM_LIQUID_VALUE = 3;

    public static final int TANK_TOTAL_CAPACITY_MB = 16000;
    public static final int ITEM_TRANSFER_TIME = 200;

    public CrucibleContentConfig() {
        registerSlot(INPUT_SLOT, SlotDefinitions.fuelDefinition);
        registerSlot(BUCKET_SLOT, SlotDefinitions.bucketDefinition);

        registerField(TANK_CONTENT_MB, "TankContentMB");
        registerField(ITEM_TO_TANK_STATUS, "ItemToTankStatus");
        registerField(TANK_TO_BUCKET_STATUS, "TankToBucketStatus");
        registerField(ITEM_LIQUID_VALUE, "ItemLiquidValue");
    }

    public ItemStack getInputSlot() {
        return getStackAtIndex(INPUT_SLOT);
    }

    public ItemStack getBucketSlot() {
        return getStackAtIndex(BUCKET_SLOT);
    }

    public int getTankContentMb() {
        return getFieldWithIndex(TANK_CONTENT_MB);
    }

    public void setTankContentMb(final int infusingFuelLeft) {
        setFieldWithIndex(TANK_CONTENT_MB, infusingFuelLeft);
    }

    public int getItemToTankStatus() {
        return getFieldWithIndex(ITEM_TO_TANK_STATUS);
    }

    public void setItemToTankStatus(final int currentItemBurnTime) {
        setFieldWithIndex(ITEM_TO_TANK_STATUS, currentItemBurnTime);
    }

    public int getTankToBucketStatus() {
        return getFieldWithIndex(TANK_TO_BUCKET_STATUS);
    }

    public void setTankToBucketStatus(final int itemInfusingStatus) {
        setFieldWithIndex(TANK_TO_BUCKET_STATUS, itemInfusingStatus);
    }

    public void increaseItemToTankStatus() {
        increaseFieldAtIndex(ITEM_TO_TANK_STATUS);
    }

    public void increaseTankToBucketStatus() {
        increaseFieldAtIndex(TANK_TO_BUCKET_STATUS);
    }

    public void setItemLiquidValue(final int liquidValue) {
        setFieldWithIndex(ITEM_LIQUID_VALUE, liquidValue);
    }

    public int getItemLiquidValue() {
        return getFieldWithIndex(ITEM_LIQUID_VALUE);
    }
}

