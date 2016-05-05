package kyne.xpessence.blocks;

import kyne.xpessence.XpEssence;
import kyne.xpessence.gui.GUIHandler;
import kyne.xpessence.tab.ModTabs;
import kyne.xpessence.tileentities.TileEntityXPFurnace;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockXPFurnace extends BlockFurnace {

    private static boolean keepInventory;
    private final boolean burning;

    public BlockXPFurnace(final boolean isBurning) {
        super(isBurning);
        burning = isBurning;
        this.setUnlocalizedName("xp_furnace_" + (isBurning ? "on" : "off"))
                .setHardness(4F)
                .setStepSound(soundTypeStone);
        if (!isBurning) {
            this.setCreativeTab(ModTabs.creativeTab);
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        keepInventory = true;
        if (active) {
            worldIn.setBlockState(pos, ModBlocks.xpFurnaceOn.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        } else {
            worldIn.setBlockState(pos, ModBlocks.xpFurnaceOff.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        keepInventory = false;
        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityXPFurnace) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFurnace) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityXPFurnace();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side,
                                    float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return true;
        playerIn.openGui(XpEssence.instance, GUIHandler.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos) {
        return Item.getItemFromBlock(ModBlocks.xpFurnaceOff);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.xpFurnaceOff);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (this.burning) {
            EnumFacing enumfacing = state.getValue(FACING);
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            switch (enumfacing) {
                case WEST:
                    spawnParticlesAt(worldIn, d1, d0 - d3, d2 + d4);
                    break;
                case EAST:
                    spawnParticlesAt(worldIn, d1, d0 + d3, d2 + d4);
                    break;
                case NORTH:
                    spawnParticlesAt(worldIn, d1, d0 + d4, d2 - d3);
                    break;
                case SOUTH:
                    spawnParticlesAt(worldIn, d1, d0 + d4, d2 + d3);
            }
        }
    }

    private void spawnParticlesAt(World worldIn, double d1, double xCoord, double zCoord) {
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, xCoord, d1, zCoord, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.SLIME, xCoord, d1, zCoord, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public int getLightValue(IBlockAccess world, BlockPos pos) {
        return burning ? ModBlocks.xpGemBlock.getLightValue() : 0;
    }
}