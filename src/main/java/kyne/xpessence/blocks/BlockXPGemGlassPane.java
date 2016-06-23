package kyne.xpessence.blocks;

import kyne.xpessence.tab.ModTabs;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockXPGemGlassPane extends BlockPane {

    public BlockXPGemGlassPane() {
        super(Material.GLASS, true);
        this.setUnlocalizedName("xp_gemglass_pane");
        this.setCreativeTab(ModTabs.creativeTab);
        this.setLightLevel(0.7F);
        this.setHardness(1F);
        this.setSoundType(SoundType.GLASS);
        this.setResistance(4F);
        this.setLightOpacity(0);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
