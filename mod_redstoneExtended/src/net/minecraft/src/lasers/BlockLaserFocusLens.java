package net.minecraft.src.lasers;

import net.minecraft.src.*;
import net.minecraft.src.util.*;

import java.util.Random;

public class BlockLaserFocusLens extends BlockContainerWithOverlay implements ILaserEmitter {
    public BlockLaserFocusLens(int id) {
        super(id, Block.dispenser.blockIndexInTexture + 17, Material.rock);
    }

    private final static int textureFrontDefault = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontDefault.png");
    private final static int textureFrontDeadly = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontDeadly.png");
    private final static int textureFrontBridge = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontBridge.png");
    private final static int textureSideLaserStrip = TextureManager.getInstance().getTerrainTexture("/laserEmitter/sideLaserStrip.png");
    private final static int textureSideLaserStripOverlay = TextureManager.getInstance().getTerrainTexture("/laserEmitter/sideLaserStripOverlay.png");
    private final static int textureBack = TextureManager.getInstance().getTerrainTexture("/laserReceiver/back.png");
    private final static int textureBackOverlay = TextureManager.getInstance().getTerrainTexture("/laserReceiver/backOverlay.png");

    private final static LaserShape[] operatingModes;

    static {
        operatingModes = new LaserShape[] {
                LaserShapes.Default,
                LaserShapes.Deadly,
                LaserShapes.Bridge
        };
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntityLaserFocusLens();
    }

    @Override
    public int getRenderType() {
        return mod_redstoneExtended.getInstance().renderStandardBlockWithOverlay;
    }

    @Override
    public boolean shouldOverlayBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return (layer == 1 && side == DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) &&
                getOperatingMode(iBlockAccess, x, y, z) != 2) ||
                (side != DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) &&
                        (layer == 1 || layer == 2));
    }

    @Override
    public boolean shouldOverlayBeRenderedInGUI(int side, int layer) {
        return (side == 3 && layer == 1) || (side != 3 && (layer == 1 || layer == 2));
    }

    @Override
    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        if (side == DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)))
            switch (getOperatingMode(iBlockAccess, x, y, z)) {
                case 0:
                    return textureFrontDefault;
                case 1:
                    return textureFrontDeadly;
                default:
                    return TextureManager.getInstance().emptyTexture;
            }
        else if (side == getOrientation(iBlockAccess, x, y, z))
            if (layer == 1)
                return textureBack;
            else
                return textureBackOverlay;
        else if (layer == 1)
            return textureSideLaserStrip;
        else
            return textureSideLaserStripOverlay;
    }

    @Override
    public int getBlockOverlayTextureInGUI(int side, int layer) {
        if (side == 2)
            return textureFrontDefault;
        else if (side == 3)
            if (layer == 1)
                return textureBack;
            else
                return textureBackOverlay;
        else if (layer == 1)
            return textureSideLaserStrip;
        else
            return textureSideLaserStripOverlay;
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
    public double getOverlayRotationInGUI(int side, int layer) {
        return side > 1 ? 0D : 90D;
    }

    @Override
    public ColorRGB getOverlayColorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return (side != DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) && layer == 1 &&
                getState(iBlockAccess, x, y, z)) ? getLaserMode(iBlockAccess, x, y, z).color : ColorRGB.Colors.Gray;
    }

    @Override
    public ColorRGB getOverlayColorMultiplierInGUI(int side, int layer) {
        return ColorRGB.Colors.Gray;
    }

    @Override
    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return (side != DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) && layer == 1 &&
                getState(iBlockAccess, x, y, z));
    }

    @Override
    public int tickRate() {
        return 1;
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
        int orientation = getOrientation(iBlockAccess, x, y, z);
        return (side == orientation && getOperatingMode(iBlockAccess, x, y, z) == 2) ? textureFrontBridge : blockIndexInTexture;
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        return blockIndexInTexture;
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
        return (getState(world, x, y, z) != updateInputState(world, x, y, z)) ||
                LaserUtil.isBlockUpdateForLaserInDirectionNecessary(world, x, y, z, getOrientation(world, x, y, z));
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (getState(world, x, y, z) != updateInputState(world, x, y, z)) {
            setState(world, x, y, z, !getState(world, x, y, z));
            world.markBlocksDirty(x, y, z, x, y, z);
        }

        LaserUtil.blockUpdateForLaserInDirection(world, x, y, z, getOrientation(world, x, y, z));
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        setOperatingMode(world, x, y, z, getOperatingMode(world, x, y, z) >= 2 ? 0 : (getOperatingMode(world, x, y, z) + 1));

        world.markBlocksDirty(x, y, z, x, y, z);
        world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
        world.notifyBlocksOfNeighborChange(x, y, z, blockID);

        return true;
    }

    boolean updateInputState(IBlockAccess iBlockAccess, int x, int y, int z) {
        int orientation = getOrientation(iBlockAccess, x, y, z);
        Position sourcePos = new Position(x, y, z).moveInDirection(DirectionUtil.invertDirection(orientation));
        int sourceBlockId = iBlockAccess.getBlockId(sourcePos.X, sourcePos.Y, sourcePos.Z);
        if ((Block.blocksList[sourceBlockId] instanceof ILaserEmitter) && (((ILaserEmitter)Block.blocksList[sourceBlockId]).isProvidingLaserPowerInDirection(iBlockAccess, sourcePos.X, sourcePos.Y, sourcePos.Z, orientation))) {
            setLaserMode(iBlockAccess, x, y, z, ((ILaserEmitter)Block.blocksList[sourceBlockId]).getLaserModeProvidedInDirection(iBlockAccess, sourcePos.X, sourcePos.Y, sourcePos.Z, orientation).getClone());
            getLaserMode(iBlockAccess, x, y, z).shape = operatingModes[getOperatingMode(iBlockAccess, x, y, z)].getClone();
            setDistance(iBlockAccess, x, y, z, ((ILaserEmitter)Block.blocksList[sourceBlockId]).getInitialDistanceProvidedInDirection(iBlockAccess, sourcePos.X, sourcePos.Y, sourcePos.Z, orientation));
            return true;
        }
        return false;
    }

    private static int getOperatingMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).operatingMode;
    }

    private static void setOperatingMode(IBlockAccess iBlockAccess, int x, int y, int z, int operatingMode) {
        ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).operatingMode = operatingMode;
    }

    private static boolean getStateFromMetadata(int metadata) {
        return ((metadata & 0x8) >> 3) == 1;
    }

    private static int getOrientationFromMetadata(int metadata) {
        return metadata & 0x7;
    }

    private static int setStateInMetadata(int metadata, boolean state) {
        return ((metadata & 0x7) | ((state ? 1 : 0) << 3) & 0x8);
    }

    private static int setOrientationInMetadata(int metadata, int orientation) {
        return ((metadata & 0x8) | (orientation & 0x7));
    }

    private static boolean getState(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getStateFromMetadata(metadata);
    }

    private static int getOrientation(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getOrientationFromMetadata(metadata);
    }

    private static void setState(World world, int x, int y, int z, boolean state) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setStateInMetadata(oldMetadata, state);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    private static void setOrientation(World world, int x, int y, int z, int orientation) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setOrientationInMetadata(oldMetadata, orientation);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    private static LaserMode getLaserMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode;
    }

    private static void setLaserMode(IBlockAccess iBlockAccess, int x, int y, int z, LaserMode laserMode) {
        ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).mode = laserMode;
    }

    private static int getDistance(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).distance;
    }

    private static void setDistance(IBlockAccess iBlockAccess, int x, int y, int z, int distance) {
        ((TileEntityLaserFocusLens)iBlockAccess.getBlockTileEntity(x, y, z)).distance = distance;
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

        return getLaserMode(iBlockAccess, x, y, z);
    }

    @Override
    public int getInitialDistanceProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        if (!isProvidingLaserPowerInDirection(iBlockAccess, x, y, z, direction))
            return 0;

        return (getDistance(iBlockAccess, x, y, z) + 1);
    }
}
