package kyne.xpessence.items;

import kyne.xpessence.tab.ModTabs;
import kyne.xpessence.utils.XPUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemEmptyCrystal extends Item {

    public ItemEmptyCrystal() {
        this.setUnlocalizedName("empty_crystal");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final BlockPos position = playerIn.getPosition();

        if (hasEnoughtExperience(playerIn)) {
            final boolean wasInserted = playerIn.inventory.addItemStackToInventory(new ItemStack(ModItems.xpGem));
            if (wasInserted) {
                playerIn.swingItem();
                XPUtils.addPlayerXP(playerIn, -XPUtils.XP_PER_GEM);

                itemStackIn.stackSize -= 1;
                worldIn.playSound(position.getX(), position.getY(), position.getZ(), "random.orb", 0.7f, 1.0f, false);
            }
        }

        return itemStackIn;
    }

    private boolean hasEnoughtExperience(final EntityPlayer playerIn) {
        return playerIn.experienceTotal >= XPUtils.XP_PER_GEM;
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
        list.add("Use it to fill with your own XP");
    }
}
