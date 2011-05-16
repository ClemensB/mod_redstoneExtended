package net.minecraft.src.redstoneExtended;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;

public class MyRenderBlocks {

    public static boolean renderBlockRedstoneLogicGate(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        renderBlocks.renderStandardBlock(block, x, y, z);

        boolean isActive = block.blockID == ((BlockRedstoneLogicGateBase) block).blockId(true);
        float[] redstoneColor = isActive ? RenderBlocks.redstoneColors[13] : RenderBlocks.redstoneColors[0];
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        double rotation = BlockRedstoneLogicGateBase.getOrientationFromMetadata(metadata) * 90D;
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSideAndMetadata(6, metadata), 0, 0D, 0D, 1D, 1D, rotation, redstoneColor[0], redstoneColor[1], redstoneColor[2], isActive, 0D, 0D, 1D, 1D);
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSideAndMetadata(7, metadata), 0, 0D, 0D, 1D, 1D, rotation, 1F, 1F, 1F, false, 0D, 0D, 1D, 1D);
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSideAndMetadata(8, metadata), 0, 0D, 0D, 1D, 1D, rotation, 1F, 1F, 1F, false, 0D, 0D, 1D, 1D);
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSideAndMetadata(9, metadata), 0, 0D, 0D, 1D, 1D, rotation, 1F, 1F, 1F, false, 0D, 0D, 1D, 1D);

        return true;
    }

    public static boolean renderBlockRedstoneClock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        renderBlocks.renderStandardBlock(block, x, y, z);

        boolean isActive = BlockRedstoneClock.getState(iBlockAccess, x, y, z);
        float[] redstoneColor = isActive ? RenderBlocks.redstoneColors[13] : RenderBlocks.redstoneColors[0];
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSideAndMetadata(6, metadata), 0, 0D, 0D, 1D, 1D, 0D, redstoneColor[0], redstoneColor[1], redstoneColor[2], isActive, 0D, 0D, 1D, 1D);

        return true;
    }

    public static boolean renderBlockRedstoneLightSensor(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        renderBlocks.renderStandardBlock(block, x, y, z);

        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSideAndMetadata(6, iBlockAccess.getBlockMetadata(x, y, z)), 0, 0.0625D * 1.5D, 0.0625D * 1.5D, 0.5D * 0.75D, 0.5D * 0.75D, 0D, false, 0D, 0D, 1D, 1D);
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, Block.blockLapis.getBlockTextureFromSide(0), 0, 0.5D, 0.125D, 0.5D * 0.75D, 0.5D * 0.75D, 0D, false, 0D, 0D, 1D, 1D);
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, Block.glass.getBlockTextureFromSide(0), 1, 0.5D, 0.125D, 0.5D * 0.75D, 0.5D * 0.75D, 0D, 1F, 1F, 1F, false, 0D, 0D, 1D, 15D / 16D);
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, Block.blockLapis.getBlockTextureFromSide(0), 0, 0.5D, 0.5D, 0.5D * 0.75D, 0.5D * 0.75D, 0D, false, 0D, 0D, 1D, 1D);
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, Block.glass.getBlockTextureFromSide(0), 1, 0.5D, 0.5D, 0.5D * 0.75D, 0.5D * 0.75D, 0D, 1F, 1F, 1F, false, 0D, 1D / 256D, 1D, 15D / 16D);
        boolean isActive = BlockRedstoneLightSensor.getState(iBlockAccess, x, y, z);
        float[] redstoneColor = isActive ? RenderBlocks.redstoneColors[13] : RenderBlocks.redstoneColors[0];
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, Block.redstoneWire.getBlockTextureFromSide(1), 0, 0.0625D * 1.5D, 0.5D, 0.5D * 0.75D, 0.5D * 0.75D, 0D, redstoneColor[0], redstoneColor[1], redstoneColor[2], isActive, 0D, 0D, 1D, 1D);

        return true;
    }

    public static boolean renderBlockRedstoneFlipFlop(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        renderBlocks.renderStandardBlock(block, x, y, z);

        double rotation = BlockRedstoneFlipFlop.getOrientation(iBlockAccess, x, y, z) * 90D;
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSide(6), 0, 0D, 0D, 1D, 1D, rotation, false, 0D, 0D, 1D, 1D);
        boolean isActive = BlockRedstoneFlipFlop.getState(iBlockAccess, x, y, z);
        float[] redstoneColor = isActive ? RenderBlocks.redstoneColors[13] : RenderBlocks.redstoneColors[0];
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSide(7), 0, 0D, 0D, 1D, 1D, rotation, redstoneColor[0], redstoneColor[1], redstoneColor[2], true, 0D, 0D, 1D, 1D);
        redstoneColor = isActive ? RenderBlocks.redstoneColors[0] : RenderBlocks.redstoneColors[13];
        RenderHelper.renderBlockOverlay(iBlockAccess, block, x, y, z, block.getBlockTextureFromSide(8), 0, 0D, 0D, 1D, 1D, rotation, redstoneColor[0], redstoneColor[1], redstoneColor[2], true, 0D, 0D, 1D, 1D);

        return true;
    }

    public static boolean renderBlockRedstoneLightBulb(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        int orientation = iBlockAccess.getBlockMetadata(x, y, z);
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
                renderBlocks.renderTorchAtAngle(block, (double) x - offsetSide, (double) y + offsetY, z, -angle, 0.0D);
                break;
            case 2:
                renderBlocks.renderTorchAtAngle(block, (double) x + offsetSide, (double) y + offsetY, z, angle, 0.0D);
                break;
            case 3:
                renderBlocks.renderTorchAtAngle(block, x, (double) y + offsetY, (double) z - offsetSide, 0.0D, -angle);
                break;
            case 4:
                renderBlocks.renderTorchAtAngle(block, x, (double) y + offsetY, (double) z + offsetSide, 0.0D, angle);
                break;
            case 5:
                renderBlocks.renderTorchAtAngle(block, x, y, z, 0.0D, 0.0D);
                break;
            case 6:
                RenderHelper.renderTorchOnCeiling(block, x, y + BlockRedstoneLightBulb.positionInCeilingY, z);
                break;
        }
        return true;
    }
}
