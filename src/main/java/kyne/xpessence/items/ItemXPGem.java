package kyne.xpessence.items;

import kyne.xpessence.items.base.ItemXPFuel;
import kyne.xpessence.tab.ModTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
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
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final BlockPos position = playerIn.getPosition();
        worldIn.playSound(position.getX(), position.getY(), position.getZ(), "random.orb", 0.7f, 1.0f, false);

        itemStackIn.stackSize -= 1;
        playerIn.addExperience(10);
        return itemStackIn;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add("Use it to gain XP");
    }

    @Override
    public int getInfusingPower() {
        return 200;
    }
}
