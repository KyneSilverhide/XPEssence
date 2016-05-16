package kyne.xpessence;

import kyne.xpessence.proxy.ClientProxy;
import kyne.xpessence.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        logger.fine("Initializing " + Constants.MODID + " " + event);
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(final FMLInitializationEvent event) {
        logger.fine("Post-initializing " + Constants.MODID + " " + event);
        proxy.postInit();
    }
}
