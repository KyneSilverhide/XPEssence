package kyne.xpessence.blocks;

import kyne.xpessence.tab.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXPGemGlassPane extends BlockPane {

    public BlockXPGemGlassPane() {
        super(Material.glass, true);
        this.setUnlocalizedName("xp_gemglass_pane");
        this.setCreativeTab(ModTabs.creativeTab);
        this.setLightLevel(0.7F);
        this.setHardness(1F);
        this.setStepSound(Block.soundTypeGlass);
        this.setResistance(4F);
        this.setLightOpacity(0);
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
}
