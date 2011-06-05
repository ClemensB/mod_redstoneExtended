package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;

import java.util.Random;

public class BlockLaserFocusLens extends BlockContainer implements ILaserEmitter {
    public BlockLaserFocusLens(int id) {
        super(id, Block.stone.blockIndexInTexture, Material.rock);
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntityLaserFocusLens();
    }

    @Override
    public int tickRate() {
        return 1;
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
        int orientation = getOrientation(iBlockAccess, x, y, z);
        if (side == orientation)
            return Block.dispenser.getBlockTextureFromSide(3);
        else if (side == Util.invertDirection(orientation))
            return Block.blockSnow.blockIndexInTexture;
        else
            return blockIndexInTexture;
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        switch (side) {
            case 3:
                return Block.dispenser.getBlockTextureFromSide(3);
            case 2:
                return Block.blockSnow.blockIndexInTexture;
            default:
                return blockIndexInTexture;
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
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
        if (isBlockUpdateNeeded(world, x, y, z))
            world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
    }

    private boolean isBlockUpdateNeeded(World world, int x, int y, int z) {
        if (getState(world, x, y, z) != isInputBeingLaserPowered(world, x, y, z))
            return true;

        Position laserPos = new Position(x, y, z).positionMoveInDirection(getOrientation(world, x, y, z));
        int blockIdAtLaserPos = world.getBlockId(laserPos.X, laserPos.Y, laserPos.Z);

        if (getState(world, x, y, z)) {
            if ((blockIdAtLaserPos == 0) || ((blockIdAtLaserPos != mod_redstoneExtended.getInstance().blockLaser.blockID) && (Block.blocksList[blockIdAtLaserPos].blockMaterial.func_27283_g())))
                return true;

            if ((blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                    (BlockLaser.getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == getOrientation(world, x, y, z)) &&
                    ((!BlockLaser.getLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z).equals(getLaserMode(world, x, y, z))) ||
                            (BlockLaser.getDistance(world, laserPos.X, laserPos.Y, laserPos.Z) != 1)))
                return true;
        } else if ((blockIdAtLaserPos == mod_redstoneExtended.getInstance().blockLaser.blockID) &&
                (getOrientation(world, laserPos.X, laserPos.Y, laserPos.Z) == getOrientation(world, x, y, z)))
            return true;

        return false;
    }

    private LaserMode getLaserMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode;
    }

    private void setLaserMode(World world, int x, int y, int z, LaserMode laserMode) {
        ((TileEntityLaserFocusLens)world.getBlockTileEntity(x, y, z)).mode = laserMode;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (getState(world, x, y, z) != isInputBeingLaserPowered(world, x, y, z))
            setState(world, x, y, z, !getState(world, x, y, z));

        Position laserPos = new Position(x, y, z).positionMoveInDirection(getOrientation(world, x, y, z));
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

                if (!BlockLaser.getLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z).equals(getLaserMode(world, x, y, z))) {
                    BlockLaser.setLaserMode(world, laserPos.X, laserPos.Y, laserPos.Z, getLaserMode(world, x, y, z));
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

    /*@Override
    public boolean isProvidingLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return getState(iBlockAccess, x, y, z);
    }

    @Override
    public LaserMode getProvidedLaserMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserFocusLens) iBlockAccess.getBlockTileEntity(x, y, z)).mode;
    }*/

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        ((TileEntityLaserFocusLens)world.getBlockTileEntity(x, y, z)).operatingMode =
                ((TileEntityLaserFocusLens)world.getBlockTileEntity(x, y, z)).operatingMode >= 2 ? 0 :
                        (byte)(((TileEntityLaserFocusLens)world.getBlockTileEntity(x, y, z)).operatingMode + 1);

        world.markBlockAsNeedsUpdate(x, y, z);
        world.notifyBlocksOfNeighborChange(x, y, z, blockID);

        return true;
    }

    protected boolean isInputBeingLaserPowered(IBlockAccess iBlockAccess, int x, int y, int z) {
        int orientation = getOrientation(iBlockAccess, x, y, z);
        Position sourcePos = new Position(x, y, z).positionMoveInDirection(Util.invertDirection(orientation));
        int sourceBlockId = iBlockAccess.getBlockId(sourcePos.X, sourcePos.Y, sourcePos.Z);
        if ((Block.blocksList[sourceBlockId] instanceof ILaserEmitter) && (((ILaserEmitter)Block.blocksList[sourceBlockId]).canProvideLaserPowerInDirection(iBlockAccess, sourcePos.X, sourcePos.Y, sourcePos.Z, orientation))) {
            ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode = (LaserMode)((ILaserEmitter)Block.blocksList[sourceBlockId]).getLaserModeProvidedInDirection(iBlockAccess, sourcePos.X, sourcePos.Y, sourcePos.Z, orientation).copy();
            switch (((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).operatingMode) {
                case 0:
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.width = 0.33f;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.collision = false;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.damage = 0;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.texture = (byte)Block.blockSnow.blockIndexInTexture;
                    break;
                case 1:
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.width = 0.166f;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.collision = false;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.damage = 4;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.texture = (byte)Block.blockSnow.blockIndexInTexture;
                    break;
                case 2:
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.width = 1.0f;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.collision = true;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.damage = 0;
                    ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode.texture = (byte)Block.glass.blockIndexInTexture;
                    break;
            }
            return true;
        }
        return false;
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
        return (getOrientation(iBlockAccess, x, y, z) == direction) && getState(iBlockAccess, x, y, z);
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

        return 1;
    }
}
