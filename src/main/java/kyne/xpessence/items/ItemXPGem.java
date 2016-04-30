package kyne.xpessence.items;

import kyne.xpessence.tab.ModTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemXPGem extends Item {

    public ItemXPGem() {
        this.setUnlocalizedName("xp_gem");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        playerIn.addExperience(10);
        final BlockPos position = playerIn.getPosition();
        worldIn.playSound(position.getX(), position.getY(), position.getZ(), "random.orb", 0.7f, 1.0f, false);
        return new ItemStack(itemStackIn.getItem(), itemStackIn.stackSize -1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return true;
    }
}
