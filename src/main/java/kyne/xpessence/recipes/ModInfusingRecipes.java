package kyne.xpessence.recipes;

import com.google.common.collect.Maps;
import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ModInfusingRecipes {

    private static final int METADATA_WILDCARD_VALUE = 32767;
    private static final Map<ItemStack, ItemStack> smeltingList = Maps.newHashMap();

    public static void registerSmeltingRecipes() {
        addSmeltingRecipe(new ItemStack(ModItems.devitalizedSeeds), new ItemStack(ModItems.xpSeeds));
        addSmeltingRecipe(new ItemStack(Items.apple), new ItemStack(ModItems.xpApple));
        addSmeltingRecipe(new ItemStack(Item.getItemFromBlock(Blocks.glass)),
                new ItemStack(Item.getItemFromBlock(ModBlocks.xpGemGlassBlock)));
    }

    private static void addSmeltingRecipe(final ItemStack input, final ItemStack output) {
        smeltingList.put(input, output);
    }

    public static ItemStack getInfusingResults(final ItemStack stack) {
        for (final Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
            if (compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static boolean compareItemStacks(final ItemStack stack1, final ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == METADATA_WILDCARD_VALUE || stack2
                .getMetadata() == stack1.getMetadata());
    }
}
