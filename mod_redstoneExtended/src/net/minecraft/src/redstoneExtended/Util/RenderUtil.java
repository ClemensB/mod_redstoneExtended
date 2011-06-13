package net.minecraft.src.redstoneExtended.Util;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Tessellator;

public class RenderUtil {
    public static void renderFaceOfBlockEx(IBlockAccess iBlockAccess, Block block, int face, Position blockPos, int textureId, int layer, Vector3d offset,
                                           Vector3d scale, double rotation, Vector2d textureOffset, Vector2d textureScale, ColorRGB colorMultiplier, boolean ignoreLighting) {
        Tessellator tessellator = Tessellator.instance;

        float blockBrightness = block.getBlockBrightness(iBlockAccess, blockPos.X, blockPos.Y, blockPos.Z);
        Position relativeBlockPos = blockPos.getClone().moveInDirection(DirectionUtil.invertDirection(face));
        float relativeBlockBrightness = block.getBlockBrightness(iBlockAccess, relativeBlockPos.X, relativeBlockPos.Y, relativeBlockPos.Z);

        if (face == 1 && block.maxX != 1.0D && !block.blockMaterial.getIsLiquid())
            relativeBlockBrightness = blockBrightness;

        if (face == 2 && block.minZ > 0.0D)
            relativeBlockBrightness = blockBrightness;

        if (face == 3 && block.maxZ < 1.0D)
            relativeBlockBrightness = blockBrightness;

        if (face == 4 && block.minX > 0.0D)
            relativeBlockBrightness = blockBrightness;

        if (face == 5 && block.maxX < 1.0D)
            relativeBlockBrightness = blockBrightness;

        if (ignoreLighting)
            relativeBlockBrightness = 1F;

        tessellator.setColorOpaque_F(((float)(colorMultiplier.R & 0xff) / 255F) * relativeBlockBrightness, ((float)(colorMultiplier.G & 0xff) / 255F) * relativeBlockBrightness, ((float)(colorMultiplier.B & 0xff) / 255F) * relativeBlockBrightness);

        renderBlockFaceEx(block, face, blockPos.toVector(), textureId, layer, offset, scale, rotation, textureOffset, textureScale);
    }

    public static void renderBlockFaceEx(Block block, int face, Vector3d blockPos, int textureId, int layer,
                                         Vector3d offset, Vector3d scale, double rotation, Vector2d textureOffset, Vector2d textureScale) {
        Tessellator tessellator = Tessellator.instance;

        int textureIndexX = (textureId & 0xF) << 4;
        int textureIndexY = textureId & 0xF0;

        double textureUMin = ((double)textureIndexX + block.minX * 16D) / 256D;
        double textureUMax = (((double)textureIndexX + block.maxX * 16D) - 0.01D) / 256D;
        double textureVMin = ((double)textureIndexY + block.minZ * 16D) / 256D;
        double textureVMax = (((double)textureIndexY + block.maxZ * 16D) - 0.01D) / 256D;

        if (block.minX < 0.0D || block.maxX > 1.0D) {
            textureUMin = ((double)textureIndexX) / 256D;
            textureUMax = ((double)textureIndexX + 15.99D) / 256D;
        }

        if (block.minZ < 0.0D || block.maxZ > 1.0D) {
            textureVMin = ((double)textureIndexY) / 256D;
            textureVMax = ((double)textureIndexY + 15.99D) / 256D;
        }

        textureUMin += textureOffset.X;
        textureUMax += textureOffset.X;
        textureVMin += textureOffset.Y;
        textureVMax += textureOffset.Y;

        textureUMax = textureUMin + ((textureUMax - textureUMin) * textureScale.X);
        textureVMax = textureVMin + ((textureVMax - textureVMin) * textureScale.Y);

        Vector3d layerOffset = new Vector3d(0D, 0D, 0D);
        switch (face) {
            case 0:
                layerOffset.Y = -1D;
                break;
            case 1:
                layerOffset.Y = 1D;
                break;
            case 2:
                layerOffset.Z = 1D;
                break;
            case 3:
                layerOffset.Z = -1D;
                break;
            case 4:
                layerOffset.X = 1D;
                break;
            case 5:
                layerOffset.X = -1D;
                break;
        }
        layerOffset.multiply(layer * 0.001D);

        double absolutePosXMin = blockPos.X + block.minX + offset.X + layerOffset.X;
        double absolutePosXMax = blockPos.X + (block.minX + ((block.maxX - block.minX) * scale.X)) + offset.X + layerOffset.X;
        double absolutePosYMin = blockPos.Y + block.minY + layerOffset.Y;
        double absolutePosYMax = blockPos.Y + (block.minY + ((block.maxY - block.minY) * scale.Y)) + offset.Y + layerOffset.Y;
        double absolutePosZMin = blockPos.Z + block.minZ + offset.Z + layerOffset.Z;
        double absolutePosZMax = blockPos.Z + (block.minZ + ((block.maxZ - block.minZ) * scale.Z)) + offset.Z + layerOffset.Z;

        double rotationInRadians = Math.toRadians(rotation);

        double centerX = absolutePosXMin + ((absolutePosXMax - absolutePosXMin) / 2);
        double centerY = absolutePosYMin + ((absolutePosYMax - absolutePosYMin) / 2);
        double centerZ = absolutePosZMin + ((absolutePosZMax - absolutePosZMin) / 2);

        Vector3d quadTL = new Vector3d(0D, 0D, 0D),
                quadTR = new Vector3d(0D, 0D, 0D),
                quadBR = new Vector3d(0D, 0D, 0D),
                quadBL = new Vector3d(0D, 0D, 0D);
        Vector2d quadUVTL = new Vector2d(0D, 0D),
                quadUVTR = new Vector2d(0D, 0D),
                quadUVBR = new Vector2d(0D, 0D),
                quadUVBL = new Vector2d(0D, 0D);

        switch (face) {
            case 0:
                quadTL = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMin - centerX) - Math.sin(rotationInRadians) * (absolutePosZMax - centerZ),
                        absolutePosYMin,
                        centerZ + Math.sin(rotationInRadians) * (absolutePosXMin - centerX) + Math.cos(rotationInRadians) * (absolutePosZMax - centerZ));
                quadTR = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMin - centerX) - Math.sin(rotationInRadians) * (absolutePosZMin - centerZ),
                        absolutePosYMin,
                        centerZ + Math.sin(rotationInRadians) * (absolutePosXMin - centerX) + Math.cos(rotationInRadians) * (absolutePosZMin - centerZ));
                quadBR = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMax - centerX) - Math.sin(rotationInRadians) * (absolutePosZMin - centerZ),
                        absolutePosYMin,
                        centerZ + Math.sin(rotationInRadians) * (absolutePosXMax - centerX) + Math.cos(rotationInRadians) * (absolutePosZMin - centerZ));
                quadBL = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMax - centerX) - Math.sin(rotationInRadians) * (absolutePosZMax - centerZ),
                        absolutePosYMin,
                        centerZ + Math.sin(rotationInRadians) * (absolutePosXMax - centerX) + Math.cos(rotationInRadians) * (absolutePosZMax - centerZ));

                quadUVTL = new Vector2d(textureUMin, textureVMax);
                quadUVTR = new Vector2d(textureUMin, textureVMin);
                quadUVBR = new Vector2d(textureUMax, textureVMin);
                quadUVBL = new Vector2d(textureUMax, textureVMax);
                break;
            case 1:
                quadTL = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMax - centerX) - Math.sin(rotationInRadians) * (absolutePosZMax - centerZ),
                        absolutePosYMax,
                        centerZ + Math.sin(rotationInRadians) * (absolutePosXMax - centerX) + Math.cos(rotationInRadians) * (absolutePosZMax - centerZ));
                quadTR = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMax - centerX) - Math.sin(rotationInRadians) * (absolutePosZMin - centerZ),
                        absolutePosYMax,
                        centerZ + Math.sin(rotationInRadians) * (absolutePosXMax - centerX) + Math.cos(rotationInRadians) * (absolutePosZMin - centerZ));
                quadBR = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMin - centerX) - Math.sin(rotationInRadians) * (absolutePosZMin - centerZ),
                        absolutePosYMax,
                        centerZ + Math.sin(rotationInRadians) * (absolutePosXMin - centerX) + Math.cos(rotationInRadians) * (absolutePosZMin - centerZ));
                quadBL = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMin - centerX) - Math.sin(rotationInRadians) * (absolutePosZMax - centerZ),
                        absolutePosYMax,
                        centerZ + Math.sin(rotationInRadians) * (absolutePosXMin - centerX) + Math.cos(rotationInRadians) * (absolutePosZMax - centerZ));

                quadUVTL = new Vector2d(textureUMax, textureVMax);
                quadUVTR = new Vector2d(textureUMax, textureVMin);
                quadUVBR = new Vector2d(textureUMin, textureVMin);
                quadUVBL = new Vector2d(textureUMin, textureVMax);
                break;
            case 2:
                quadTL = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMin - centerX) - Math.sin(rotationInRadians) * (absolutePosYMax - centerY),
                        centerY + Math.sin(rotationInRadians) * (absolutePosXMin - centerX) + Math.cos(rotationInRadians) * (absolutePosYMax - centerY),
                        absolutePosZMax);
                quadTR = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMin - centerX) - Math.sin(rotationInRadians) * (absolutePosYMin - centerY),
                        centerY + Math.sin(rotationInRadians) * (absolutePosXMin - centerX) + Math.cos(rotationInRadians) * (absolutePosYMin - centerY),
                        absolutePosZMax);
                quadBR = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMax - centerX) - Math.sin(rotationInRadians) * (absolutePosYMin - centerY),
                        centerY + Math.sin(rotationInRadians) * (absolutePosXMax - centerX) + Math.cos(rotationInRadians) * (absolutePosYMin - centerY),
                        absolutePosZMax);
                quadBL = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMax - centerX) - Math.sin(rotationInRadians) * (absolutePosYMax - centerY),
                        centerY + Math.sin(rotationInRadians) * (absolutePosXMax - centerX) + Math.cos(rotationInRadians) * (absolutePosYMax - centerY),
                        absolutePosZMax);

                quadUVTL = new Vector2d(textureUMin, textureVMin);
                quadUVTR = new Vector2d(textureUMin, textureVMax);
                quadUVBR = new Vector2d(textureUMax, textureVMax);
                quadUVBL = new Vector2d(textureUMax, textureVMin);
                break;
            case 3:
                quadTL = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMin - centerX) - Math.sin(rotationInRadians) * (absolutePosYMax - centerY),
                        centerY + Math.sin(rotationInRadians) * (absolutePosXMin - centerX) + Math.cos(rotationInRadians) * (absolutePosYMax - centerY),
                        absolutePosZMin);
                quadTR = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMax - centerX) - Math.sin(rotationInRadians) * (absolutePosYMax - centerY),
                        centerY + Math.sin(rotationInRadians) * (absolutePosXMax - centerX) + Math.cos(rotationInRadians) * (absolutePosYMax - centerY),
                        absolutePosZMin);
                quadBR = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMax - centerX) - Math.sin(rotationInRadians) * (absolutePosYMin - centerY),
                        centerY + Math.sin(rotationInRadians) * (absolutePosXMax - centerX) + Math.cos(rotationInRadians) * (absolutePosYMin - centerY),
                        absolutePosZMin);
                quadBL = new Vector3d(centerX + Math.cos(rotationInRadians) * (absolutePosXMin - centerX) - Math.sin(rotationInRadians) * (absolutePosYMin - centerY),
                        centerY + Math.sin(rotationInRadians) * (absolutePosXMin - centerX) + Math.cos(rotationInRadians) * (absolutePosYMin - centerY),
                        absolutePosZMin);

                quadUVTL = new Vector2d(textureUMax, textureVMin);
                quadUVTR = new Vector2d(textureUMin, textureVMin);
                quadUVBR = new Vector2d(textureUMin, textureVMax);
                quadUVBL = new Vector2d(textureUMax, textureVMax);
                break;
            case 4:
                quadTL = new Vector3d(absolutePosXMax,
                        centerY + Math.cos(rotationInRadians) * (absolutePosYMin - centerY) - Math.sin(rotationInRadians) * (absolutePosZMax - centerZ),
                        centerZ + Math.sin(rotationInRadians) * (absolutePosYMin - centerY) + Math.cos(rotationInRadians) * (absolutePosZMax - centerZ));
                quadTR = new Vector3d(absolutePosXMax,
                        centerY + Math.cos(rotationInRadians) * (absolutePosYMin - centerY) - Math.sin(rotationInRadians) * (absolutePosZMin - centerZ),
                        centerZ + Math.sin(rotationInRadians) * (absolutePosYMin - centerY) + Math.cos(rotationInRadians) * (absolutePosZMin - centerZ));
                quadBR = new Vector3d(absolutePosXMax,
                        centerY + Math.cos(rotationInRadians) * (absolutePosYMax - centerY) - Math.sin(rotationInRadians) * (absolutePosZMin - centerZ),
                        centerZ + Math.sin(rotationInRadians) * (absolutePosYMax - centerY) + Math.cos(rotationInRadians) * (absolutePosZMin - centerZ));
                quadBL = new Vector3d(absolutePosXMax,
                        centerY + Math.cos(rotationInRadians) * (absolutePosYMax - centerY) - Math.sin(rotationInRadians) * (absolutePosZMax - centerZ),
                        centerZ + Math.sin(rotationInRadians) * (absolutePosYMax - centerY) + Math.cos(rotationInRadians) * (absolutePosZMax - centerZ));

                quadUVTL = new Vector2d(textureUMin, textureVMax);
                quadUVTR = new Vector2d(textureUMax, textureVMax);
                quadUVBR = new Vector2d(textureUMax, textureVMin);
                quadUVBL = new Vector2d(textureUMin, textureVMin);
                break;
            case 5:
                quadTL = new Vector3d(absolutePosXMin,
                        centerY + Math.cos(rotationInRadians) * (absolutePosYMax - centerY) - Math.sin(rotationInRadians) * (absolutePosZMax - centerZ),
                        centerZ + Math.sin(rotationInRadians) * (absolutePosYMax - centerY) + Math.cos(rotationInRadians) * (absolutePosZMax - centerZ));
                quadTR = new Vector3d(absolutePosXMin,
                        centerY + Math.cos(rotationInRadians) * (absolutePosYMax - centerY) - Math.sin(rotationInRadians) * (absolutePosZMin - centerZ),
                        centerZ + Math.sin(rotationInRadians) * (absolutePosYMax - centerY) + Math.cos(rotationInRadians) * (absolutePosZMin - centerZ));
                quadBR = new Vector3d(absolutePosXMin,
                        centerY + Math.cos(rotationInRadians) * (absolutePosYMin - centerY) - Math.sin(rotationInRadians) * (absolutePosZMin - centerZ),
                        centerZ + Math.sin(rotationInRadians) * (absolutePosYMin - centerY) + Math.cos(rotationInRadians) * (absolutePosZMin - centerZ));
                quadBL = new Vector3d(absolutePosXMin,
                        centerY + Math.cos(rotationInRadians) * (absolutePosYMin - centerY) - Math.sin(rotationInRadians) * (absolutePosZMax - centerZ),
                        centerZ + Math.sin(rotationInRadians) * (absolutePosYMin - centerY) + Math.cos(rotationInRadians) * (absolutePosZMax - centerZ));

                quadUVTL = new Vector2d(textureUMax, textureVMin);
                quadUVTR = new Vector2d(textureUMin, textureVMin);
                quadUVBR = new Vector2d(textureUMin, textureVMax);
                quadUVBL = new Vector2d(textureUMax, textureVMax);
                break;
        }

        tessellator.addVertexWithUV(quadTL.X, quadTL.Y, quadTL.Z, quadUVTL.X, quadUVTL.Y);
        tessellator.addVertexWithUV(quadTR.X, quadTR.Y, quadTR.Z, quadUVTR.X, quadUVTR.Y);
        tessellator.addVertexWithUV(quadBR.X, quadBR.Y, quadBR.Z, quadUVBR.X, quadUVBR.Y);
        tessellator.addVertexWithUV(quadBL.X, quadBL.Y, quadBL.Z, quadUVBL.X, quadUVBL.Y);
    }

    public static void renderTorchOnCeiling(Block block, double x, double y, double z) {
        Tessellator tessellator = Tessellator.instance;
        int textureIndex = block.getBlockTextureFromSide(0);
        int texturePositionX = (textureIndex & 0xF) << 4;
        int texturePositionY = textureIndex & 0xF0;
        float textureUMin = (float)texturePositionX / 256F;
        float textureUMax = ((float)texturePositionX + 15.99F) / 256F;
        float textureVMin = (float)texturePositionY / 256F;
        float textureVMax = ((float)texturePositionY + 15.99F) / 256F;
        double texTorchUMin = (double)textureUMin + 0.02734375D;
        double texTorchVMin = (double)textureVMin + 0.0234375D;
        double texTorchUMax = (double)textureUMin + 0.03515625D;
        double texTorchVMax = (double)textureVMin + 0.03125D;
        x += 0.5D;
        z += 0.5D;
        double d9 = x - 0.5D;
        double d10 = x + 0.5D;
        double d11 = z - 0.5D;
        double d12 = z + 0.5D;
        double d13 = 0.0625D;
        double d14 = 1D - 0.625D;
        tessellator.addVertexWithUV(x + d13, y + d14, z - d13, texTorchUMin, texTorchVMin);
        tessellator.addVertexWithUV(x + d13, y + d14, z + d13, texTorchUMin, texTorchVMax);
        tessellator.addVertexWithUV(x - d13, y + d14, z + d13, texTorchUMax, texTorchVMax);
        tessellator.addVertexWithUV(x - d13, y + d14, z - d13, texTorchUMax, texTorchVMin);
        tessellator.addVertexWithUV(x - d13, y + 1.0D, d11, textureUMin, textureVMax);
        tessellator.addVertexWithUV(x - d13, y, d11, textureUMin, textureVMin);
        tessellator.addVertexWithUV(x - d13, y, d12, textureUMax, textureVMin);
        tessellator.addVertexWithUV(x - d13, y + 1.0D, d12, textureUMax, textureVMax);
        tessellator.addVertexWithUV(x + d13, y + 1.0D, d12, textureUMin, textureVMax);
        tessellator.addVertexWithUV(x + d13, y, d12, textureUMin, textureVMin);
        tessellator.addVertexWithUV(x + d13, y, d11, textureUMax, textureVMin);
        tessellator.addVertexWithUV(x + d13, y + 1.0D, d11, textureUMax, textureVMax);
        tessellator.addVertexWithUV(d9, y + 1.0D, z + d13, textureUMin, textureVMax);
        tessellator.addVertexWithUV(d9, y, z + d13, textureUMin, textureVMin);
        tessellator.addVertexWithUV(d10, y, z + d13, textureUMax, textureVMin);
        tessellator.addVertexWithUV(d10, y + 1.0D, z + d13, textureUMax, textureVMax);
        tessellator.addVertexWithUV(d10, y + 1.0D, z - d13, textureUMin, textureVMax);
        tessellator.addVertexWithUV(d10, y, (z - d13), textureUMin, textureVMin);
        tessellator.addVertexWithUV(d9, y, (z - d13), textureUMax, textureVMin);
        tessellator.addVertexWithUV(d9, y + 1.0D, z - d13, textureUMax, textureVMax);
    }
}
