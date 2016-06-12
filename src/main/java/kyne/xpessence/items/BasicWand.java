package kyne.xpessence.items;

import kyne.xpessence.entities.EntityGreenFire;
import kyne.xpessence.tab.ModTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class BasicWand extends Item {

    public BasicWand() {
        this.setUnlocalizedName("basic_wand");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        worldIn.playSoundAtEntity(playerIn, "random.fizz", 0.3F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            worldIn.spawnEntityInWorld(new EntityGreenFire(worldIn, playerIn));
        }
        return itemStackIn;
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
        list.add("This is your first wand.");
        list.add("Unfortunately, it doesn't do much");
    }
}
