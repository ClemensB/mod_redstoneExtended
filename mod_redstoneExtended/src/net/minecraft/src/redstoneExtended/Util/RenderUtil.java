package net.minecraft.src.redstoneExtended.Util;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Tessellator;

public class RenderUtil {
    public static void renderOverlay(Block block, double x, double y, double z, int textureIndex, int layer, double offsetX, double offsetZ, double scaleX, double scaleZ, double rotation, double textureOffsetU, double textureOffsetV, double textureScaleU, double textureScaleV) {
        Tessellator tessellator = Tessellator.instance;
        int texturePositionX = (textureIndex & 0xF) << 4;
        int texturePositionY = textureIndex & 0xF0;
        double textureUMin = ((double)texturePositionX + block.minX * 16D) / 256D;
        double textureUMax = (((double)texturePositionX + block.maxX * 16D) - 0.01D) / 256D;
        double textureVMin = ((double)texturePositionY + block.minZ * 16D) / 256D;
        double textureVMax = (((double)texturePositionY + block.maxZ * 16D) - 0.01D) / 256D;
        if (block.minX < 0.0D || block.maxX > 1.0D) {
            textureUMin = ((float)texturePositionX + 0.0F) / 256F;
            textureUMax = ((float)texturePositionX + 15.99F) / 256F;
        }
        if (block.minZ < 0.0D || block.maxZ > 1.0D) {
            textureVMin = ((float)texturePositionY + 0.0F) / 256F;
            textureVMax = ((float)texturePositionY + 15.99F) / 256F;
        }

        textureUMin += textureOffsetU;
        textureUMax += textureOffsetU;
        textureVMin += textureOffsetV;
        textureVMax += textureOffsetV;
        textureUMax = textureUMin + ((textureUMax - textureUMin) * textureScaleU);
        textureVMax = textureVMin + ((textureVMax - textureVMin) * textureScaleV);

        double absolutePosXMin = x + block.minX + offsetX;
        double absolutePosXMax = x + (block.minX + ((block.maxX - block.minX) * scaleX)) + offsetX;
        double absolutePosY = y + block.maxY + 0.001D + (layer * 0.001D);
        double absolutePosZMin = z + block.minZ + offsetZ;
        double absolutePosZMax = z + (block.minZ + ((block.maxZ - block.minZ) * scaleZ)) + offsetZ;

        double rotationRad = Math.toRadians(rotation);

        double centerX = absolutePosXMin + ((absolutePosXMax - absolutePosXMin) / 2);
        double centerZ = absolutePosZMin + ((absolutePosZMax - absolutePosZMin) / 2);

        double quadRotatedTLX = centerX + Math.cos(rotationRad) * (absolutePosXMax - centerX) - Math.sin(rotationRad) * (absolutePosZMax - centerZ);
        double quadRotatedTLZ = centerZ + Math.sin(rotationRad) * (absolutePosXMax - centerX) + Math.cos(rotationRad) * (absolutePosZMax - centerZ);
        double quadRotatedTRX = centerX + Math.cos(rotationRad) * (absolutePosXMax - centerX) - Math.sin(rotationRad) * (absolutePosZMin - centerZ);
        double quadRotatedTRZ = centerZ + Math.sin(rotationRad) * (absolutePosXMax - centerX) + Math.cos(rotationRad) * (absolutePosZMin - centerZ);
        double quadRotatedBRX = centerX + Math.cos(rotationRad) * (absolutePosXMin - centerX) - Math.sin(rotationRad) * (absolutePosZMin - centerZ);
        double quadRotatedBRZ = centerZ + Math.sin(rotationRad) * (absolutePosXMin - centerX) + Math.cos(rotationRad) * (absolutePosZMin - centerZ);
        double quadRotatedBLX = centerX + Math.cos(rotationRad) * (absolutePosXMin - centerX) - Math.sin(rotationRad) * (absolutePosZMax - centerZ);
        double quadRotatedBLZ = centerZ + Math.sin(rotationRad) * (absolutePosXMin - centerX) + Math.cos(rotationRad) * (absolutePosZMax - centerZ);

        tessellator.addVertexWithUV(quadRotatedTLX, absolutePosY, quadRotatedTLZ, textureUMax, textureVMax);
        tessellator.addVertexWithUV(quadRotatedTRX, absolutePosY, quadRotatedTRZ, textureUMax, textureVMin);
        tessellator.addVertexWithUV(quadRotatedBRX, absolutePosY, quadRotatedBRZ, textureUMin, textureVMin);
        tessellator.addVertexWithUV(quadRotatedBLX, absolutePosY, quadRotatedBLZ, textureUMin, textureVMax);
    }

    public static void renderBlockOverlay(IBlockAccess iBlockAccess, Block block, int x, int y, int z, int textureIndex, int level, double xOffset, double zOffset, double xScale, double zScale, double rotation, boolean ignoreLighting, double textureOffsetU, double textureOffsetV, double textureScaleU, double textureScaleV) {
        int colorMultiplier = block.colorMultiplier(iBlockAccess, x, y, z);
        float colorMultiplierR = (float)(colorMultiplier >> 16 & 0xFF) / 255F;
        float colorMultiplierG = (float)(colorMultiplier >> 8 & 0xFF) / 255F;
        float colorMultiplierB = (float)(colorMultiplier & 0xFF) / 255F;

        renderBlockOverlay(iBlockAccess, block, x, y, z, textureIndex, level, xOffset, zOffset, xScale, zScale, rotation, colorMultiplierR, colorMultiplierG, colorMultiplierB, ignoreLighting, textureOffsetU, textureOffsetV, textureScaleU, textureScaleV);
    }

    public static void renderBlockOverlay(IBlockAccess iBlockAccess, Block block, int x, int y, int z, int textureIndex, int layer, double xOffset, double zOffset, double xScale, double zScale, double rotationDeg, float colorMultiplierR, float colorMultiplierG, float colorMultiplierB, boolean ignoreLighting, double textureOffsetU, double textureOffsetV, double textureScaleU, double textureScaleV) {
        Tessellator tessellator = Tessellator.instance;

        float blockBrightness = block.getBlockBrightness(iBlockAccess, x, y, z);
        float blockBrightnessAbove = block.getBlockBrightness(iBlockAccess, x, y + 1, z);
        if (block.maxY != 1.0D && !block.blockMaterial.getIsLiquid()) {
            blockBrightnessAbove = blockBrightness;
        }
        if (ignoreLighting)
            blockBrightnessAbove = 1F;
        tessellator.setColorOpaque_F(colorMultiplierR * blockBrightnessAbove, colorMultiplierG * blockBrightnessAbove, colorMultiplierB * blockBrightnessAbove);

        renderOverlay(block, x, y, z, textureIndex, layer, xOffset, zOffset, xScale, zScale, rotationDeg, textureOffsetU, textureOffsetV, textureScaleU, textureScaleV);
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
