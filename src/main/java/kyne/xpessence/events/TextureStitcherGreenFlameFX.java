package kyne.xpessence.events;

import kyne.xpessence.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitcherGreenFlameFX {

    @SubscribeEvent
    public void stitcherEventPre(final TextureStitchEvent.Pre event) {
        final ResourceLocation flameRL = new ResourceLocation(Constants.MODID + ":entity/flame_green_fx");
        event.map.registerSprite(flameRL);
    }
}