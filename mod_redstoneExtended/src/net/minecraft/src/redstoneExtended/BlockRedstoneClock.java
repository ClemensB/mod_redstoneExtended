package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;
import net.minecraft.src.redstoneExtended.Util.ColorRGB;
import net.minecraft.src.redstoneExtended.Util.TextureManager;

public class BlockRedstoneClock extends BlockContainerWithOverlay {
    private static final int texture = TextureManager.getInstance().getTerrainTexture("/clock/clock.png");

    public BlockRedstoneClock(int id) {
        super(id, Block.stairSingle.getBlockTextureFromSideAndMetadata(1, 0), Material.circuits);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntityRedstoneClock();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);

        world.notifyBlocksOfNeighborChange(x + 1, y, z, blockID);
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
        return getState(iBlockAccess, x, y, z) && ((direction >= 2) && (direction <= 5));
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
    public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
        return Block.stairSingle.getBlockTextureFromSideAndMetadata(side, 0);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        int oldDelaySetting = getDelaySetting(world, x, y, z);
        int newDelaySetting = (oldDelaySetting >= 5) ? 0 : oldDelaySetting + 1;
        setDelaySetting(world, x, y, z, newDelaySetting);
        ((TileEntityRedstoneClock)world.getBlockTileEntity(x, y, z)).delaySettingChanged();

        return true;
    }

    private static boolean getStateFromMetadata(int metadata) {
        return ((metadata & 0x8) >> 3) == 1;
    }

    private static int getDelaySettingFromMetadata(int metadata) {
        return metadata & 0x7;
    }

    private static int setStateInMetadata(int metadata, boolean state) {
        return ((metadata & 0x7) | ((state ? 1 : 0) << 3) & 0x8);
    }

    private static int setDelaySettingInMetadata(int metadata, int delaySetting) {
        return ((metadata & 0x8) | (delaySetting & 0x7));
    }

    private static boolean getState(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getStateFromMetadata(metadata);
    }

    public static int getDelaySetting(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getDelaySettingFromMetadata(metadata);
    }

    public static void setState(World world, int x, int y, int z, boolean state) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setStateInMetadata(oldMetadata, state);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    private static void setDelaySetting(World world, int x, int y, int z, int delaySetting) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setDelaySettingInMetadata(oldMetadata, delaySetting);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    @Override
    public boolean shouldOverlayBeRenderedInGUI(int side, int layer) {
        return side == 1 && layer == 1;
    }

    @Override
    public int getBlockOverlayTextureInGUI(int side, int layer) {
        return texture;
    }

    @Override
    public ColorRGB getOverlayColorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        float[] redstoneColor = getState(iBlockAccess, x, y, z) ? RenderBlocks.redstoneColors[13] :
                RenderBlocks.redstoneColors[0];
        return new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
    }

    @Override
    public ColorRGB getOverlayColorMultiplierInGUI(int side, int layer) {
        float[] redstoneColor = RenderBlocks.redstoneColors[13];
        return new ColorRGB(redstoneColor[0], redstoneColor[1], redstoneColor[2]);
    }

    @Override
    public boolean shouldOverlayIgnoreLighting(IBlockAccess iBlockAccess, int x, int y, int z, int side, int layer) {
        return getState(iBlockAccess, x, y, z);
    }
}
