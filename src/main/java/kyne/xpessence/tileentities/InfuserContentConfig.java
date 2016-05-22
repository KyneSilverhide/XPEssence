package kyne.xpessence.tileentities;

import kyne.xpessence.slots.FuelSlot;
import kyne.xpessence.slots.InfusingSlot;
import kyne.xpessence.slots.OutputSlot;
import kyne.xpessence.tileentities.base.TileEntityContentConfig;
import net.minecraft.item.ItemStack;

public class InfuserContentConfig extends TileEntityContentConfig {

    public static final int INPUT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    public static final int INFUSING_FUEL_LEFT = 0;
    public static final int CURRENT_ITEM_BURN_TIME = 1;
    public static final int ITEM_INFUSING_STATUS = 2;
    public static final int TIME_TO_INFUSE_ITEM = 3;

    public InfuserContentConfig() {
        registerSlot(INPUT_SLOT, new InfusingSlot());
        registerSlot(FUEL_SLOT, new FuelSlot());
        registerSlot(OUTPUT_SLOT, new OutputSlot());

        registerField(INFUSING_FUEL_LEFT, "InfusingFuelLeft");
        registerField(CURRENT_ITEM_BURN_TIME, "CurrentItemBurnTime");
        registerField(ITEM_INFUSING_STATUS, "ItemInfusingStatus");
        registerField(TIME_TO_INFUSE_ITEM, "TimeToInfuseItem");

        setTimeToInfuseItem(TileEntityInfuser.ITEM_INFUSING_TIME);
    }

    public ItemStack getInputSlot() {
        return getStackAtIndex(INPUT_SLOT);
    }

    public void setInputSlot(final ItemStack input) {
        setItemStackAtIndex(INPUT_SLOT, input);
    }

    public ItemStack getFuelSlot() {
        return getStackAtIndex(FUEL_SLOT);
    }

    public void setFuelSlot(final ItemStack fuel) {
        setItemStackAtIndex(FUEL_SLOT, fuel);
    }

    public ItemStack getOutputSlot() {
        return getStackAtIndex(OUTPUT_SLOT);
    }

    public void setOutputSlot(final ItemStack output) {
        setItemStackAtIndex(OUTPUT_SLOT, output);
    }

    public int getInfusingFuelLeft() {
        return getFieldWithIndex(INFUSING_FUEL_LEFT);
    }

    public void setInfusingFuelLeft(final int infusingFuelLeft) {
        setFieldWithIndex(INFUSING_FUEL_LEFT, infusingFuelLeft);
    }

    public int getCurrentItemBurnTime() {
        return getFieldWithIndex(CURRENT_ITEM_BURN_TIME);
    }

    public void setCurrentItemBurnTime(final int currentItemBurnTime) {
        setFieldWithIndex(CURRENT_ITEM_BURN_TIME, currentItemBurnTime);
    }

    public int getTimeToInfuseItem() {
        return getFieldWithIndex(TIME_TO_INFUSE_ITEM);
    }

    public void setTimeToInfuseItem(final int timeToInfuseItem) {
        setFieldWithIndex(TIME_TO_INFUSE_ITEM, timeToInfuseItem);
    }

    public int getItemInfusingStatus() {
        return getFieldWithIndex(ITEM_INFUSING_STATUS);
    }

    public void setItemInfusingStatus(final int itemInfusingStatus) {
        setFieldWithIndex(ITEM_INFUSING_STATUS, itemInfusingStatus);
    }

    public void decreaseInfusingFuelLeft() {
        decreaseFieldAtIndex(INFUSING_FUEL_LEFT);
    }

    public void increaseItemInfusingStatus() {
        increaseFieldAtIndex(ITEM_INFUSING_STATUS);
    }
}

