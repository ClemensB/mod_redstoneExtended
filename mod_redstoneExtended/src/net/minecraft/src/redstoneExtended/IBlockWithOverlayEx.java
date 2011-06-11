package net.minecraft.src.redstoneExtended;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.redstoneExtended.Util.ColorRGB;
import net.minecraft.src.redstoneExtended.Util.Vector2d;
import net.minecraft.src.redstoneExtended.Util.Vector3d;

public interface IBlockWithOverlayEx extends IBlockWithOverlay {
    public Vector3d getOverlayOffset(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public Vector3d getOverlayScale(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public double getOverlayRotation(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public Vector2d getOverlayTextureOffset(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public Vector2d getOverlayTextureScale(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public ColorRGB getOverlayColorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);

    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer);
}
