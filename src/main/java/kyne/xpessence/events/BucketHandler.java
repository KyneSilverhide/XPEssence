package kyne.xpessence.events;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class BucketHandler {

    public static Map<Block, Item> buckets = new HashMap<Block, Item>();

    @SubscribeEvent
    public void onBucketFill(final FillBucketEvent event) {
        final ItemStack result = fillCustomBucket(event.world, event.target);
        if (result == null) {
            return;
        }
        event.result = result;
        event.setResult(Result.ALLOW);
    }

    public static void addBucket(final Block fluidBlock, final Item bucketItem) {
        buckets.put(fluidBlock, bucketItem);
    }

    private ItemStack fillCustomBucket(final World world, final MovingObjectPosition pos) {
        final Block block = world.getBlockState(pos.getBlockPos()).getBlock();

        final Item bucket = buckets.get(block);
        if (bucket != null) {
            world.setBlockState(pos.getBlockPos(), Blocks.air.getDefaultState());
            return new ItemStack(bucket);
        } else {
            return null;
        }
    }
}