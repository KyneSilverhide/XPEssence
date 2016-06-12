package kyne.xpessence.proxy;

import kyne.xpessence.Constants;
import kyne.xpessence.XpEssence;
import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.entities.ModEntities;
import kyne.xpessence.events.BucketHandler;
import kyne.xpessence.events.MobDropEvent;
import kyne.xpessence.fluids.ModFluids;
import kyne.xpessence.gui.GUIHandler;
import kyne.xpessence.items.ModItems;
import kyne.xpessence.recipes.ModInfusingRecipes;
import kyne.xpessence.recipes.ModShapedRecipes;
import kyne.xpessence.recipes.ModShapelessRecipes;
import kyne.xpessence.tileentities.ModTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public static final String PROXY_CLASS = "kyne." + Constants.MODID + ".proxy.CommonProxy";

    public void preInit() {
        ModFluids.registerFluids();

        ModBlocks.initBlocks();
        ModItems.initItems();

        ModBlocks.registerBlocks();
        ModItems.registerItems();

        ModTileEntities.registerTileEntities();
        ModItems.registerFluidContainers();
    }

    public void init() {
        registerRecipes();
        registerGUIs();
        registerEvents();
        registerTallGrass();

        ModEntities.registerEntities();
    }

    private void registerRecipes() {
        ModShapedRecipes.registerShapedRecipes();
        ModShapelessRecipes.registerShapelessRecipes();
        ModInfusingRecipes.registerSmeltingRecipes();
    }

    private void registerGUIs() {
        NetworkRegistry.INSTANCE.registerGuiHandler(XpEssence.instance, new GUIHandler());
    }

    private void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new MobDropEvent());
        MinecraftForge.EVENT_BUS.register(new BucketHandler());
    }

    private void registerTallGrass() {
        MinecraftForge.addGrassSeed(new ItemStack(ModItems.devitalizedSeeds), 1);
    }

    public void postInit(){
        //...
    }
}