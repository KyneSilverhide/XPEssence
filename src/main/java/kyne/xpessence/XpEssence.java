package kyne.xpessence;

import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.events.BucketHandler;
import kyne.xpessence.events.MobDropEvent;
import kyne.xpessence.fluids.ModFluids;
import kyne.xpessence.gui.GUIHandler;
import kyne.xpessence.items.ModItems;
import kyne.xpessence.proxy.ClientProxy;
import kyne.xpessence.proxy.CommonProxy;
import kyne.xpessence.recipes.ModRecipes;
import kyne.xpessence.tileentities.ModTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.logging.Logger;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION)
public class XpEssence {

    public static Logger logger = Logger.getLogger("XP ESSENCE");

    @Mod.Instance(Constants.MODID)
    public static XpEssence instance;

    @SidedProxy(clientSide = ClientProxy.PROXY_CLASS, serverSide = CommonProxy.PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        logger.fine("Pre-initializing " + Constants.MODID + " " + event);

        ModFluids.registerFluids();

        ModBlocks.initBlocks();
        ModItems.initItems();

        ModItems.registerItems();
        ModBlocks.registerBlocks();

        ModItems.registerFluidContainers();

        ModTileEntities.registerTileEntities();
        ModRecipes.registerCraftingRecipes();

        MinecraftForge.addGrassSeed(new ItemStack(ModItems.devitalizedSeeds), 1);
        NetworkRegistry.INSTANCE.registerGuiHandler(XpEssence.instance, new GUIHandler());
        MinecraftForge.EVENT_BUS.register(new MobDropEvent());
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        logger.fine("Initializing " + Constants.MODID + " " + event);
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void postInit(final FMLInitializationEvent event) {
        logger.fine("Post-initializing " + Constants.MODID + " " + event);
    }
}
