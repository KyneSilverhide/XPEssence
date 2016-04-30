package kyne.xpessence.items;

import kyne.xpessence.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    private static final List<Item> modItems = new ArrayList<Item>();
    public static Item xpGem;
    public static Item xpApple;
    public static Item xpSeeds;

    public static void initItems() {
        xpGem = build(new ItemXPGem());
        xpApple = build(new ItemXPApple());
        xpSeeds = build(new ItemXPSeed());
    }

    private static Item build(final Item item) {
        modItems.add(item);
        return item;
    }

    public static void registerItems() {
        for (final Item modItem : modItems) {
            registerItem(modItem);
        }
    }

    private static void registerItem(final Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
    }

    public static void registerRenders() {
        for (final Item modItem : modItems) {
            registerRender(modItem);
        }
    }

    private static void registerRender(final Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5),
                        "inventory"));
    }
}