package kyne.xpessence.items;

import kyne.xpessence.tab.ModTabs;
import kyne.xpessence.utils.XPUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemEmptyCrystal extends Item {

    public ItemEmptyCrystal() {
        this.setUnlocalizedName("empty_crystal");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
                                                    EnumHand hand) {
        final BlockPos position = playerIn.getPosition();

        if (hasEnoughtExperience(playerIn)) {
            final boolean wasInserted = playerIn.inventory.addItemStackToInventory(new ItemStack(ModItems.xpGem));
            if (wasInserted) {
                playerIn.swingArm(EnumHand.MAIN_HAND);
                XPUtils.addPlayerXP(playerIn, -XPUtils.XP_PER_GEM);

                itemStackIn.stackSize -= 1;
                worldIn.playSound(position.getX(), position.getY(), position.getZ(),
                        SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.7f, 1.0f, false);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    private boolean hasEnoughtExperience(final EntityPlayer playerIn) {
        return playerIn.experienceTotal >= XPUtils.XP_PER_GEM;
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List<String> tooltip, final boolean advanced) {
        tooltip.add("Use it to fill with your own XP");
    }
}
