package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.*;
import net.minecraft.src.redstoneExtended.IBlockWithOverlayEx;
import net.minecraft.src.redstoneExtended.Util.*;

import java.util.Random;

public class BlockLaserEmitter extends BlockContainer implements ILaserEmitter, IBlockWithOverlayEx {
    public BlockLaserEmitter(int id) {
        super(id, Block.dispenser.blockIndexInTexture + 17, Material.rock);
    }

    public final static int textureFront = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontDefault.png");
    public final static int textureSideLaserStrip = TextureManager.getInstance().getTerrainTexture("/laserEmitter/sideLaserStrip.png");
    public final static int textureSideLaserStripOverlay = TextureManager.getInstance().getTerrainTexture("/laserEmitter/sideLaserStripOverlay.png");

    public final static int textureFrontInv = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontInv.png");
    public final static int textureSideInv = TextureManager.getInstance().getTerrainTexture("/laserEmitter/sideInv.png");
    public final static int textureSideRotInv = TextureManager.getInstance().getTerrainTexture("/laserEmitter/sideRotInv.png");

    public final static LaserMode[] operatingModes;

    static {
        operatingModes = new LaserMode[] {
                new LaserMode(LaserShapes.Default, ColorRGB.Colors.Red),
                new LaserMode(LaserShapes.Default, ColorRGB.Colors.Green),
                new LaserMode(LaserShapes.Default, ColorRGB.Colors.Blue)
        };
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntityLaserEmitter();
    }

    @Override
    public int getRenderType() {
        return mod_redstoneExtended.getInstance().renderStandardBlockWithOverlay;
    }

    @Override
    public boolean shouldOverlayBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return (side == DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) && layer == 1) ||
                (side != getOrientation(iBlockAccess, x, y, z) && side != DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) &&
                        (layer == 1 || layer == 2));
    }

    @Override
    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        if (side == DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)))
            return textureFront;
        else if (layer == 1)
            return textureSideLaserStrip;
        else
            return textureSideLaserStripOverlay;
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
        if (side > 1)
            return 0D;

        int rotation = 0;
        int orientation = getOrientation(iBlockAccess, x, y, z);

        switch (orientation) {
            case 2:
                rotation = 2;
                break;
            case 3:
                rotation = 0;
                break;
            case 4:
                rotation = 1;
                break;
            case 5:
                rotation = 3;
                break;
        }

        return ((double)(rotation + 1) * 90D);
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
        return (side != DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) && layer == 1) ?
                operatingModes[getOperatingMode(iBlockAccess, x, y, z)].color : ColorRGB.Colors.Gray;
    }

    @Override
    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return (side != DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) && layer == 1);
    }

    @Override
    public int tickRate() {
        return 2;
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
        return blockIndexInTexture;
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        switch (side) {
            case 0:
            case 1:
                return textureSideRotInv;
            case 3:
                return textureFrontInv;
            case 2:
                return blockIndexInTexture;
            default:
                return textureSideInv;
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
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
                LaserUtil.isBlockUpdateForLaserInDirectionNecessary(world, x, y, z, getOrientation(world, x, y, z));
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (getState(world, x, y, z) != isBeingPowered(world, x, y, z))
            setState(world, x, y, z, !getState(world, x, y, z));

        LaserUtil.blockUpdateForLaserInDirection(world, x, y, z, getOrientation(world, x, y, z));
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer) {
        setOperatingMode(world, x, y, z, getOperatingMode(world, x, y, z) >= (operatingModes.length - 1) ? 0 : (getOperatingMode(world, x, y, z) + 1));

        world.markBlocksDirty(x, y, z, x, y, z);
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

    public static int getOrientationFromMetadata(int metadata) {
        return metadata & 0x7;
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

    public static int getOrientation(IBlockAccess iBlockAccess, int x, int y, int z) {
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

    public static int getOperatingMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserEmitter)iBlockAccess.getBlockTileEntity(x, y, z)).operatingMode;
    }

    public static void setOperatingMode(IBlockAccess iBlockAccess, int x, int y, int z, int operatingMode) {
        ((TileEntityLaserEmitter)iBlockAccess.getBlockTileEntity(x, y, z)).operatingMode = operatingMode;
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
    public LaserMode getLaserModeProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return null;

        return operatingModes[getOperatingMode(iBlockAccess, x, y, z)].getClone();
    }

    @Override
    public int getInitialDistanceProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return 0;

        return 1;
    }
}
