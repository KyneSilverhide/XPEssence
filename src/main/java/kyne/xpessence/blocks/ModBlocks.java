package kyne.xpessence.blocks;

import kyne.xpessence.Constants;
import kyne.xpessence.items.ItemBlockEssenceTorch;
import kyne.xpessence.items.ItemBlockXPGem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {

    private static final Map<Block, Class<? extends ItemBlock>> itemBlocks = new HashMap<Block, Class<? extends
            ItemBlock>>();

    public static Block xpGemBlock;
    public static Block xpCropBlock;
    public static Block xpGemGlassBlock;
    public static Block xpGemGlassPane;
    public static Block xpInfuserOn;
    public static Block xpInfuserOff;
    public static Block xpCrucibleOn;
    public static Block xpCrucibleOff;
    public static Block essenceTorch;

    public static void initBlocks() {
        xpGemBlock = build(new BlockXPGem(), ItemBlockXPGem.class);
        xpCropBlock = build(new BlockXPCrop());
        xpGemGlassBlock = build(new BlockXPGemGlass());
        xpGemGlassPane = build(new BlockXPGemGlassPane());
        xpInfuserOn = build(new BlockInfuser(true));
        xpInfuserOff = build(new BlockInfuser(false));
        xpCrucibleOn = build(new BlockCrucible(true));
        xpCrucibleOn = build(new BlockCrucible(false));
        essenceTorch = build(new BlockEssenceTorch(), ItemBlockEssenceTorch.class);
    }

    private static Block build(final Block block, final Class<? extends ItemBlock> itemBlock) {
        itemBlocks.put(block, itemBlock);
        return block;
    }

    private static Block build(final Block block) {
        itemBlocks.put(block, null);
        return block;
    }

    public static void registerBlocks() {
        for (final Block modBlock : itemBlocks.keySet()) {
            final Class<? extends ItemBlock> matchingItemBlock = itemBlocks.get(modBlock);
            if (matchingItemBlock != null) {
                registerItemBlock(modBlock, itemBlocks.get(modBlock));
            } else {
                registerBlock(modBlock);
            }
        }
    }

    private static void registerItemBlock(final Block block, final Class<? extends ItemBlock> item) {
        GameRegistry.registerBlock(block, item, block.getUnlocalizedName().substring(5));
    }

    private static void registerBlock(final Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
    }

    public static void registerRenders() {
        for (final Block modBlock : itemBlocks.keySet()) {
            registerRender(modBlock);
        }
    }

    private static void registerRender(final Block block) {
        final Item blockItem = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(blockItem, 0,
                new ModelResourceLocation(Constants.MODID + ":" + blockItem.getUnlocalizedName().substring(5),
                        "inventory"));
    }
}