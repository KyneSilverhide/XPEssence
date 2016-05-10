package kyne.xpessence.recipes;

import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModShapedRecipes {

    public static void buildShapedRecipes() {
        xpGemToBlock();
        xpGemToGlass();
        xpGlassToXpGlassPane();
        xpInfuser();
    }

    private static void xpInfuser() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.xpInfuserOff), "sgs", "sfs", "sgs",
                's', Item.getItemFromBlock(Blocks.stone),
                'g', ModItems.xpGem,
                'f', Item.getItemFromBlock(Blocks.furnace));
    }

    private static void xpGemToBlock() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.xpGemBlock), "xxx", "xxx", "xxx",
                'x', ModItems.xpGem);
    }

    private static void xpGemToGlass() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.xpGemGlassBlock, 2), "xgx",
                'x', Blocks.glass,
                'g', ModItems.xpGem);
    }

    private static void xpGlassToXpGlassPane() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.xpGemGlassPane, 16), "ggg", "ggg",
                'g', ModBlocks.xpGemGlassBlock);
    }
}
