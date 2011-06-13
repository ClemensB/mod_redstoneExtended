package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.*;
import net.minecraft.src.redstoneExtended.IBlockWithOverlayEx;
import net.minecraft.src.redstoneExtended.Util.*;

import java.util.LinkedList;
import java.util.Random;

public class BlockLaserMirror extends BlockContainer implements ILaserEmitter, IBlockWithOverlayEx {
    public BlockLaserMirror(int id) {
        super(id, Block.dispenser.blockIndexInTexture + 17, Material.rock);
    }

    public final static int textureFrontDefault = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontDefault.png");
    public final static int textureFrontDeadly = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontDeadly.png");
    public final static int textureFrontBridge = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontBridge.png");
    public final static int textureBack = TextureManager.getInstance().getTerrainTexture("/laserReceiver/back.png");
    public final static int textureBackOverlay = TextureManager.getInstance().getTerrainTexture("/laserReceiver/backOverlay.png");

    public final static int textureFrontInv = TextureManager.getInstance().getTerrainTexture("/laserEmitter/frontInv.png");
    public final static int textureBackInv = TextureManager.getInstance().getTerrainTexture("/laserReceiver/backInv.png");

    @Override
    public TileEntity getBlockEntity() {
        return new net.minecraft.src.redstoneExtended.Laser.TileEntityLaserMirror();
    }

    @Override
    public int getRenderType() {
        return mod_redstoneExtended.getInstance().renderStandardBlockWithOverlay;
    }

    @Override
    public boolean shouldOverlayBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return (layer == 1 && side == DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z))) ||
                (side != DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) && (layer == 1 || layer == 2));
    }

    @Override
    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        if (side == DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z))) {
            LaserShape shape = getLaserMode(iBlockAccess, x, y, z).shape;

            if (!getState(iBlockAccess, x, y, z))
                return textureFrontDefault;
            if (shape.equals(LaserShapes.Deadly))
                return textureFrontDeadly;
            else if (shape.equals(LaserShapes.Bridge))
                return mod_redstoneExtended.getInstance().emptyTexture;
            else
                return textureFrontDefault;
        } else if (layer == 1)
            return textureBack;
        else
            return textureBackOverlay;
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
        return (side != DirectionUtil.invertDirection(getOrientation(iBlockAccess, x, y, z)) && layer == 1 &&
                getState(iBlockAccess, x, y, z)) ? getLaserMode(iBlockAccess, x, y, z).color : ColorRGB.Colors.Gray;
    }

    @Override
    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return false;
    }

    @Override
    public int tickRate() {
        return 1;
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
        int orientation = getOrientation(iBlockAccess, x, y, z);
        return (side == orientation && getLaserMode(iBlockAccess, x, y, z).shape.equals(LaserShapes.Bridge) &&
                getState(iBlockAccess, x, y, z)) ? textureFrontBridge : blockIndexInTexture;
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        if (side == 3)
            return textureFrontInv;
        else
            return textureBackInv;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving) {
        setOrientation(world, x, y, z, DirectionUtil.getOrientationFromPlayer(entityLiving));
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

        net.minecraft.src.redstoneExtended.Laser.LaserUtil.blockUpdateForLaserInDirection(world, x, y, z, getOrientation(world, x, y, z));
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
    public net.minecraft.src.redstoneExtended.Laser.LaserMode getLaserModeProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
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

    private boolean isBlockUpdateNecessary(World world, int x, int y, int z) {
        return (getState(world, x, y, z) != updateInputState(world, x, y, z)) ||
                net.minecraft.src.redstoneExtended.Laser.LaserUtil.isBlockUpdateForLaserInDirectionNecessary(world, x, y, z, getOrientation(world, x, y, z));
    }

    private boolean updateInputState(World world, int x, int y, int z) {
        int orientation = getOrientation(world, x, y, z);

        int distance = Short.MAX_VALUE;
        LinkedList<net.minecraft.src.redstoneExtended.Laser.LaserMode> inputLaserModes = new LinkedList<net.minecraft.src.redstoneExtended.Laser.LaserMode>();

        for (int i = 0; i < 6; i++) {
            if (i == orientation)
                continue;

            net.minecraft.src.redstoneExtended.Util.Position sourcePos = new net.minecraft.src.redstoneExtended.Util.Position(x, y, z).moveInDirection(i);
            int sourceBlockId = world.getBlockId(sourcePos.X, sourcePos.Y, sourcePos.Z);
            if ((Block.blocksList[sourceBlockId] instanceof net.minecraft.src.redstoneExtended.Laser.ILaserEmitter) &&
                    ((net.minecraft.src.redstoneExtended.Laser.ILaserEmitter)Block.blocksList[sourceBlockId]).isProvidingLaserPowerInDirection(world, sourcePos.X, sourcePos.Y, sourcePos.Z, DirectionUtil.invertDirection(i))) {
                inputLaserModes.add(((ILaserEmitter)Block.blocksList[sourceBlockId]).getLaserModeProvidedInDirection(world, sourcePos.X, sourcePos.Y, sourcePos.Z, DirectionUtil.invertDirection(i)).getClone());

                int sourceDistance = ((ILaserEmitter)Block.blocksList[sourceBlockId]).getInitialDistanceProvidedInDirection(world, sourcePos.X, sourcePos.Y, sourcePos.Z, DirectionUtil.invertDirection(i));
                if (sourceDistance < distance)
                    distance = sourceDistance;
            }
        }

        if (inputLaserModes.size() == 0)
            return false;

        int colorR = 0, colorG = 0, colorB = 0;

        net.minecraft.src.redstoneExtended.Laser.LaserMode lastLaserMode = null;
        boolean areShapesEquals = true;
        for (net.minecraft.src.redstoneExtended.Laser.LaserMode laserMode : inputLaserModes) {
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

        ColorRGB color = new ColorRGB(colorR, colorG, colorB);
        net.minecraft.src.redstoneExtended.Laser.LaserShape shape = areShapesEquals ? lastLaserMode.shape.getClone() : new net.minecraft.src.redstoneExtended.Laser.LaserShape();

        net.minecraft.src.redstoneExtended.Laser.LaserMode laserMode = new net.minecraft.src.redstoneExtended.Laser.LaserMode(shape, color);
        setLaserMode(world, x, y, z, laserMode);
        setDistance(world, x, y, z, distance);
        world.notifyBlocksOfNeighborChange(x, y, z, blockID);

        return true;
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

    public static net.minecraft.src.redstoneExtended.Laser.LaserMode getLaserMode(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserMirror)iBlockAccess.getBlockTileEntity(x, y, z)).mode;
    }

    public static void setLaserMode(IBlockAccess iBlockAccess, int x, int y, int z, net.minecraft.src.redstoneExtended.Laser.LaserMode laserMode) {
        ((TileEntityLaserMirror)iBlockAccess.getBlockTileEntity(x, y, z)).mode = laserMode;
    }

    public static int getDistance(IBlockAccess iBlockAccess, int x, int y, int z) {
        return ((TileEntityLaserMirror)iBlockAccess.getBlockTileEntity(x, y, z)).distance;
    }

    public static void setDistance(IBlockAccess iBlockAccess, int x, int y, int z, int distance) {
        ((TileEntityLaserMirror)iBlockAccess.getBlockTileEntity(x, y, z)).distance = distance;
    }
}
