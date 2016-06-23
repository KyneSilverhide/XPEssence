package kyne.xpessence.blocks;

import kyne.xpessence.fx.GreenFireFX;
import kyne.xpessence.tab.ModTabs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEssenceTorch extends BlockTorch {

    public BlockEssenceTorch() {
        this.setUnlocalizedName("essence_torch");
        this.setHardness(0.0F);
        this.setLightLevel(0.9375F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand) {
        final EnumFacing enumfacing = state.getValue(FACING);
        final double posX = (double) pos.getX() + 0.5D;
        final double posY = (double) pos.getY() + 0.7D;
        final double posZ = (double) pos.getZ() + 0.5D;
        final double d3 = 0.22D;
        final double d4 = 0.27D;

        if (enumfacing.getAxis().isHorizontal()) {
            final EnumFacing enumfacing1 = enumfacing.getOpposite();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + d4 * (double) enumfacing1.getFrontOffsetX(),
                    posY + d3, posZ + d4 * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
            final GreenFireFX greenFireFX = new GreenFireFX(worldIn, posX + d4 * (double) enumfacing1.getFrontOffsetX(),
                    posY + d3, posZ + d4 * (double) enumfacing1.getFrontOffsetZ(), 0, 0, 0);
            Minecraft.getMinecraft().effectRenderer.addEffect(greenFireFX);
        } else {
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            final GreenFireFX greenFireFX = new GreenFireFX(worldIn, posX, posY, posZ, 0, 0, 0);
            Minecraft.getMinecraft().effectRenderer.addEffect(greenFireFX);
        }
    }
}
