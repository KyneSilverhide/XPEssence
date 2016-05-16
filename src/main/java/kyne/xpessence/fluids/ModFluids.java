package kyne.xpessence.fluids;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModFluids {

    private static final List<Block> blockFluids = new ArrayList<Block>();

    public static Fluid liquidExperience;
    public static Block liquidExperienceBlock;

    public static void registerFluids() {
        liquidExperience = registerFluid(new FluidLiquidExperience());
        liquidExperienceBlock = registerFluidBlock(new BlockLiquidExperience());
    }

    private static Fluid registerFluid(final Fluid fluid) {
        FluidRegistry.registerFluid(fluid);
        return fluid;
    }

    public static List<Block> getBlockFluids() {
        return blockFluids;
    }

    private static <T extends Block & IFluidBlock> T registerFluidBlock(final T block) {
        final String fluidName = block.getFluid().getUnlocalizedName();
        block.setUnlocalizedName(fluidName);
        GameRegistry.registerBlock(block, fluidName);
        blockFluids.add(block);
        return block;
    }
}