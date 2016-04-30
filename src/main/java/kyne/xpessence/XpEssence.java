package kyne.xpessence;

import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.items.ModItems;
import kyne.xpessence.proxy.ClientProxy;
import kyne.xpessence.proxy.CommonProxy;
import kyne.xpessence.recipes.ModRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.logging.Logger;

@Mod(modid = Reference.MODID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class XpEssence {

    public static Logger logger = Logger.getLogger("XP ESSENCE");

    @Mod.Instance(Reference.MODID)
    public static XpEssence instance;

    @SidedProxy(clientSide = ClientProxy.PROXY_CLASS, serverSide = CommonProxy.PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        logger.fine("Pre-initializing " + Reference.MODID + " " + event);

        ModBlocks.initBlocks();
        ModItems.initItems();

        ModItems.registerItems();
        ModBlocks.registerBlocks();

        ModRecipes.registerCraftingRecipes();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        logger.fine("Initializing " + Reference.MODID + " " + event);
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void postInit(final FMLInitializationEvent event) {
        logger.fine("Post-initializing " + Reference.MODID + " " + event);
    }
}
