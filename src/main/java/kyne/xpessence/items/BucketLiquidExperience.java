package kyne.xpessence.items;

import kyne.xpessence.fluids.ModFluids;
import kyne.xpessence.tab.ModTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BucketLiquidExperience extends ItemBucket {

    public BucketLiquidExperience() {
        super(ModFluids.liquidExperienceBlock);
        this.setUnlocalizedName("bucket_liquid_experience");
        this.setContainerItem(Items.bucket);
        this.setCreativeTab(ModTabs.creativeTab);
    }

    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
        list.add("Better than slime...");
    }
}
