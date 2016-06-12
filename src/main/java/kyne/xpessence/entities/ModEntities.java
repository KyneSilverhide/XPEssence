package kyne.xpessence.entities;

import kyne.xpessence.XpEssence;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGreenFire.class,
                new RenderGreenFire<Entity>(Minecraft.getMinecraft().getRenderManager()));
    }

    public static void registerEntities() {
        EntityRegistry.registerModEntity(EntityGreenFire.class, "GreenFire", 1, XpEssence.instance, 64, 10, true);
    }
}
