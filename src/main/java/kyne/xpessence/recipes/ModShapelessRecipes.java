package kyne.xpessence.recipes;

import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModShapelessRecipes {

    public static void buildShapelessRecipes() {
        xpBlockToGem();
        xpBottleToXpGem();
    }

    private static void xpBlockToGem() {
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.xpGem, 9), new ItemStack(ModBlocks.xpGemBlock));
    }

    private static void xpBottleToXpGem() {
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.xpGem, 3), new ItemStack(Items.experience_bottle),
                new ItemStack(Items.emerald));
    }
}
