package kyne.xpessence.items;

import kyne.xpessence.tab.ModTabs;
import kyne.xpessence.utils.XPUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemXPApple extends ItemFood {

    public ItemXPApple() {
        super(4, 0.4F, false);
        this.setUnlocalizedName("xp_apple");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    protected void onFoodEaten(final ItemStack stack, final World worldIn, final EntityPlayer player) {
        if (!worldIn.isRemote) {
            final BlockPos position = player.getPosition();
            XPUtils.addPlayerXP(player, XPUtils.XP_PER_GEM);
            worldIn.playSound(position.getX(), position.getY(), position.getZ(),
                    SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.7f, 1.0f, false);
            super.onFoodEaten(stack, worldIn, player);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add("Such apple. Many XP. Very tasty. Wow !");
    }
}
