package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.*;
import net.minecraft.src.redstoneExtended.IBlockWithOverlayEx;
import net.minecraft.src.redstoneExtended.Util.ColorRGB;
import net.minecraft.src.redstoneExtended.Util.DirectionUtil;
import net.minecraft.src.redstoneExtended.Util.Vector2d;
import net.minecraft.src.redstoneExtended.Util.Vector3d;

import java.util.Random;

public class BlockLaserEmitter extends BlockContainer implements ILaserEmitter, IBlockWithOverlayEx {
    public BlockLaserEmitter(int id) {
        super(id, Block.dispenser.blockIndexInTexture, Material.rock);
    }

    public final static int textureFront = ModLoader.addOverride("/terrain.png", "/redstoneExtended/laserEmitter/frontDefault.png");
    public final static int textureFrontInv = ModLoader.addOverride("/terrain.png", "/redstoneExtended/laserEmitter/frontInv.png");

    public final static net.minecraft.src.redstoneExtended.Laser.LaserMode[] operatingModes;

    static {
        operatingModes = new net.minecraft.src.redstoneExtended.Laser.LaserMode[] {
                new net.minecraft.src.redstoneExtended.Laser.LaserMode(LaserShapes.Default, new net.minecraft.src.redstoneExtended.Util.ColorRGB((byte)255, (byte)0, (byte)0)),
                new net.minecraft.src.redstoneExtended.Laser.LaserMode(LaserShapes.Default, new net.minecraft.src.redstoneExtended.Util.ColorRGB((byte)0, (byte)255, (byte)0)),
                new net.minecraft.src.redstoneExtended.Laser.LaserMode(LaserShapes.Default, new net.minecraft.src.redstoneExtended.Util.ColorRGB((byte)0, (byte)0, (byte)255))
        };
    }

    @Override
    public TileEntity getBlockEntity() {
        return new net.minecraft.src.redstoneExtended.Laser.TileEntityLaserEmitter();
    }

    @Override
    public int getRenderType() {
        return mod_redstoneExtended.getInstance().renderStandardBlockWithOverlay;
    }

    @Override
    public boolean shouldOverlayBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return layer == 1 && side == DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z));
    }

    @Override
    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return textureFront;
    }

    @Override
    public Vector3d getOverlayOffset(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return new Vector3d(0D);
    }

    @Override
    public Vector3d getOverlayScale(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return new Vector3d(1D);
    }

    @Override
    public double getOverlayRotation(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return 0D;
    }

    @Override
    public Vector2d getOverlayTextureOffset(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return new Vector2d(0D);
    }

    @Override
    public Vector2d getOverlayTextureScale(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return new Vector2d(1D);
    }

    @Override
    public ColorRGB getOverlayColorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return ColorRGB.Colors.White;
    }

    @Override
    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return false;
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
                return ((getOrientation(iBlockAccess, x, y, z) == side) ? blockIndexInTexture + 17 : blockIndexInTexture);
        }
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        switch (side) {
            case 0:
            case 1:
                return blockIndexInTexture + 17;
            case 3:
                return textureFrontInv;
            default:
                return blockIndexInTexture;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving creator) {
        setOrientation(world, x, y, z, DirectionUtil.getOrientationFromPlayer(creator));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
        if (isBlockUpdateNecessary(world, x, y, z))
            world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
    }

    private boolean isBlockUpdateNecessary(World world, int x, int y, int z) {
        return (getState(world, x, y, z) != isBeingPowered(world, x, y, z)) ||
                net.minecraft.src.redstoneExtended.Laser.LaserUtil.isBlockUpdateForLaserInDirectionNecessary(world, x, y, z, getOrientation(world, x, y, z));
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (getState(world, x, y, z) != isBeingPowered(world, x, y, z))
            setState(world, x, y, z, !getState(world, x, y, z));

        net.minecraft.src.redstoneExtended.Laser.LaserUtil.blockUpdateForLaserInDirection(world, x, y, z, getOrientation(world, x, y, z));
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer) {
        setOperatingMode(world, x, y, z, getOperatingMode(world, x, y, z) >= (operatingModes.length - 1) ? (byte)0 : (byte)(getOperatingMode(world, x, y, z) + 1));

        world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
        world.notifyBlocksOfNeighborChange(x, y, z, blockID);

        return true;
    }

    private boolean isBeingPowered(World world, int x, int y, int z) {
        return world.isBlockIndirectlyGettingPowered(x, y, z) ||
                world.isBlockIndirectlyGettingPowered(x, y + 1, z);
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

    public static byte getOperatingMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((net.minecraft.src.redstoneExtended.Laser.TileEntityLaserEmitter)iBlockAccess.getBlockTileEntity(x, y, z)).operatingMode;
    }

    public static void setOperatingMode(IBlockAccess iBlockAccess, int x, int y, int z, byte operatingMode) {
        ((net.minecraft.src.redstoneExtended.Laser.TileEntityLaserEmitter)iBlockAccess.getBlockTileEntity(x, y, z)).operatingMode = operatingMode;
    }

    @Override
    public boolean canProvideLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return (getOrientation(iBlockAccess, x, y, z) == direction);
    }

    @Override
    public boolean isProvidingLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return canProvideLaserPowerInDirection(iBlockAccess, x, y, z, direction) && getState(iBlockAccess, x, y, z);
    }

    @Override
    public net.minecraft.src.redstoneExtended.Laser.LaserMode getLaserModeProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return null;

        return operatingModes[getOperatingMode(iBlockAccess, x, y, z)].getClone();
    }

    @Override
    public short getInitialDistanceProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return 0;

        return 1;
    }
}
