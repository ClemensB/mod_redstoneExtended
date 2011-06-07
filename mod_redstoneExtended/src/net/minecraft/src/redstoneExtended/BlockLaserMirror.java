package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;

import java.util.LinkedList;
import java.util.Random;

public class BlockLaserMirror extends BlockContainer implements ILaserEmitter {
    public BlockLaserMirror(int id) {
        super(id, Block.blockSnow.blockIndexInTexture, Material.rock);
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntityLaserMirror();
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
        else
            return Block.blockSnow.blockIndexInTexture;
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        if (side == 3)
            return Block.dispenser.getBlockTextureFromSide(3);
        else
            return Block.blockSnow.blockIndexInTexture;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving) {
        setOrientation(world, x, y, z, Util.getOrientationFromPlayer(entityLiving));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
        if (isBlockUpdateNecessary(world, x, y, z))
            world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (getState(world, x, y, z) != updateInputState(world, x, y, z))
            setState(world, x, y, z, !getState(world, x, y, z));

        LaserUtil.blockUpdateForLaserInDirection(world, x, y, z, getOrientation(world, x, y, z));
    }

    @Override
    public boolean canProvideLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return (getOrientation(iBlockAccess, x, y, z) == direction);
    }

    @Override
    public boolean isProvidingLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return canProvideLaserPowerInDirection(iBlockAccess, x, y, z, direction) &&
                getState(iBlockAccess, x, y, z);
    }

    @Override
    public LaserMode getLaserModeProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return null;

        return getLaserMode(iBlockAccess, x, y, z);
    }

    @Override
    public short getInitialDistanceProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return 0;

        return (short)(getDistance(iBlockAccess, x, y, z) + 1);
    }

    private boolean isBlockUpdateNecessary(World world, int x, int y, int z) {
        return (getState(world, x, y, z) != updateInputState(world, x, y, z)) ||
                LaserUtil.isBlockUpdateForLaserInDirectionNecessary(world, x, y, z, getOrientation(world, x, y, z));
    }

    private boolean updateInputState(World world, int x, int y, int z) {
        int orientation = getOrientation(world, x, y, z);

        short distance = Short.MAX_VALUE;
        LinkedList<LaserMode> inputLaserModes = new LinkedList<LaserMode>();

        for (int i = 0; i < 6; i++) {
            if (i == orientation)
                continue;

            Position sourcePos = new Position(x, y, z).positionMoveInDirection(i);
            int sourceBlockId = world.getBlockId(sourcePos.X, sourcePos.Y, sourcePos.Z);
            if ((Block.blocksList[sourceBlockId] instanceof ILaserEmitter) &&
                    ((ILaserEmitter)Block.blocksList[sourceBlockId]).isProvidingLaserPowerInDirection(world, sourcePos.X, sourcePos.Y, sourcePos.Z, Util.invertDirection(i))) {
                inputLaserModes.add(((ILaserEmitter)Block.blocksList[sourceBlockId]).getLaserModeProvidedInDirection(world, sourcePos.X, sourcePos.Y, sourcePos.Z, Util.invertDirection(i)).getClone());

                short sourceDistance = ((ILaserEmitter)Block.blocksList[sourceBlockId]).getInitialDistanceProvidedInDirection(world, sourcePos.X, sourcePos.Y, sourcePos.Z, Util.invertDirection(i));
                if (sourceDistance < distance)
                    distance = sourceDistance;
            }
        }

        if (inputLaserModes.size() == 0)
            return false;

        int colorR = 0, colorG = 0, colorB = 0;

        LaserMode lastLaserMode = null;
        boolean areShapesEquals = true;
        for (LaserMode laserMode : inputLaserModes) {
            colorR += (laserMode.color.R & 0xff);
            colorG += (laserMode.color.G & 0xff);
            colorB += (laserMode.color.B & 0xff);

            if (lastLaserMode == null) {
                lastLaserMode = laserMode.getClone();
                continue;
            }

            if (!lastLaserMode.shape.equals(laserMode.shape))
                areShapesEquals = false;
            lastLaserMode = laserMode.getClone();
        }

        if (lastLaserMode == null)
            return false;

        colorR = MathUtil.clamp(0, 255, colorR);
        colorG = MathUtil.clamp(0, 255, colorG);
        colorB = MathUtil.clamp(0, 255, colorB);

        ColorRGB color = new ColorRGB((byte)colorR, (byte)colorG, (byte)colorB);
        LaserShape shape = areShapesEquals ? lastLaserMode.shape.getClone() : new LaserShape();

        LaserMode laserMode = new LaserMode(shape, color);
        setLaserMode(world, x, y, z, laserMode);
        setDistance(world, x, y, z, distance);

        return true;
    }

    public static boolean getStateFromMetadata(int metadata) {
        return ((metadata & 0x8) >> 3) == 1;
    }

    public static byte getOrientationFromMetadata(int metadata) {
        return (byte)(metadata & 0x7);
    }

    public static int setStateInMetadata(int metadata, boolean state) {
        return ((metadata & 0x7) | ((state ? 1 : 0) << 3) & 0x8);
    }

    public static int setOrientationInMetadata(int metadata, int orientation) {
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

    public static void setState(World world, int x, int y, int z, boolean state) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setStateInMetadata(oldMetadata, state);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    public static void setOrientation(World world, int x, int y, int z, int orientation) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setOrientationInMetadata(oldMetadata, orientation);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    public static LaserMode getLaserMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserMirror)iBlockAccess.getBlockTileEntity(x, y, z)).mode;
    }

    public static void setLaserMode(IBlockAccess iBlockAccess, int x, int y, int z, LaserMode laserMode) {
        ((TileEntityLaserMirror)iBlockAccess.getBlockTileEntity(x, y, z)).mode = laserMode;
    }

    public static short getDistance(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserMirror)iBlockAccess.getBlockTileEntity(x, y, z)).distance;
    }

    public static void setDistance(IBlockAccess iBlockAccess, int x, int y, int z, short distance) {
        ((TileEntityLaserMirror)iBlockAccess.getBlockTileEntity(x, y, z)).distance = distance;
    }
}
