package kyne.xpessence.tileentities;

import kyne.xpessence.blocks.BlockXPFurnace;
import kyne.xpessence.containers.ContainerXPFurnace;
import kyne.xpessence.items.base.ItemBlockXPFuel;
import kyne.xpessence.items.base.ItemXPFuel;
import kyne.xpessence.recipes.ModSmeltingRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;

public class TileEntityXPFurnace extends TileEntityFurnace {

    public static final int SMELTING_ITEM_SLOT = 0;
    public static final int FUEL_ITEM_SLOT = 1;
    public static final int SMELTED_ITEM_SLOT = 2;

    private static final int BURN_TIME_FIELD = 0;
    private static final int CURRENT_ITEM_BURN_TIME_FIELD = 1;
    private static final int COOK_TIME_FIELD = 2;
    private static final int TOTAL_COOK_TIME_FIELD = 3;

    @Override
    public String getName() {
        return "container.xp_furnace.name";
    }

    @Override
    public void update() {

        boolean wasBurning = this.isBurning();
        boolean dirty = false;

        if (this.isBurning()) {
            int burnTime = getField(BURN_TIME_FIELD);
            setField(BURN_TIME_FIELD, burnTime - 1);
        }

        if (!this.worldObj.isRemote) {

            ItemStack fuelItemStack = getStackInSlot(FUEL_ITEM_SLOT);
            ItemStack smeltingItem = getStackInSlot(SMELTING_ITEM_SLOT);
            int cookTime = getField(COOK_TIME_FIELD);
            int totalCookTime = getField(TOTAL_COOK_TIME_FIELD);
            if (this.isBurning() || fuelItemStack != null && smeltingItem != null) {
                if (!this.isBurning() && this.canSmelt()) {

                    int fuelAmount = getFuelAmount(fuelItemStack);
                    setField(BURN_TIME_FIELD, fuelAmount);
                    setField(CURRENT_ITEM_BURN_TIME_FIELD, fuelAmount);

                    if (this.isBurning()) {
                        dirty = true;

                        if (fuelItemStack != null) {
                            decrStackSize(FUEL_ITEM_SLOT, 1);
                            if (fuelItemStack.stackSize == 0) {
                                setInventorySlotContents(FUEL_ITEM_SLOT, fuelItemStack.getItem().getContainerItem(fuelItemStack));
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    setField(COOK_TIME_FIELD, cookTime + 1);

                    if (cookTime == totalCookTime) {
                        setField(COOK_TIME_FIELD, 0);
                        setField(TOTAL_COOK_TIME_FIELD, this.getCookTime(smeltingItem));
                        this.smeltItem();
                        dirty = true;
                    }
                } else {
                    setField(COOK_TIME_FIELD, 0);
                }
            } else if (!this.isBurning() && cookTime > 0) {
                setField(COOK_TIME_FIELD, MathHelper.clamp_int(cookTime - 2, 0, totalCookTime));
            }

            if (wasBurning != this.isBurning()) {
                dirty = true;
                BlockXPFurnace.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if (dirty) {
            this.markDirty();
        }
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        System.out.println("index = [" + index + "], stack = [" + stack + "]");
        super.setInventorySlotContents(index, stack);
    }

    private int getFuelAmount(ItemStack fuelItemStack) {
        if(fuelItemStack.getItem() instanceof ItemXPFuel) {
            return ((ItemXPFuel)fuelItemStack.getItem()).getFuelAmount();
        } else if(fuelItemStack.getItem() instanceof  ItemBlockXPFuel) {
            return ((ItemBlockXPFuel) fuelItemStack.getItem()).getFuelAmount();
        }
        return 0;
    }

    private boolean canSmelt() {
        ItemStack smeltingItem = getStackInSlot(SMELTING_ITEM_SLOT);
        ItemStack smeltedItem = getStackInSlot(SMELTED_ITEM_SLOT);
        if (smeltingItem == null) {
            return false;
        } else {
            ItemStack itemstack = ModSmeltingRecipes.getSmeltingResult(smeltingItem);
            if (itemstack == null) {
                return false;
            }
            if (smeltedItem == null) {
                return true;
            }
            if (!smeltedItem.isItemEqual(itemstack)) {
                return false;
            }
            int result = smeltedItem.stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= smeltedItem.getMaxStackSize();
        }
    }

    @Override
    public int getCookTime(ItemStack stack) {
        return 200;
    }

    @Override
    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack smeltingItem = getStackInSlot(SMELTING_ITEM_SLOT);
            ItemStack itemstack = ModSmeltingRecipes.getSmeltingResult(smeltingItem);
            ItemStack smeltedItem = getStackInSlot(SMELTED_ITEM_SLOT);

            if (itemstack != null) {
                if (smeltedItem == null) {
                    setInventorySlotContents(SMELTED_ITEM_SLOT, itemstack.copy());
                } else if (smeltedItem.getItem() == itemstack.getItem()) {
                    smeltedItem.stackSize += itemstack.stackSize;
                }
                decrStackSize(SMELTING_ITEM_SLOT, 1);
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        System.out.println("slot = [" + slot + "], stack = [" + stack + "]");
        return canInsertInSlot(slot, stack);
    }

    public static boolean canInsertInSlot(int slot, ItemStack stack) {
        return true;
//        if (slot == FUEL_ITEM_SLOT) {
//            return stack.getItem() instanceof ItemXPFuel || stack.getItem() instanceof ItemBlockXPFuel;
//        } else if (slot == SMELTING_ITEM_SLOT) {
//            return ModSmeltingRecipes.getSmeltingResult(stack) != null;
//        }
//        return false;
    }

    public static boolean isSmeltableItem(ItemStack stack) {
        System.out.println("stack = [" + stack + "]");
        return canInsertInSlot(SMELTING_ITEM_SLOT, stack);
    }

    @Override
    public String getGuiID() {
        return "xpessence:xp_furnace";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerXPFurnace(playerInventory, this);
    }
}