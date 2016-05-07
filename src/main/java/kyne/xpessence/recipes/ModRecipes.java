package kyne.xpessence.recipes;

public class ModRecipes {

    public static void registerCraftingRecipes() {
        ModShapedRecipes.buildShapedRecipes();
        ModShapelessRecipes.buildShapelessRecipes();
        ModInfusingRecipes.buildSmeltingRecipes();
    }
}
