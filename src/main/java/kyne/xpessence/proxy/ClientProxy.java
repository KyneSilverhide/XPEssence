package kyne.xpessence.proxy;

import kyne.xpessence.Constants;
import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.entities.ModEntities;
import kyne.xpessence.fluids.ModFluids;
import kyne.xpessence.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;

public class ClientProxy extends CommonProxy {

    public static final String PROXY_CLASS = "kyne." + Constants.MODID + ".proxy.ClientProxy";


    @Override
    public void preInit() {
        super.preInit();
        for (final Block blockFluid : ModFluids.getBlockFluids()) {
            registerFluidModel((IFluidBlock) blockFluid);
        }
    }

    @Override
    public void init() {
        super.init();

        ModItems.registerRenders();
        ModBlocks.registerRenders();
        ModEntities.registerRenders();
    }

    private void registerFluidModel(final IFluidBlock fluidBlock) {
        final Item item = Item.getItemFromBlock((Block) fluidBlock);
        ModelBakery.registerItemVariants(item);
        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Constants.MODID + ":fluids",
                fluidBlock.getFluid().getName());

        ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
                return modelResourceLocation;
            }
        });
    }
}
