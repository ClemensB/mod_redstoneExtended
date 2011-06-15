package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;
import net.minecraft.src.redstoneExtended.Util.ColorRGB;
import net.minecraft.src.redstoneExtended.Util.TextureManager;
import net.minecraft.src.redstoneExtended.Util.Vector2d;
import net.minecraft.src.redstoneExtended.Util.Vector3d;

public class BlockRedstoneLightSensor extends BlockContainerWithOverlay {
    private final int textureSun = TextureManager.getInstance().getTerrainTexture("/lightSensor/sun.png");
    private final int textureRain = TextureManager.getInstance().getTerrainTexture("/lightSensor/rain.png");
    private final int textureMoon = TextureManager.getInstance().getTerrainTexture("/lightSensor/moon.png");
    private final int textureDarkness = TextureManager.getInstance().getTerrainTexture("/lightSensor/darkness.png");

    public BlockRedstoneLightSensor(int id) {
        super(id, Block.stairSingle.getBlockTextureFromSideAndMetadata(1, 0), Material.circuits);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntityLightSensor();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);

        world.notifyBlocksOfNeighborChange(x + 1, x, z, blockID);
        world.notifyBlocksOfNeighborChange(x - 1, y, z, blockID);
        world.notifyBlocksOfNeighborChange(x, y, z + 1, blockID);
        world.notifyBlocksOfNeighborChange(x, y, z - 1, blockID);
        world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, blockID);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
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
        }
    }

    @Override
    public boolean isIndirectlyPoweringTo(World world, int x, int y, int z, int direction) {
        return isPoweringTo(world, x, y, z, direction);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        return getState(iBlockAccess, x, y, z);
    }

    @Override
    public boolean canProvidePower() {
        return true;
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
    public int getBlockTextureFromSide(int side) {
        return Block.stairSingle.getBlockTextureFromSideAndMetadata(side, 0);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        int oldTriggerSetting = getTriggerSetting(world, x, y, z);
        int newTriggerSetting = (oldTriggerSetting >= 5) ? 0 : oldTriggerSetting + 1;
        setTriggerSetting(world, x, y, z, newTriggerSetting);
        world.markBlocksDirty(x, y, z, x, y, z);

        return true;
    }

    public static void update(World world, int x, int y, int z) {
        int triggerSetting = getTriggerSetting(world, x, y, z);
        int lightLevel = world.getBlockLightValue(x, y, z);
        boolean isPowered;
        switch (triggerSetting) {
            case 0:
                isPowered = (lightLevel == 15);
                break;
            case 1:
                isPowered = (lightLevel == 13);
                break;
            case 2:
                isPowered = (lightLevel == 12);
                break;
            case 3:
                isPowered = (lightLevel == 6);
                break;
            case 4:
                isPowered = (lightLevel == 4);
                break;
            case 5:
                isPowered = (lightLevel == 0);
                break;
            default:
                isPowered = false;
        }
        if (isPowered != getState(world, x, y, z)) {
            setState(world, x, y, z, isPowered);
            world.markBlocksDirty(x, y, z, x, y, z);
        }
    }

    private static boolean getStateFromMetadata(int metadata) {
        return ((metadata & 0x8) >> 3) == 1;
    }

    private static int getTriggerSettingFromMetadata(int metadata) {
        return metadata & 0x7;
    }

    private static int setStateInMetadata(int metadata, boolean state) {
        return ((metadata & 0x7) | ((state ? 1 : 0) << 3) & 0x8);
    }

    private static int setTriggerSettingInMetadata(int metadata, int triggerSetting) {
        return ((metadata & 0x8) | (triggerSetting & 0x7));
    }

    private static boolean getState(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getStateFromMetadata(metadata);
    }

    private static int getTriggerSetting(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getTriggerSettingFromMetadata(metadata);
    }

    private static void setState(World world, int x, int y, int z, boolean state) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setStateInMetadata(oldMetadata, state);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    private static void setTriggerSetting(World world, int x, int y, int z, int triggerSetting) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setTriggerSettingInMetadata(oldMetadata, triggerSetting);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    @Override
    public boolean shouldOverlayBeRenderedInGUI(int side, int layer) {
        return side == 1 && layer >= 1 && layer <= 6;
    }

    @Override
    public int getBlockOverlayTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        switch (layer) {
            case 1: {
                switch (getTriggerSetting(iBlockAccess, x, y, z)) {
                    case 0:
                        return textureSun;
                    case 1:
                        return Block.torchWood.blockIndexInTexture;
                    case 2:
                        return textureRain;
                    case 3:
                        return Block.torchRedstoneActive.blockIndexInTexture;
                    case 4:
                        return textureMoon;
                    case 5:
                        return textureDarkness;
                }
            }
            case 2:
            case 3:
                return Block.blockLapis.blockIndexInTexture;
            case 4:
            case 5:
                return Block.glass.blockIndexInTexture;
            case 6:
                return Block.redstoneWire.blockIndexInTexture;
            default:
                return TextureManager.getInstance().emptyTexture;
        }
    }

    @Override
    public int getBlockOverlayTextureInGUI(int side, int layer) {
        switch (layer) {
            case 1:
                return textureSun;
            case 2:
            case 3:
                return Block.blockLapis.blockIndexInTexture;
            case 4:
            case 5:
                return Block.glass.blockIndexInTexture;
            case 6:
                return Block.redstoneWire.blockIndexInTexture;
            default:
                return TextureManager.getInstance().emptyTexture;
        }
    }

    @Override
    public Vector3d getOverlayOffsetInGUI(int side, int layer) {
        switch (layer) {
            case 1:
                return new Vector3d(0.0625D * 1.5D, 0D, 0.0625D * 1.5D);
            case 2:
            case 4:
                return new Vector3d(0.5D, 0D, 0.125D);
            case 3:
            case 5:
                return new Vector3d(0.5D, 0D, 0.5D);
            case 6:
                return new Vector3d(0.0625D * 1.5D, 0.D, 0.5D);
            default:
                return new Vector3d(0D);
        }
    }

    @Override
    public Vector3d getOverlayScaleInGUI(int side, int layer) {
        return new Vector3d(0.5D * 0.75D, 1D, 0.5D * 0.75D);
    }

    @Override
    public Vector2d getOverlayTextureOffsetInGUI(int side, int layer) {
        return layer == 5 ? new Vector2d(0D, 1D / 256D) : new Vector2d(0D);
    }

    @Override
    public Vector2d getOverlayTextureScaleInGUI(int side, int layer) {
        return layer == 4 || layer == 5 ? new Vector2d(1D, 15D / 16D) : new Vector2d(1D);
    }

    @Override
    public ColorRGB getOverlayColorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        if (layer != 6)
            return ColorRGB.Colors.White;

        float[] redstoneColor = RenderBlocks.redstoneColors[getState(iBlockAccess, x, y, z) ? 13 : 0];
        return new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
    }

    @Override
    public ColorRGB getOverlayColorMultiplierInGUI(int side, int layer) {
        if (layer != 6)
            return ColorRGB.Colors.White;

        float[] redstoneColor = RenderBlocks.redstoneColors[13];
        return new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
    }

    @Override
    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return layer == 6 && getState(iBlockAccess, x, y, z);
    }
}
