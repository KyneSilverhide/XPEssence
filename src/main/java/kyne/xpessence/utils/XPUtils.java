package kyne.xpessence.utils;

import net.minecraft.entity.player.EntityPlayer;

public class XPUtils {

    public static final int XP_PER_GEM = 10;

    public static void addPlayerXP(final EntityPlayer player, final int amount) {
        if (amount < 0) {
            removePlayerXP(player, -amount);
        } else {
            player.addExperience(amount);
        }
    }

    // Thanks to the awesome quality of Minecraft's code, it's much more safe to do this very basic implementation...
    public static void removePlayerXP(final EntityPlayer player, final int amount) {
        final int currentXP = player.experienceTotal;
        final int currentScore = player.getScore();
        player.experience = 0;
        player.experienceLevel = 0;
        player.experienceTotal = 0;
        player.addExperience(currentXP - amount);
        player.setScore(currentScore);
    }
}
