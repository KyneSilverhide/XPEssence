package kyne.xpessence.recipes;

import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModShapedRecipes {

    public static void registerShapedRecipes() {
        xpGemToBlock();
        xpGlassToXpGlassPane();
        xpInfuser();
        xpCrucible();
        glassPaneToEmptyCrystal();
        experienceTorch();
        basicWand();
    }

    private static void basicWand() {
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.basicWand), "g  ", " s ", "  g",
                'g', ModItems.xpGem,
                's', Items.STICK);
    }

    private static void xpInfuser() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.xpInfuserOff), "sgs", "sfs", "sps",
                's', Item.getItemFromBlock(Blocks.STONE),
                'g', ModItems.xpGem,
                'f', Item.getItemFromBlock(Blocks.FURNACE),
                'p', Blocks.PISTON);
    }

    private static void xpCrucible() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.xpCrucibleOff), "sgs", "sfs", "sgs",
                's', Item.getItemFromBlock(Blocks.STONE),
                'g', ModItems.xpGem,
                'f', Item.getItemFromBlock(Blocks.FURNACE));
    }

    private static void xpGemToBlock() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.xpGemBlock), "xxx", "xxx", "xxx",
                'x', ModItems.xpGem);
    }

    private static void xpGlassToXpGlassPane() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.xpGemGlassPane, 16), "ggg", "ggg",
                'g', ModBlocks.xpGemGlassBlock);
    }

    private static void glassPaneToEmptyCrystal() {
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.emptyCrystal), " p ", "p p", " p ",
                'p', Blocks.GLASS_PANE);
    }

    private static void experienceTorch() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.essenceTorch, 2), "tgt",
                't', Blocks.TORCH, 'g', ModItems.xpGem);
    }
}
