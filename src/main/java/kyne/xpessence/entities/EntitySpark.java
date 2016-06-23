package kyne.xpessence.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpark extends EntityThrowable {

    public EntitySpark(final World worldIn, final EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    // Required for reflection
    public EntitySpark(final World worldIn) {
        super(worldIn);
    }

    // Required for reflection
    public EntitySpark(final World worldIn, final double x, final double y, final double z) {
        super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate() {
        this.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        super.onUpdate();
    }

    protected void onImpact(final RayTraceResult position) {
        if (position.entityHit != null) {
            int i = 0;
            if (position.entityHit instanceof EntityBlaze) {
                i = 3;
            }
            position.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) i);
        }
        this.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}
