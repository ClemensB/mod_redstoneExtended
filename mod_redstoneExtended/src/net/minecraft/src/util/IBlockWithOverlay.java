package net.minecraft.src.util;

import net.minecraft.src.IBlockAccess;

public interface IBlockWithOverlay {
    public boolean shouldOverlayBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public boolean shouldOverlayBeRenderedInGUI(int side, int layer);

    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public int getBlockOverlayTextureInGUI(int side, int layer);
}
