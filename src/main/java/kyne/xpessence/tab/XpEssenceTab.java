package kyne.xpessence.tab;

import kyne.xpessence.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class XpEssenceTab extends CreativeTabs {

    public XpEssenceTab() {
        super("xp_essence_tab");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return ModItems.xpGem;
    }

}
