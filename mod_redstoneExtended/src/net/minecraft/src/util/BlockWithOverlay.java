package net.minecraft.src.util;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;

public abstract class BlockWithOverlay extends Block implements IBlockWithOverlayEx {
    protected BlockWithOverlay(int id, Material material) {
        super(id, material);
    }

    protected BlockWithOverlay(int id, int texture, Material material) {
        super(id, texture, material);
    }

    @Override
    public boolean shouldOverlayBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return shouldOverlayBeRenderedInGUI(side, layer);
    }

    @Override
    public boolean shouldOverlayBeRenderedInGUI(int side, int layer) {
        return false;
    }

    @Override
    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getBlockOverlayTextureInGUI(side, layer);
    }

    @Override
    public int getBlockOverlayTextureInGUI(int side, int layer) {
        return TextureManager.getInstance().emptyTexture;
    }

    @Override
    public Vector3d getOverlayOffset(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getOverlayOffsetInGUI(side, layer);
    }

    @Override
    public Vector3d getOverlayOffsetInGUI(int side, int layer) {
        return new Vector3d(0D);
    }

    @Override
    public Vector3d getOverlayScale(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getOverlayScaleInGUI(side, layer);
    }

    @Override
    public Vector3d getOverlayScaleInGUI(int side, int layer) {
        return new Vector3d(1D);
    }

    @Override
    public double getOverlayRotation(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getOverlayRotationInGUI(side, layer);
    }

    @Override
    public double getOverlayRotationInGUI(int side, int layer) {
        return 0D;
    }

    @Override
    public Vector2d getOverlayTextureOffset(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getOverlayTextureOffsetInGUI(side, layer);
    }

    @Override
    public Vector2d getOverlayTextureOffsetInGUI(int side, int layer) {
        return new Vector2d(0D);
    }

    @Override
    public Vector2d getOverlayTextureScale(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getOverlayTextureScaleInGUI(side, layer);
    }

    @Override
    public Vector2d getOverlayTextureScaleInGUI(int side, int layer) {
        return new Vector2d(1D);
    }

    @Override
    public ColorRGB getOverlayColorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getOverlayColorMultiplierInGUI(side, layer);
    }

    @Override
    public ColorRGB getOverlayColorMultiplierInGUI(int side, int layer) {
        return ColorRGB.Colors.White;
    }

    @Override
    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return shouldOverlayIgnoreLightingInGUI(side, layer);
    }

    @Override
    public boolean shouldOverlayIgnoreLightingInGUI(int side, int layer) {
        return false;
    }
}
