package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;
import net.minecraft.src.redstoneExtended.Util.ColorRGB;
import net.minecraft.src.redstoneExtended.Util.LoggingUtil;
import net.minecraft.src.redstoneExtended.Util.TextureManager;

import java.util.Random;

public abstract class BlockRedstoneLogicGate extends BlockWithOverlay {
    private final boolean active;

    BlockRedstoneLogicGate(int id, boolean isActive) {
        super(id, Block.stairSingle.getBlockTextureFromSideAndMetadata(1, 0), Material.circuits);
        active = isActive;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);

        if ((operatingModeCount() < 1) || (operatingModeCount() > 4))
            LoggingUtil.logError("Operating mode count must in range of 1 and 4!");
    }


    protected abstract int operatingModeTexture(int operatingMode, int part, boolean isActive);

    protected abstract int operatingModeDelay(int operatingMode);

    protected abstract boolean operatingModeCondition(int operatingMode, boolean inputA, boolean inputB, boolean inputC);

    protected abstract int operatingModeCount();

    protected abstract int blockId(boolean active);

    private static int getOrientationFromMetadata(int metadata) {
        return metadata & 0x3;
    }

    private static int getOperatingModeFromMetadata(int metadata) {
        return (metadata & 0xC) >> 2;
    }

    private static int setOrientationInMetadata(int metadata, int orientation) {
        return ((metadata & 0xC) | (orientation & 0x3));
    }

    private static int setOperatingModeInMetadata(int metadata, int operatingMode) {
        return ((metadata & 0x3) | ((operatingMode << 2) & 0xC));
    }

    private static int getOrientation(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getOrientationFromMetadata(metadata);
    }

    private static int getOperatingMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getOperatingModeFromMetadata(metadata);
    }

    private static void setOrientation(World world, int x, int y, int z, int orientation) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setOrientationInMetadata(oldMetadata, orientation);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    private static void setOperatingMode(World world, int x, int y, int z, int operatingMode) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setOperatingModeInMetadata(oldMetadata, operatingMode);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    private boolean isBeingPowered(World world, int x, int y, int z) {
        int operatingMode = getOperatingMode(world, x, y, z);

        boolean inputA = isInputABeingPowered(world, x, y, z);
        boolean inputB = isInputBBeingPowered(world, x, y, z);
        boolean inputC = isInputCBeingPowered(world, x, y, z);

        return operatingModeCondition(operatingMode, inputA, inputB, inputC);
    }

    private boolean isInputABeingPowered(World world, int x, int y, int z) {
        switch (getOrientation(world, x, y, z)) {
            case 0:
                return world.isBlockIndirectlyProvidingPowerTo(x - 1, y, z, 4);
            case 2:
                return world.isBlockIndirectlyProvidingPowerTo(x + 1, y, z, 5);
            case 3:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z + 1, 3);
            case 1:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z - 1, 2);
            default:
                return false;
        }
    }

    private boolean isInputBBeingPowered(World world, int x, int y, int z) {
        switch (getOrientation(world, x, y, z)) {
            case 0:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z + 1, 3);
            case 2:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z - 1, 2);
            case 3:
                return world.isBlockIndirectlyProvidingPowerTo(x + 1, y, z, 5);
            case 1:
                return world.isBlockIndirectlyProvidingPowerTo(x - 1, y, z, 4);
            default:
                return false;
        }
    }

    private boolean isInputCBeingPowered(World world, int x, int y, int z) {
        switch (getOrientation(world, x, y, z)) {
            case 0:
                return world.isBlockIndirectlyProvidingPowerTo(x + 1, y, z, 5);
            case 2:
                return world.isBlockIndirectlyProvidingPowerTo(x - 1, y, z, 4);
            case 3:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z - 1, 2);
            case 1:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z + 1, 3);
            default:
                return false;
        }
    }

    private int getOperatingModeCount() {
        return (operatingModeCount() < 1) ? 1 : ((operatingModeCount() > 4) ? 4 : operatingModeCount());
    }


    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int idDropped(int metadata, Random random) {
        return blockId(false);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.isBlockOpaqueCube(x, y - 1, z) &&
                super.canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.isBlockOpaqueCube(x, y - 1, z) &&
                super.canBlockStay(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int direction) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
            world.setBlockWithNotify(x, y, z, 0);
            return;
        }

        boolean powered = isBeingPowered(world, x, y, z);
        int operatingMode = getOperatingMode(world, x, y, z);

        if ((active && !powered) || (!active && powered))
            world.scheduleBlockUpdate(x, y, z, blockID, operatingModeDelay(operatingMode));
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        world.notifyBlocksOfNeighborChange(x + 1, y, z, blockID);
        world.notifyBlocksOfNeighborChange(x - 1, y, z, blockID);
        world.notifyBlocksOfNeighborChange(x, y, z + 1, blockID);
        world.notifyBlocksOfNeighborChange(x, y, z - 1, blockID);
        world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, blockID);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        int metadata = world.getBlockMetadata(x, y, z);
        boolean powered = isBeingPowered(world, x, y, z);

        if (active && !powered) {
            world.setBlockAndMetadataWithNotify(x, y, z, blockId(false), metadata);
        } else if (!active) {
            world.setBlockAndMetadataWithNotify(x, y, z, blockId(true), metadata);
            if (!powered) {
                world.scheduleBlockUpdate(x, y, z, blockId(true), operatingModeDelay(getOperatingModeFromMetadata(metadata)));
            }
        }
    }


    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer activator) {
        int oldOperatingMode = getOperatingMode(world, x, y, z);
        int newOperatingMode = (oldOperatingMode == (getOperatingModeCount() - 1)) ? 0 : oldOperatingMode + 1;
        setOperatingMode(world, x, y, z, newOperatingMode);
        world.markBlocksDirty(x, y, z, x, y, z);

        boolean powered = isBeingPowered(world, x, y, z);

        if ((active && !powered) || (!active && powered))
            world.scheduleBlockUpdate(x, y, z, blockID, operatingModeDelay(newOperatingMode));

        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving creator) {
        int orientation = ((MathHelper.floor_double((double)((creator.rotationYaw * 4F) / 360F) + 0.5D) & 0x3) + 2) % 4;
        setOrientation(world, x, y, z, orientation);

        boolean powered = isBeingPowered(world, x, y, z);
        int operatingMode = getOperatingMode(world, x, y, z);

        if (powered)
            world.scheduleBlockUpdate(x, y, z, blockID, operatingModeDelay(operatingMode));
    }


    @Override
    public boolean isIndirectlyPoweringTo(World world, int x, int y, int z, int direction) {
        return isPoweringTo(world, x, y, z, direction);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!active) return false;

        int orientation = getOrientation(iBlockAccess, x, y, z);
        return (orientation == 0 && direction == 3) || (orientation == 1 && direction == 4) ||
                (orientation == 2 && direction == 2) || (orientation == 3 && direction == 5);
    }

    @Override
    public boolean canProvidePower() {
        return false;
    }


    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return mod_redstoneExtended.getInstance().renderStandardBlockWithOverlay;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
        return Block.stairSingle.getBlockTextureFromSideAndMetadata(side, 0);
    }

    @Override
    public boolean shouldOverlayBeRenderedInGUI(int side, int layer) {
        return (side == 1 && layer >= 1 && layer <= 4);
    }

    @Override
    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        int operatingMode = getOperatingMode(iBlockAccess, x, y, z);
        switch (layer) {
            case 1:
                return operatingModeTexture(operatingMode, 0, active);
            case 2:
                return operatingModeTexture(operatingMode, 1, active);
            case 3:
                return operatingModeTexture(operatingMode, 2, active);
            case 4:
                return operatingModeTexture(operatingMode, 3, active);
            default:
                return TextureManager.getInstance().emptyTexture;
        }
    }

    @Override
    public int getBlockOverlayTextureInGUI(int side, int layer) {
        return operatingModeTexture(0, layer - 1, true);
    }

    @Override
    public double getOverlayRotation(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getOrientation(iBlockAccess, x, y, z) * 90D;
    }

    @Override
    public double getOverlayRotationInGUI(int side, int layer) {
        return 0D;
    }

    @Override
    public ColorRGB getOverlayColorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        if (layer != 1)
            return ColorRGB.Colors.White;

        float[] redstoneColor = active ? RenderBlocks.redstoneColors[13] : RenderBlocks.redstoneColors[0];
        return new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
    }

    @Override
    public ColorRGB getOverlayColorMultiplierInGUI(int side, int layer) {
        if (layer != 1)
            return ColorRGB.Colors.White;

        float[] redstoneColor = RenderBlocks.redstoneColors[0];
        return new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
    }

    @Override
    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return (layer == 1 && active);
    }
}
