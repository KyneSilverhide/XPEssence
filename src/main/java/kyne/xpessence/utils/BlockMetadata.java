package kyne.xpessence.utils;

import net.minecraft.block.Block;

public class BlockMetadata {

    private final Block block;
    private final int metadata;

    public BlockMetadata(final Block block, final int metadata) {
        this.block = block;
        this.metadata = metadata;
    }

    public Block getBlock() {
        return block;
    }

    public int getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final BlockMetadata that = (BlockMetadata) o;
        return metadata == that.metadata && (block != null ? block.equals(that.block) : that.block == null);

    }

    @Override
    public int hashCode() {
        int result = block != null ? block.hashCode() : 0;
        result = 31 * result + metadata;
        return result;
    }

    @Override
    public String toString() {
        return "BlockMetadata{" + "block=" + block +
                ", metadata=" + metadata +
                '}';
    }
}
