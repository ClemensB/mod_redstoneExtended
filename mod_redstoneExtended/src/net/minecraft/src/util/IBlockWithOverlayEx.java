package net.minecraft.src.util;

import net.minecraft.src.IBlockAccess;

public interface IBlockWithOverlayEx extends IBlockWithOverlay {
    public Vector3d getOverlayOffset(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public Vector3d getOverlayOffsetInGUI(int side, int layer);

    public Vector3d getOverlayScale(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public Vector3d getOverlayScaleInGUI(int side, int layer);

    public double getOverlayRotation(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public double getOverlayRotationInGUI(int side, int layer);

    public Vector2d getOverlayTextureOffset(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public Vector2d getOverlayTextureOffsetInGUI(int side, int layer);

    public Vector2d getOverlayTextureScale(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public Vector2d getOverlayTextureScaleInGUI(int side, int layer);

    public ColorRGB getOverlayColorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public ColorRGB getOverlayColorMultiplierInGUI(int side, int layer);

    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);
}
