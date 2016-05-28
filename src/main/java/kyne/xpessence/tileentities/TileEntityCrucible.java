package kyne.xpessence.tileentities;

import kyne.xpessence.blocks.BlockCrucible;
import kyne.xpessence.blocks.ModBlocks;
import kyne.xpessence.containers.ContainerCrucible;
import kyne.xpessence.tileentities.base.BasicTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;


public class TileEntityCrucible extends BasicTileEntity {

    private static final int[] slotsTop = new int[]{CrucibleContentConfig.INPUT_SLOT};
    private static final int[] slotsBottom = new int[]{CrucibleContentConfig.BUCKET_SLOT};
    private static final int[] slotsSides = new int[]{CrucibleContentConfig.BUCKET_SLOT};

    private final CrucibleContentConfig crucibleContentConfig;

    public TileEntityCrucible() {
        super(new CrucibleContentConfig(), "xp_crucible");
        crucibleContentConfig = (CrucibleContentConfig) tileEntityContentConfig;
    }

    @Override
    public void setInventorySlotContents(final int index, final ItemStack stack) {
        super.setInventorySlotContents(index, stack);

        if (index == CrucibleContentConfig.INPUT_SLOT && !isSameItemStackAlreadyInSlot(stack, index)) {
            crucibleContentConfig.setItemToTankStatus(0);
            markDirty();
        }
    }

    @Override
    public void update() {

        boolean needsUpdate = false;
        final boolean wasActive = isActive();

        final ItemStack inputItem = crucibleContentConfig.getInputSlot();
        final ItemStack bucketItem = crucibleContentConfig.getBucketSlot();

        if (!this.worldObj.isRemote) {

            if (wasActive != this.isActive()) {
                needsUpdate = true;
                BlockCrucible.setState(this.isActive(), this.worldObj, this.pos, ModBlocks.xpCrucibleOff, ModBlocks.xpCrucibleOff);
            }
        }

        if (needsUpdate) {
            this.markDirty();
        }
    }

    public boolean isActive() {
        return crucibleContentConfig.getItemToTankStatus() > 0;
    }

    @Override
    public int[] getSlotsForFace(final EnumFacing side) {
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
    }

    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerCrucible(playerInventory, this);
    }
}