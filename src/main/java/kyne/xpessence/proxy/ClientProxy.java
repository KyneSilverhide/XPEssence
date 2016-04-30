package kyne.xpessence.proxy;

import kyne.xpessence.Reference;
import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.items.ModItems;

public class ClientProxy extends CommonProxy {

    public static final String PROXY_CLASS = "kyne." + Reference.MODID + ".proxy.ClientProxy";

    @Override
    public void registerRenders() {
        ModItems.registerRenders();
        ModBlocks.registerRenders();
    }
}
