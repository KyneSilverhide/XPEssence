package kyne.xpessence.utils;

import kyne.xpessence.items.base.ItemBlockXPFuel;
import kyne.xpessence.items.base.ItemXPFuel;
import net.minecraft.item.ItemStack;

public class FuelUtils {

    public static boolean isFuel(final ItemStack stack) {
        return stack.getItem() instanceof ItemXPFuel || stack.getItem() instanceof ItemBlockXPFuel;
    }
}
