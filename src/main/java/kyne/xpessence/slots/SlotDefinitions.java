package kyne.xpessence.slots;

import kyne.xpessence.recipes.ModInfusingRecipes;
import kyne.xpessence.slots.base.SlotDefinition;
import kyne.xpessence.utils.FuelUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SlotDefinitions {

    public static SlotDefinition fuelDefinition = new SlotDefinition() {
        @Override
        public boolean isItemValid(final ItemStack stack) {
            return FuelUtils.isFuel(stack);
        }
    };

    public static SlotDefinition outputDefinition = new SlotDefinition() {
        @Override
        public boolean isItemValid(final ItemStack stack) {
            return false;
        }
    };

    public static SlotDefinition infusingDefinition = new SlotDefinition() {
        @Override
        public boolean isItemValid(final ItemStack stack) {
            return ModInfusingRecipes.getInfusingResults(stack) != null;
        }
    };

    public static SlotDefinition bucketDefinition = new SlotDefinition() {
        @Override
        public boolean isItemValid(final ItemStack stack) {
            return stack.getItem() == Items.BUCKET;
        }
    };
}
