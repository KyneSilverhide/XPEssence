package kyne.xpessence.items;

import kyne.xpessence.Constants;
import kyne.xpessence.events.BucketHandler;
import kyne.xpessence.fluids.ModFluids;
import net.minecraft.block.Block;
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
    public static Item devitalizedSeeds;
    public static Item emptyCrystal;
    public static Item bucketLiquidXP;

    public static void initItems() {
        xpGem = build(new ItemXPGem());
        xpApple = build(new ItemXPApple());
        xpSeeds = build(new ItemXPSeed());
        devitalizedSeeds = build(new ItemDevitalizedSeed());
        emptyCrystal = build(new ItemEmptyCrystal());
        bucketLiquidXP = build(new BucketLiquidExperience());
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

    public static void registerFluidContainers() {
        registerBucket(ModFluids.liquidExperienceBlock, ModItems.bucketLiquidXP);
    }

    private static void registerBucket(final Block fluidBlock, final Item bucketItem) {
        BucketHandler.addBucket(fluidBlock, bucketItem);
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
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
                new ModelResourceLocation(Constants.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
}