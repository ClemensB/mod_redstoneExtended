package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;

import java.util.Random;

public class BlockLaser extends BlockContainer implements ILaserEmitter {
    public BlockLaser(int id) {
        super(id, Block.glass.blockIndexInTexture, Materials.laser);
    }

    @Override
    public int idDropped(int metadata, Random random) {
        return -1;
    }

    @Override
    public int tickRate() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z) {
        byte orientation = ((TileEntityLaser)iBlockAccess.getBlockTileEntity(x, y, z)).orientation;
        float width = ((TileEntityLaser)iBlockAccess.getBlockTileEntity(x, y, z)).mode.width;
        float min = 0.5F - (width / 2F);
        float max = 0.5F + (width / 2F);
        switch (orientation) {
            case 0:
            case 1:
                setBlockBounds(min, 0.0f, min, max, 1.0f, max);
                break;
            case 2:
            case 3:
                setBlockBounds(min, min, 0.0f, max, max, 1.0f);
                break;
            case 4:
            case 5:
                setBlockBounds(0.0f, min, min, 1.0f, max, max);
                break;
        }
    }

    @Override
    public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z) {
        TileEntityLaser tileEntityLaser = (TileEntityLaser)iBlockAccess.getBlockTileEntity(x, y, z);
        return (tileEntityLaser.mode.colorR << 16) | (tileEntityLaser.mode.colorG << 8) | tileEntityLaser.mode.colorB;
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
        return ((TileEntityLaser)iBlockAccess.getBlockTileEntity(x, y, z)).mode.texture;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        if (((TileEntityLaser)world.getBlockTileEntity(x, y, z)).mode.collision)
            return super.getCollisionBoundingBoxFromPool(world, x, y, z);
        else
            return null;
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        int orientation = ((TileEntityLaser)world.getBlockTileEntity(x, y, z)).orientation;
        Position parentPos = new Position(x, y, z).positionMoveInDirection(Util.invertDirection(orientation));
        int parentBlockId = world.getBlockId(parentPos.X, parentPos.Y, parentPos.Z);
        //return ((Block.blocksList[parentBlockId] instanceof ILaserPowerProvider) && ((ILaserPowerProvider)Block.blocksList[parentBlockId]).isProvidingLaserPowerInDirection(world, parentPos.X, parentPos.Y, parentPos.Z, orientation));
        return ((Block.blocksList[parentBlockId] instanceof ILaserEmitter) && ((ILaserEmitter)Block.blocksList[parentBlockId]).canProvideLaserPowerInDirection(world, parentPos.X, parentPos.Y, parentPos.Z, orientation));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
        if (isBlockUpdateNecessary(world, x, y, z))
            world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
    }

    private boolean isBlockUpdateNecessary(World world, int x, int y, int z) {
        if (!canBlockStay(world, x, y, z))
            return true;

        Position parentPos = new Position(x, y, z).positionMoveInDirection(Util.invertDirection(getOrientation(world, x, y, z)));
        int parentBlockId = world.getBlockId(parentPos.X, parentPos.Y, parentPos.Z);
        if (!((ILaserEmitter)Block.blocksList[parentBlockId]).getLaserModeProvidedInDirection(world, parentPos.X, parentPos.Y, parentPos.Z, getOrientation(world, x, y, z)).equals(getLaserMode(world, x, y, z)))
            return true;

        Position laserPos = new Position(x, y, z).positionMoveInDirection(getOrientation(world, x, y, z));
        int blockIdAtLaserPos = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);
        if (hasNotMaximumLength(world, x, y, z)) {
            if ((blockIdAtLaserPos == 0) || ((blockIdAtLaserPos != mod_redstoneExtended.getInstance().blockLaser.blockID) && (Block.blocksList[blockIdAtLaserPos].blockMaterial.func_27283_g())))
                return true;

            if ((blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    (getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == getOrientation(world, x, y, z)) &&
                    ((!getLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z).equals(getLaserMode(world, x, y, z))) ||
                            (getDistance(world, laserPos.X, laserPos.Y, laserPos.Z) != (getDistance(world, x, y, z) + 1))))
                return true;
        } else if ((blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                (getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == getOrientation(world, x, y, z)))
            return true;

        return false;
    }

    private boolean hasNotMaximumLength(IBlockAccess iBlockAccess, int x, int y, int z) {
        return getDistance(iBlockAccess, x, y, z) < 30;
    }

    public static byte getOrientation(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaser)iBlockAccess.getBlockTileEntity(x, y, z)).orientation;
    }

    public static void setOrientation(World world, int x, int y, int z, byte orientation) {
        ((TileEntityLaser)world.getBlockTileEntity(x, y, z)).orientation = orientation;
    }

    public static short getDistance(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaser)iBlockAccess.getBlockTileEntity(x, y, z)).distance;
    }

    public static void setDistance(World world, int x, int y, int z, short distance) {
        ((TileEntityLaser)world.getBlockTileEntity(x, y, z)).distance = distance;
    }

    public static LaserMode getLaserMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaser)iBlockAccess.getBlockTileEntity(x, y, z)).mode;
    }

    public static void setLaserMode(World world, int x, int y, int z, LaserMode laserMode) {
        ((TileEntityLaser)world.getBlockTileEntity(x, y, z)).mode = laserMode;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);

        world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
    }

    @Override
    public void onBlockRemoval(World world, int x, int y, int z) {
        super.onBlockRemoval(world, x, y, z);

        world.notifyBlocksOfNeighborChange(x, y, z, blockID);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!canBlockStay(world, x, y, z)) {
            world.setBlockWithNotify(x, y, z, 0);
            return;
        }

        Position parentPos = new Position(x, y, z).positionMoveInDirection(Util.invertDirection(getOrientation(world, x, y, z)));
        int parentBlockId = world.getBlockId(parentPos.X, parentPos.Y, parentPos.Z);
        if (!((ILaserEmitter)Block.blocksList[parentBlockId]).getLaserModeProvidedInDirection(world, parentPos.X, parentPos.Y, parentPos.Z, getOrientation(world, x, y, z)).equals(getLaserMode(world, x, y, z))) {
            setLaserMode(world, x, y, z, ((ILaserEmitter)Block.blocksList[parentBlockId]).getLaserModeProvidedInDirection(world, parentPos.X, parentPos.Y, parentPos.Z, getOrientation(world, x, y, z)));
        }

        Position laserPos = new Position(x, y, z).positionMoveInDirection(getOrientation(world, x, y, z));
        int blockIdAtLaserPos = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);
        boolean blockNeedsToBeUpdated = false;

        if (hasNotMaximumLength(world, x, y, z)) {
            if ((blockIdAtLaserPos != mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    ((blockIdAtLaserPos == 0) || (Block.blocksList[blockIdAtLaserPos].blockMaterial.func_27283_g()))) {
                world.setBlock(laserPos.X, laserPos.Y, laserPos.Z, mod_redstoneExtended.getInstance().blockLaser.blockID);

                blockNeedsToBeUpdated = true;
                blockIdAtLaserPos = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);

                setOrientation(world, laserPos.X, laserPos.Y, laserPos.Z, getOrientation(world, x, y, z));
            }

            if ((blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    (getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == getOrientation(world, x, y, z))) {
                if (getDistance(world, laserPos.X, laserPos.Y, laserPos.Z) != getDistance(world, x, y, z) + 1) {
                    setDistance(world, laserPos.X, laserPos.Y, laserPos.Z, (short)(getDistance(world, x, y, z) + 1));
                    blockNeedsToBeUpdated = true;
                }

                if (!getLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z).equals(getLaserMode(world, x, y, z))) {
                    setLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z, getLaserMode(world, x, y, z));
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
        }
    }

    @Override
    public float getBlockBrightness(IBlockAccess iBlockAccess, int x, int y, int z) {
        return 1f;
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntityLaser();
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        short damage = ((TileEntityLaser)world.getBlockTileEntity(x, y, z)).mode.damage;
        if (damage > 0)
            entity.attackEntityFrom(null, damage);
    }

    @Override
    public boolean canProvideLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return (getOrientation(iBlockAccess, x, y, z) == direction) && hasNotMaximumLength(iBlockAccess, x, y, z);
    }

    @Override
    public LaserMode getLaserModeProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!canProvideLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return null;

        return getLaserMode(iBlockAccess, x, y, z);
    }

    @Override
    public int getInitialDistanceProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!canProvideLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return 0;

        return getDistance(iBlockAccess, x, y, z) + 1;
    }
}
