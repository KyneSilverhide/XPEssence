package kyne.xpessence.items;

import kyne.xpessence.tab.ModTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemXPApple extends ItemFood {

    public ItemXPApple() {
        super(4, 0.4F, false);
        this.setUnlocalizedName("xp_apple");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    protected void onFoodEaten(final ItemStack stack, final World worldIn, final EntityPlayer player) {
        player.addExperience(10);
        final BlockPos position = player.getPosition();
        worldIn.playSound(position.getX(), position.getY(), position.getZ(), "random.orb", 0.7f, 1.0f, false);
        super.onFoodEaten(stack, worldIn, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return true;
    }
}
