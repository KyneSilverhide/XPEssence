package kyne.xpessence.items;

import kyne.xpessence.fluids.ModFluids;
import kyne.xpessence.tab.ModTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class BucketLiquidExperience extends ItemBucket {

    public BucketLiquidExperience() {
        super(ModFluids.liquidExperienceBlock);
        this.setUnlocalizedName("bucket_liquid_experience");
        this.setContainerItem(Items.bucket);
        this.setCreativeTab(ModTabs.creativeTab);
    }
}
