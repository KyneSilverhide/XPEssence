package kyne.xpessence.blocks;

import kyne.xpessence.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static Block xpGemBlock;
    public static Block xpCropBlock;
    public static Block xpGemGlassBlock;

    public static void initBlocks() {
        xpGemBlock = new BlockXPGem();
        xpCropBlock = new BlockXPCrop();
        xpGemGlassBlock = new BlockXPGlassGem();
    }

    public static void registerBlocks() {
        registerBlock(xpGemBlock);
        registerBlock(xpCropBlock);
        registerBlock(xpGemGlassBlock);
    }

    public static void registerRenders() {
        registerRender(xpGemBlock);
        registerRender(xpCropBlock);
        registerRender(xpGemGlassBlock);
    }

    private static void registerBlock(final Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
    }

    private static void registerRender(final Block block) {
        final Item blockItem = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(blockItem, 0, new ModelResourceLocation(Reference.MODID + ":" + blockItem.getUnlocalizedName().substring(5),
                        "inventory"));
    }
}