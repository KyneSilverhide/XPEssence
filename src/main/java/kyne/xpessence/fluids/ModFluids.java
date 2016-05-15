package kyne.xpessence.fluids;

import kyne.xpessence.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModFluids {

    public static Fluid liquidExperience;
    public static BlockFluidClassic liquidExperienceBlock;

    public static void registerFluids() {
        liquidExperience = registerFluid(new FluidLiquidExperience());
        liquidExperienceBlock = registerFluidBlock(new BlockFluidClassic(liquidExperience, Material.water));
        registerFluidModel(liquidExperienceBlock);
    }

    private static Fluid registerFluid(final Fluid fluid) {
        FluidRegistry.registerFluid(fluid);
        return fluid;
    }

    private static <T extends Block & IFluidBlock> T registerFluidBlock(final T block) {
        final String fluidName = block.getFluid().getUnlocalizedName();
        block.setUnlocalizedName(fluidName);
        GameRegistry.registerBlock(block, fluidName);
        return block;
    }

    private static void registerFluidModel(final IFluidBlock fluidBlock) {
        final Item item = Item.getItemFromBlock((Block) fluidBlock);
        ModelBakery.addVariantName(item);
        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Constants.MODID + ":fluid",
                fluidBlock.getFluid().getName());

        ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
                return modelResourceLocation;
            }
        });
    }
}