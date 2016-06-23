package kyne.xpessence.items;

import kyne.xpessence.entities.EntitySpark;
import kyne.xpessence.tab.ModTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;

public class BasicWand extends Item {

    public BasicWand() {
        this.setUnlocalizedName("basic_wand");
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final ItemStack itemStackIn, final World worldIn,
                                                    final EntityPlayer playerIn, final EnumHand hand) {
        worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 0.3F,
                0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            final EntitySpark entitySpark = new EntitySpark(worldIn, playerIn);
            entitySpark.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntityInWorld(entitySpark);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List<String> tooltip,
                               final boolean advanced) {
        tooltip.add("This is your first wand.");
        tooltip.add("Unfortunately, it doesn't do much");
    }
}
