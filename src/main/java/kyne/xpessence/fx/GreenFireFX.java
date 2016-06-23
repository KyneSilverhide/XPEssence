package kyne.xpessence.fx;

import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.world.World;

public class GreenFireFX extends ParticleFlame {

    private static final int ENTITY_FLAME_INDEX = 48;

    public GreenFireFX(final World world, final double x, final double y, final double z, final double velocityX,
                       final double velocityY, final double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);

        setParticleTextureIndex(ENTITY_FLAME_INDEX);
        setRBGColorF(0, 50, 0);
    }
}