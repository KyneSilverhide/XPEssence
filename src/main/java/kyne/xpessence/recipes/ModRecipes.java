package kyne.xpessence.recipes;

import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void registerCraftingRecipes() {
        GameRegistry.addShapedRecipe(
                new ItemStack(ModBlocks.xpGemBlock),
                "xxx",
                "xxx",
                "xxx",
                'x', ModItems.xpGem);

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.xpGem, 9), new ItemStack(ModBlocks.xpGemBlock));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.xpApple), new ItemStack(ModItems.xpGem), new ItemStack(Items.apple));

    }
}
