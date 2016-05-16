package kyne.xpessence.fluids;

import kyne.xpessence.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidLiquidExperience extends Fluid {

    public FluidLiquidExperience() {
        super("liquid_experience", new ResourceLocation(Constants.MODID, "blocks/liquid_xp_still"),
                new ResourceLocation(Constants.MODID, "blocks/liquid_xp_flowing"));

    }
}