package kyne.xpessence.items;

import kyne.xpessence.items.base.ItemXPFuel;
import kyne.xpessence.tab.ModTabs;
import kyne.xpessence.utils.XPUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemXPGem extends ItemXPFuel {

    public ItemXPGem() {
        this.setUnlocalizedName("xp_gem");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn,
                                                    final EnumHand hand) {
        final BlockPos position = playerIn.getPosition();
        worldIn.playSound(position.getX(), position.getY(), position.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.7f, 1.0f, false);

        itemStackIn.stackSize -= 1;
        XPUtils.addPlayerXP(playerIn, XPUtils.XP_PER_GEM);
        playerIn.swingArm(EnumHand.MAIN_HAND);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List<String> tooltip, final boolean advanced) {
        tooltip.add("Use it to gain XP");
    }

    @Override
    public int getInfusingPower() {
        return 200;
    }
}
