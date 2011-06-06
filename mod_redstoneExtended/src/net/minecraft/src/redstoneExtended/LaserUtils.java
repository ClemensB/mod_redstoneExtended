package net.minecraft.src.redstoneExtended;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.mod_redstoneExtended;

public class LaserUtils {
    public static boolean isBlockUpdateForLaserInDirectionNecessary(World world, int x, int y, int z, byte direction) {
        int blockId = world.getBlockId(x, y, z);
        Block block = Block.blocksList[blockId];
        ILaserEmitter laserEmitter = (ILaserEmitter)block;

        if (!laserEmitter.canProvideLaserPowerInDirection(world, x, y, z, direction))
            return false;

        Position laserPos = new Position(x, y, z).positionMoveInDirection(direction);
        int laserBlockId = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);

        if (laserEmitter.isProvidingLaserPowerInDirection(world, x, y, z, direction)) {
            if ((laserBlockId == 0) ||
                    ((Block.blocksList[laserBlockId].blockMaterial.func_27283_g()) &&
                            (Block.blocksList[laserBlockId].blockMaterial != Materials.laser)))
                return true;

            if ((laserBlockId == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    BlockLaser.getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == direction &&
                    (!laserEmitter.getLaserModeProvidedInDirection(world, x, y, z, direction).equals(BlockLaser.getLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z)) ||
                            (BlockLaser.getDistance(world, laserPos.X, laserPos.Y, laserPos.Z) != laserEmitter.getInitialDistanceProvidedInDirection(world, x, y, z, direction))))
                return true;
        } else if ((laserBlockId == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                (laserEmitter.canProvideLaserPowerInDirection(world, x, y, z, direction)))
            return true;

        return false;
    }

    public static void blockUpdateForLaserInDirection(World world, int x, int y, int z, byte direction) {
        int blockId = world.getBlockId(x, y, z);
        Block block = Block.blocksList[blockId];
        ILaserEmitter laserEmitter = (ILaserEmitter)block;

        if (!laserEmitter.canProvideLaserPowerInDirection(world, x, y, z, direction))
            return;

        Position laserPos = new Position(x, y, z).positionMoveInDirection(direction);
        int laserBlockId = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);
        boolean blockUpdateNecessary = false;

        if (laserEmitter.isProvidingLaserPowerInDirection(world, x, y, z, direction)) {
            if ((laserBlockId == 0) ||
                    ((Block.blocksList[laserBlockId].blockMaterial.func_27283_g()) &&
                            (Block.blocksList[laserBlockId].blockMaterial != Materials.laser))) {
                world.setBlock(laserPos.X, laserPos.Y, laserPos.Z, mod_redstoneExtended.getInstance().blockLaser.blockID);

                blockUpdateNecessary = true;
                laserBlockId = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);

                BlockLaser.setOrientation(world, laserPos.X, laserPos.Y, laserPos.Z, direction);
            }

            if ((laserBlockId == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    (BlockLaser.getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == direction)) {
                if (BlockLaser.getDistance(world, laserPos.X, laserPos.Y, laserPos.Z) != laserEmitter.getInitialDistanceProvidedInDirection(world, x, y, z, direction)) {
                    BlockLaser.setDistance(world, laserPos.X, laserPos.Y, laserPos.Z, laserEmitter.getInitialDistanceProvidedInDirection(world, x, y, z, direction));
                    blockUpdateNecessary = true;
                }

                if (!laserEmitter.getLaserModeProvidedInDirection(world, x, y, z, direction).equals(BlockLaser.getLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z))) {
                    BlockLaser.setLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z, laserEmitter.getLaserModeProvidedInDirection(world, x, y, z, direction).getClone());
                    blockUpdateNecessary = true;
                }
            } else if (laserBlockId == mod_redstoneExtended.getInstance().blockLaser.blockID) {
                world.setBlock(laserPos.X, laserPos.Y, laserPos.Z, 0);

                blockUpdateNecessary = true;
                laserBlockId = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);
            }

            if (blockUpdateNecessary) {
                world.markBlockAsNeedsUpdate(laserPos.X, laserPos.Y, laserPos.Z);
                world.notifyBlocksOfNeighborChange(laserPos.X, laserPos.Y, laserPos.Z, laserBlockId);
            }
        }
    }
}
