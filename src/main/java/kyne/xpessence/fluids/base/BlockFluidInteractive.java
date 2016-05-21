package kyne.xpessence.fluids.base;

import kyne.xpessence.utils.BlockMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Map;

public class BlockFluidInteractive extends BlockFluidClassic {

    protected static final int IGNORE_METADATA = -1;
    private static final int DEFAULT_METADATA = 0;

    private final Map<BlockMetadata, BlockMetadata> interactions = new HashMap<BlockMetadata, BlockMetadata>();

    public BlockFluidInteractive(final Fluid fluid, final Material material) {
        super(fluid, material);
        this.setTickRandomly(true);
    }

    public void addInteraction(final Block preBlock, final Block postBlock) {
        addInteraction(preBlock, IGNORE_METADATA, postBlock, DEFAULT_METADATA);
    }

    public void addInteraction(final Block preBlock, final int currentMetadata, final Block postBlock, final int targetMetadata) {
        interactions.put(new BlockMetadata(preBlock, currentMetadata), new BlockMetadata(postBlock, targetMetadata));
    }

    public boolean hasInteraction(final Block preBlock, final int currentMetadata) {
        return interactions.containsKey(new BlockMetadata(preBlock, currentMetadata))
                || interactions.containsKey(new BlockMetadata(preBlock, IGNORE_METADATA));
    }

    public BlockMetadata getInteraction(final Block preBlock, final int preMeta) {
        if (interactions.containsKey(new BlockMetadata(preBlock, preMeta))) {
            return interactions.get(new BlockMetadata(preBlock, preMeta));
        }
        return interactions.get(new BlockMetadata(preBlock, IGNORE_METADATA));
    }
}
