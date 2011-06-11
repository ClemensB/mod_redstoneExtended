package net.minecraft.src.redstoneExtended;

import net.minecraft.src.IBlockAccess;

public interface IBlockWithOverlay {
    public boolean shouldOverlayBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);
}
