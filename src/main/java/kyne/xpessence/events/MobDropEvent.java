package kyne.xpessence.events;

import kyne.xpessence.items.ModItems;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class MobDropEvent {

    private final static Random random = new Random();

    @SubscribeEvent
    public void onEntityDrop(final LivingDropsEvent event) {

        final int fortune = event.lootingLevel;
        final boolean shouldDrop = random.nextInt(100) < (6 + fortune * 2);
        if(shouldDrop) {
            event.entityLiving.dropItem(ModItems.xpGem, 1);
        }
    }
}
