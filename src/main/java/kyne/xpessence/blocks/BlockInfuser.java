package kyne.xpessence.blocks;

import kyne.xpessence.XpEssence;
import kyne.xpessence.gui.GUI;
import kyne.xpessence.tab.ModTabs;
import kyne.xpessence.tileentities.TileEntityInfuser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockInfuser extends BlockContainer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private static boolean keepInventory = false;
    private final boolean infusing;

    public BlockInfuser(final boolean infusing) {
        super(Material.rock);
        this.infusing = infusing;
        this.setUnlocalizedName("xp_infuser_" + (infusing ? "on" : "off"));
        this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        if(!infusing) {
            this.setCreativeTab(ModTabs.creativeTab);
        }
    }

    @Override
    public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
        return Item.getItemFromBlock(ModBlocks.xpInfuserOff);
    }

    @Override
    public void onBlockAdded(final World parWorld, final BlockPos parBlockPos, final IBlockState parIBlockState) {
        if (!parWorld.isRemote) {
            final Block blockToNorth = parWorld.getBlockState(parBlockPos.north()).getBlock();
            final Block blockToSouth = parWorld.getBlockState(parBlockPos.south()).getBlock();
            final Block blockToWest = parWorld.getBlockState(parBlockPos.west()).getBlock();
            final Block blockToEast = parWorld.getBlockState(parBlockPos.east()).getBlock();
            EnumFacing enumfacing = parIBlockState.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && blockToNorth.isFullBlock() && !blockToSouth.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && blockToSouth.isFullBlock() && !blockToNorth.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && blockToWest.isFullBlock() && !blockToEast.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && blockToEast.isFullBlock() && !blockToWest.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            parWorld.setBlockState(parBlockPos, parIBlockState.withProperty(FACING, enumfacing), 2);
        }
    }

    public static void setState(final boolean active, final World worldIn, final BlockPos pos) {
        final IBlockState iblockstate = worldIn.getBlockState(pos);
        final TileEntity tileentity = worldIn.getTileEntity(pos);

        keepInventory = true;
        if (active) {
            worldIn.setBlockState(pos, ModBlocks.xpInfuserOn.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        } else {
            worldIn.setBlockState(pos, ModBlocks.xpInfuserOff.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        keepInventory = false;
        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public boolean onBlockActivated(final World parWorld, final BlockPos parBlockPos, final IBlockState parIBlockState,
                                    final EntityPlayer parPlayer, final EnumFacing parSide, final float hitX,
                                    final float hitY, final float hitZ) {
        if (!parWorld.isRemote) {
            parPlayer.openGui(XpEssence.instance, GUI.INFUSER, parWorld, parBlockPos.getX(), parBlockPos.getY(), parBlockPos.getZ());
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityInfuser();
    }

    @Override
    public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
                                     final float hitY, final float hitZ, final int meta, final EntityLivingBase
                                                 placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer,
                                final ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        if(!keepInventory) {
            final TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityInfuser) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityInfuser) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
            super.breakBlock(worldIn, pos, state);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(final World worldIn, final BlockPos pos) {
        return Item.getItemFromBlock(ModBlocks.xpInfuserOff);
    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(final IBlockState state) {
        return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

    @Override
    public IBlockState getStateFromMeta(final int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(final IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING);
    }

    @Override
    public int getLightValue(final IBlockAccess world, final BlockPos pos) {
        return infusing ? ModBlocks.xpGemBlock.getLightValue() : 0;
    }
}