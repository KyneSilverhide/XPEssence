package kyne.xpessence.fx;

import kyne.xpessence.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GreenFireFX extends EntityFX {

    public GreenFireFX(final World world, final double x, final double y, final double z, final double velocityX,
                       final double velocityY, final double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);

        this.particleGravity = Blocks.fire.blockParticleGravity;
        this.particleMaxAge = 100;
        this.particleAlpha = 0.99F;

        motionX = velocityX;
        motionY = velocityY;
        motionZ = velocityZ;

        final ResourceLocation flameRL = new ResourceLocation(Constants.MODID + ":entity/flame_green_fx");
        final TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(flameRL.toString());
        setParticleIcon(sprite);
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public float getAlpha() {
        return 1.0F;
    }

    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        moveEntity(motionX, motionY, motionZ);

        if (isCollided) {
            this.setDead();
        }
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }

    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float partialTick,
                               final float edgeLRdirectionX, final float edgeUDdirectionY, final float edgeLRdirectionZ,
                               final float edgeUDdirectionX, final float edgeUDdirectionZ) {
        final double minU = this.particleIcon.getMinU();
        final double maxU = this.particleIcon.getMaxU();
        final double minV = this.particleIcon.getMinV();
        final double maxV = this.particleIcon.getMaxV();

        final double scale = 0.1F * this.particleScale;
        final double x = this.prevPosX + (this.posX - this.prevPosX) * partialTick - interpPosX;
        final double y = this.prevPosY + (this.posY - this.prevPosY) * partialTick - interpPosY;
        final double z = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTick - interpPosZ;

        final int combinedBrightness = this.getBrightnessForRender(partialTick);
        final int skyLightTimes16 = combinedBrightness >> 16 & 65535;
        final int blockLightTimes16 = combinedBrightness & 65535;

        worldRenderer.pos(x - edgeLRdirectionX * scale - edgeUDdirectionX * scale, y - edgeUDdirectionY * scale,
                z - edgeLRdirectionZ * scale - edgeUDdirectionZ * scale).tex(maxU, maxV).color(this.particleRed,
                this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(skyLightTimes16,
                blockLightTimes16).endVertex();
        worldRenderer.pos(x - edgeLRdirectionX * scale + edgeUDdirectionX * scale, y + edgeUDdirectionY * scale,
                z - edgeLRdirectionZ * scale + edgeUDdirectionZ * scale).tex(maxU, minV).color(this.particleRed,
                this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(skyLightTimes16,
                blockLightTimes16).endVertex();
        worldRenderer.pos(x + edgeLRdirectionX * scale + edgeUDdirectionX * scale, y + edgeUDdirectionY * scale,
                z + edgeLRdirectionZ * scale + edgeUDdirectionZ * scale).tex(minU, minV).color(this.particleRed,
                this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(skyLightTimes16,
                blockLightTimes16).endVertex();
        worldRenderer.pos(x + edgeLRdirectionX * scale - edgeUDdirectionX * scale, y - edgeUDdirectionY * scale,
                z + edgeLRdirectionZ * scale - edgeUDdirectionZ * scale).tex(minU, maxV).color(this.particleRed,
                this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(skyLightTimes16,
                blockLightTimes16).endVertex();
    }

    @Override
    public int getBrightnessForRender(final float partialTick) {
        return 0xf000f0; // Full brightness
    }
}