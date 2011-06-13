package net.minecraft.src.redstoneExtended;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.redstoneExtended.Util.*;
import org.lwjgl.opengl.GL11;

public class MyRenderBlocks {
    public static boolean renderStandardBlockWithOverlay(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        renderBlocks.renderStandardBlock(block, x, y, z);

        if (block instanceof IBlockWithOverlay) {
            IBlockWithOverlay iBlockWithOverlay = (IBlockWithOverlay)block;
            for (int layer = 0; layer < 8; layer++) {
                for (int face = 0; face < 6; face++) {
                    if (iBlockWithOverlay.shouldOverlayBeRendered(iBlockAccess, x, y, z, face, layer)) {
                        int textureId = iBlockWithOverlay.getBlockOverlayTexture(iBlockAccess, x, y, z, face, layer);
                        Vector3d offset = new Vector3d(0D);
                        Vector3d scale = new Vector3d(1D);
                        double rotation = 0D;
                        Vector2d textureOffset = new Vector2d(0D);
                        Vector2d textureScale = new Vector2d(1D);
                        ColorRGB colorMultiplier = ColorRGB.Colors.White;
                        boolean ignoreLighting = false;

                        if (block instanceof IBlockWithOverlayEx) {
                            IBlockWithOverlayEx iBlockWithOverlayEx = (IBlockWithOverlayEx)block;

                            offset = iBlockWithOverlayEx.getOverlayOffset(iBlockAccess, x, y, z, face, layer);
                            scale = iBlockWithOverlayEx.getOverlayScale(iBlockAccess, x, y, z, face, layer);
                            rotation = iBlockWithOverlayEx.getOverlayRotation(iBlockAccess, x, y, z, face, layer);
                            textureOffset = iBlockWithOverlayEx.getOverlayTextureOffset(iBlockAccess, x, y, z, face, layer);
                            textureScale = iBlockWithOverlayEx.getOverlayTextureScale(iBlockAccess, x, y, z, face, layer);
                            colorMultiplier = iBlockWithOverlayEx.getOverlayColorMultiplier(iBlockAccess, x, y, z, face, layer);
                            ignoreLighting = iBlockWithOverlayEx.shouldOverlayIgnoreLighting(iBlockAccess, x, y, z, face, layer);
                        }

                        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, face, new Position(x, y, z), textureId,
                                layer, offset, scale, rotation, textureOffset, textureScale, colorMultiplier, ignoreLighting);
                    }
                }
            }
        }

        return true;
    }

    public static void renderStandardBlockWithOverlayInv(RenderBlocks renderBlocks, Block block, int metadata) {
        Tessellator tessellator = Tessellator.instance;

        block.setBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderBlocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderBlocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderBlocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderBlocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderBlocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderBlocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, metadata));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public static boolean renderBlockRedstoneLightSensor(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        renderBlocks.renderStandardBlock(block, x, y, z);

        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), block.getBlockTextureFromSideAndMetadata(6, iBlockAccess.getBlockMetadata(x, y, z)),
                1, new Vector3d(0.0625D * 1.5D, 0D, 0.0625D * 1.5D), new Vector3d(0.5D * 0.75D, 1D, 0.5D * 0.75D), 0D, new Vector2d(), new Vector2d(1D), new ColorRGB(), false);
        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), Block.blockLapis.blockIndexInTexture,
                1, new Vector3d(0.5D, 0D, 0.125D), new Vector3d(0.5D * 0.75D, 1D, 0.5D * 0.75D), 0D, new Vector2d(), new Vector2d(1D), new ColorRGB(), false);
        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), Block.glass.blockIndexInTexture,
                2, new Vector3d(0.5D, 0D, 0.125D), new Vector3d(0.5D * 0.75D, 1D, 0.5D * 0.75D), 0D, new Vector2d(), new Vector2d(1D, 15D / 16D), new ColorRGB(), false);
        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), Block.blockLapis.blockIndexInTexture,
                1, new Vector3d(0.5D, 0D, 0.5D), new Vector3d(0.5D * 0.75D, 1D, 0.5D * 0.75D), 0D, new Vector2d(), new Vector2d(1D), new ColorRGB(), false);
        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), Block.glass.blockIndexInTexture,
                2, new Vector3d(0.5D, 0D, 0.5D), new Vector3d(0.5D * 0.75D, 1D, 0.5D * 0.75D), 0D, new Vector2d(0D, 1D / 256D), new Vector2d(1D, 15D / 16D), new ColorRGB(), false);
        boolean isActive = BlockRedstoneLightSensor.getState(iBlockAccess, x, y, z);
        float[] redstoneColor = isActive ? RenderBlocks.redstoneColors[13] : RenderBlocks.redstoneColors[0];
        ColorRGB redstoneColorRGB = new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), Block.redstoneWire.blockIndexInTexture,
                1, new Vector3d(0.0625D * 1.5D, 0D, 0.5D), new Vector3d(0.5D * 0.75D, 1D, 0.5D * 0.75D), 0D, new Vector2d(), new Vector2d(1D), redstoneColorRGB, isActive);

        return true;
    }

    public static boolean renderBlockRedstoneFlipFlop(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        renderBlocks.renderStandardBlock(block, x, y, z);

        double rotation = BlockRedstoneFlipFlop.getOrientation(iBlockAccess, x, y, z) * 90D;
        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), block.getBlockTextureFromSide(6),
                1, new Vector3d(), new Vector3d(1D), rotation, new Vector2d(), new Vector2d(1D), new ColorRGB(), false);
        boolean isActive = BlockRedstoneFlipFlop.getState(iBlockAccess, x, y, z);
        float[] redstoneColor = isActive ? RenderBlocks.redstoneColors[13] : RenderBlocks.redstoneColors[0];
        ColorRGB redstoneColorRGB = new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), block.getBlockTextureFromSide(7),
                1, new Vector3d(), new Vector3d(1D), rotation, new Vector2d(), new Vector2d(1D), redstoneColorRGB, isActive);
        redstoneColor = isActive ? RenderBlocks.redstoneColors[0] : RenderBlocks.redstoneColors[13];
        redstoneColorRGB = new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, 1, new Position(x, y, z), block.getBlockTextureFromSide(8),
                1, new Vector3d(), new Vector3d(1D), rotation, new Vector2d(), new Vector2d(1D), redstoneColorRGB, !isActive);

        return true;
    }

    public static boolean renderBlockTorch(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        int orientation = iBlockAccess.getBlockMetadata(x, y, z) & 0x7;
        Tessellator tessellator = Tessellator.instance;
        float brightness = block.getBlockBrightness(iBlockAccess, x, y, z);
        if (Block.lightValue[block.blockID] > 0) {
            brightness = 1.0F;
        }
        tessellator.setColorOpaque_F(brightness, brightness, brightness);
        double angle = 0.40000000596046448D;
        double offsetSide = 0.5D - angle;
        double offsetY = 0.20000000298023224D;
        switch (orientation) {
            case 1:
                renderBlocks.renderTorchAtAngle(block, (double)x - offsetSide, (double)y + offsetY, z, -angle, 0.0D);
                break;
            case 2:
                renderBlocks.renderTorchAtAngle(block, (double)x + offsetSide, (double)y + offsetY, z, angle, 0.0D);
                break;
            case 3:
                renderBlocks.renderTorchAtAngle(block, x, (double)y + offsetY, (double)z - offsetSide, 0.0D, -angle);
                break;
            case 4:
                renderBlocks.renderTorchAtAngle(block, x, (double)y + offsetY, (double)z + offsetSide, 0.0D, angle);
                break;
            case 5:
                renderBlocks.renderTorchAtAngle(block, x, y, z, 0.0D, 0.0D);
                break;
            case 6:
                RenderUtil.renderTorchOnCeiling(block, x, y + BlockRedstoneLightBulb.positionInCeilingY, z);
                break;
        }
        return true;
    }
}
