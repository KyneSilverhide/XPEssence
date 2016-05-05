package kyne.xpessence.recipes;

import com.google.common.collect.Maps;
import kyne.xpessence.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ModSmeltingRecipes {

    private static final int METADATA_WILDCARD_VALUE = 32767;
    private static Map<ItemStack, ItemStack> smeltingList = Maps.newHashMap();

    public static void buildSmeltingRecipes() {
        addSmeltingRecipe(new ItemStack(Items.wheat_seeds), new ItemStack(ModItems.xpSeeds));
    }

    private static void addSmeltingRecipe(ItemStack input, ItemStack output) {
        smeltingList.put(input, output);
    }

    public static ItemStack getSmeltingResult(ItemStack stack) {
        for (Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
            if (compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem()
                && (stack2.getMetadata() == METADATA_WILDCARD_VALUE || stack2.getMetadata() == stack1.getMetadata());
    }

}
