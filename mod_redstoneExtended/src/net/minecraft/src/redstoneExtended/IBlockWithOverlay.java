package net.minecraft.src.redstoneExtended;

import net.minecraft.src.IBlockAccess;

public interface IBlockWithOverlay {
    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);
}
