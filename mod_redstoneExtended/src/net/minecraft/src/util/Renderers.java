package net.minecraft.src.util;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class Renderers {
    public static boolean renderStandardBlockWithOverlay(RenderBlocks renderBlocks, IBlockAccess iBlockAccess,
                                                         Block block, int x, int y, int z) {
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
                            textureOffset = iBlockWithOverlayEx.getOverlayTextureOffset(iBlockAccess, x, y, z,
                                    face, layer);
                            textureScale = iBlockWithOverlayEx.getOverlayTextureScale(iBlockAccess, x, y, z,
                                    face, layer);
                            colorMultiplier = iBlockWithOverlayEx.getOverlayColorMultiplier(iBlockAccess, x, y, z,
                                    face, layer);
                            ignoreLighting = iBlockWithOverlayEx.shouldOverlayIgnoreLighting(iBlockAccess, x, y, z,
                                    face, layer);
                        }

                        RenderUtil.renderFaceOfBlockEx(iBlockAccess, block, face, new Position(x, y, z), textureId,
                                layer, offset, scale, rotation, textureOffset, textureScale, colorMultiplier,
                                ignoreLighting);
                    }
                }
            }
        }

        return true;
    }

    public static void renderStandardBlockWithOverlayInv(Block block, int metadata) {
        Tessellator tessellator = Tessellator.instance;

        FloatBuffer colorFloatBuffer = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_CURRENT_COLOR, colorFloatBuffer);

        float lightingColorMultiplierR = colorFloatBuffer.get(0);
        float lightingColorMultiplierG = colorFloatBuffer.get(1);
        float lightingColorMultiplierB = colorFloatBuffer.get(2);

        block.setBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        if (block instanceof IBlockWithOverlay) {
            IBlockWithOverlay iBlockWithOverlay = (IBlockWithOverlay)block;
            for (int face = 0; face < 6; face++) {
                Vector3d normal = new Vector3d(0D);
                switch (face) {
                    case 0:
                        normal = new Vector3d(0D, -1D, 0D);
                        break;
                    case 1:
                        normal = new Vector3d(0D, 1D, 0D);
                        break;
                    case 2:
                        normal = new Vector3d(0D, 0D, -1D);
                        break;
                    case 3:
                        normal = new Vector3d(0D, 0D, 1D);
                        break;
                    case 4:
                        normal = new Vector3d(-1D, 0D, 0D);
                        break;
                    case 5:
                        normal = new Vector3d(1D, 0D, 0D);
                        break;
                }

                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_F(lightingColorMultiplierR, lightingColorMultiplierG,
                        lightingColorMultiplierB);
                tessellator.setNormal((float)normal.X, (float)normal.Y, (float)normal.Z);

                RenderUtil.renderBlockFaceEx(block, face, new Vector3d(0D),
                        block.getBlockTextureFromSideAndMetadata(face, metadata),
                        0, new Vector3d(0D), new Vector3d(1D), 0D, new Vector2d(0D), new Vector2d(1D));

                tessellator.draw();

                for (int layer = 0; layer < 8; layer++) {
                    if (iBlockWithOverlay.shouldOverlayBeRenderedInGUI(face, layer)) {
                        int textureId = iBlockWithOverlay.getBlockOverlayTextureInGUI(face, layer);
                        Vector3d offset = new Vector3d(0D);
                        Vector3d scale = new Vector3d(1D);
                        double rotation = 0D;
                        Vector2d textureOffset = new Vector2d(0D);
                        Vector2d textureScale = new Vector2d(1D);
                        ColorRGB colorMultiplier = ColorRGB.Colors.White;
                        boolean ignoreLighting = false;

                        if (block instanceof IBlockWithOverlayEx) {
                            IBlockWithOverlayEx iBlockWithOverlayEx = (IBlockWithOverlayEx)block;

                            offset = iBlockWithOverlayEx.getOverlayOffsetInGUI(face, layer);
                            scale = iBlockWithOverlayEx.getOverlayScaleInGUI(face, layer);
                            rotation = iBlockWithOverlayEx.getOverlayRotationInGUI(face, layer);
                            textureOffset = iBlockWithOverlayEx.getOverlayTextureOffsetInGUI(face, layer);
                            textureScale = iBlockWithOverlayEx.getOverlayTextureScaleInGUI(face, layer);
                            colorMultiplier = iBlockWithOverlayEx.getOverlayColorMultiplierInGUI(face, layer);
                            ignoreLighting = iBlockWithOverlayEx.shouldOverlayIgnoreLightingInGUI(face, layer);
                        }

                        float colorR = (ignoreLighting ? 1F : lightingColorMultiplierR) *
                                ((float)colorMultiplier.R / 255);
                        float colorG = (ignoreLighting ? 1F : lightingColorMultiplierG) *
                                ((float)colorMultiplier.G / 255);
                        float colorB = (ignoreLighting ? 1F : lightingColorMultiplierB) *
                                ((float)colorMultiplier.B / 255);

                        tessellator.startDrawingQuads();
                        tessellator.setNormal((float)normal.X, (float)normal.Y, (float)normal.Z);
                        tessellator.setColorOpaque_F(colorR, colorG, colorB);

                        RenderUtil.renderBlockFaceEx(block, face, new Vector3d(0D), textureId,
                                layer, offset, scale, rotation, textureOffset, textureScale);

                        tessellator.draw();
                    }
                }
            }
        }

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public static boolean renderBlockTorch(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block,
                                           int x, int y, int z, double positionInCeilingY) {
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
                RenderUtil.renderTorchOnCeiling(block, x, y + positionInCeilingY, z);
                break;
        }
        return true;
    }
}
