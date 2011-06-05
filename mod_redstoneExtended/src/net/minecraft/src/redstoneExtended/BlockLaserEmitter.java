package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;

import java.util.Random;

public class BlockLaserEmitter extends Block implements ILaserEmitter {
    public BlockLaserEmitter(int id) {
        super(id, Block.dispenser.blockIndexInTexture, Material.rock);
    }

    @Override
    public int tickRate() {
        return 2;
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
        switch (side) {
            case 0:
            case 1:
                return blockIndexInTexture + 17;
            default:
                return blockIndexInTexture + ((getOrientation(iBlockAccess, x, y, z) == side) ? 1 : 0);
        }
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        switch (side) {
            case 0:
            case 1:
                return blockIndexInTexture + 17;
            case 3:
                return blockIndexInTexture + 1;
            default:
                return blockIndexInTexture;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving creator) {
        int orientation = MathHelper.floor_double((double)((creator.rotationYaw * 4F) / 360F) + 0.5D) & 0x3;
        switch (orientation) {
            case 0:
                orientation = 2;
                break;
            case 1:
                orientation = 5;
                break;
            case 2:
                orientation = 3;
                break;
            case 3:
                orientation = 4;
                break;
        }
        setOrientation(world, x, y, z, orientation);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
        if (isBlockUpdateNecessary(world, x, y, z))
            world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
    }

    private boolean isBlockUpdateNecessary(World world, int x, int y, int z) {
        if (getState(world, x, y, z) != isBeingPowered(world, x, y, z))
            return true;

        /*Position laserPos = new Position(x, y, z).positionMoveInDirection(getOrientation(world, x, y, z));
        int blockIdAtLaserPos = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);

        if (getState(world, x, y, z)) {
            if ((blockIdAtLaserPos == 0) || ((blockIdAtLaserPos != mod_redstoneExtended.getInstance().blockLaser.blockID) && (Block.blocksList[blockIdAtLaserPos].blockMaterial.func_27283_g())))
                return true;

            if ((blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    (BlockLaser.getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == getOrientation(world, x, y, z)) &&
                    ((!BlockLaser.getLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z).equals(getLaserModeProvidedInDirection(world, x, y, z, getOrientation(world, x, y, z)))) ||
                            (BlockLaser.getDistance(world, laserPos.X, laserPos.Y, laserPos.Z) != 1)))
                return true;
        } else if ((blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                (getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == getOrientation(world, x, y, z)))
            return true;

        return false;*/
        return LaserUtils.isBlockUpdateForLaserInDirectionNecessary(world, x, y, z, getOrientation(world, x, y, z));
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (getState(world, x, y, z) != isBeingPowered(world, x, y, z))
            setState(world, x, y, z, !getState(world, x, y, z));

        /*if (getState(world, x, y, z)) {
            tryToPlaceLaser(world, x, y, z);
        }*/

        /*Position laserPos = new Position(x, y, z).positionMoveInDirection(getOrientation(world, x, y, z));
        int blockIdAtLaserPos = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);
        boolean blockNeedsToBeUpdated = false;

        if (getState(world, x, y, z)) {
            if ((blockIdAtLaserPos != mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    ((blockIdAtLaserPos == 0) || (Block.blocksList[blockIdAtLaserPos].blockMaterial.func_27283_g()))) {
                world.setBlock(laserPos.X, laserPos.Y, laserPos.Z, mod_redstoneExtended.getInstance().blockLaser.blockID);

                blockNeedsToBeUpdated = true;
                blockIdAtLaserPos = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);

                BlockLaser.setOrientation(world, laserPos.X, laserPos.Y, laserPos.Z, getOrientation(world, x, y, z));
            }

            if ((blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    (BlockLaser.getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == getOrientation(world, x, y, z))) {
                if (BlockLaser.getDistance(world, laserPos.X, laserPos.Y, laserPos.Z) != 1) {
                    BlockLaser.setDistance(world, laserPos.X, laserPos.Y, laserPos.Z, (short)1);
                    blockNeedsToBeUpdated = true;
                }

                if (!BlockLaser.getLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z).equals(getLaserModeProvidedInDirection(world, x, y, z, getOrientation(world, x, y, z)))) {
                    BlockLaser.setLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z, getLaserModeProvidedInDirection(world, x, y, z, getOrientation(world, x, y, z)));
                    blockNeedsToBeUpdated = true;
                }
            }
        } else if (blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) {
            world.setBlock(laserPos.X, laserPos.Y, laserPos.Z, 0);

            blockNeedsToBeUpdated = true;
            blockIdAtLaserPos = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);
        }

        if (blockNeedsToBeUpdated) {
            world.markBlockAsNeedsUpdate(laserPos.X, laserPos.Y, laserPos.Z);
            world.notifyBlocksOfNeighborChange(laserPos.X, laserPos.Y, laserPos.Z, blockIdAtLaserPos);
        }*/

        LaserUtils.blockUpdateForLaserInDirection(world, x, y, z, getOrientation(world, x, y, z));
    }

    private boolean isBeingPowered(World world, int x, int y, int z) {
        return world.isBlockIndirectlyGettingPowered(x, y, z) ||
                world.isBlockIndirectlyGettingPowered(x, y + 1, z);
    }

    private static boolean getStateFromMetadata(int metadata) {
        return ((metadata & 0x8) >> 3) == 1;
    }

    private static byte getOrientationFromMetadata(int metadata) {
        return (byte)(metadata & 0x7);
    }

    private static int setStateInMetadata(int metadata, boolean state) {
        return ((metadata & 0x7) | ((state ? 1 : 0) << 3) & 0x8);
    }

    private static int setOrientationInMetadata(int metadata, int orientation) {
        return ((metadata & 0x8) | (orientation & 0x7));
    }

    public static boolean getState(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getStateFromMetadata(metadata);
    }

    public static byte getOrientation(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getOrientationFromMetadata(metadata);
    }

    protected static void setState(World world, int x, int y, int z, boolean state) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setStateInMetadata(oldMetadata, state);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    private static void setOrientation(World world, int x, int y, int z, int orientation) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setOrientationInMetadata(oldMetadata, orientation);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    @Override
    public boolean canProvideLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return (getOrientation(iBlockAccess, x, y, z) == direction);
    }

    @Override
    public boolean isProvidingLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return (getOrientation(iBlockAccess, x, y, z) == direction) && getState(iBlockAccess, x, y, z);
    }

    @Override
    public LaserMode getLaserModeProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return null;

        return new LaserMode(0.33f, false, (short)0, (byte)Block.blockSnow.blockIndexInTexture, (byte)255, (byte)0, (byte)0);
    }

    @Override
    public short getInitialDistanceProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return 0;

        return 1;
    }
}
