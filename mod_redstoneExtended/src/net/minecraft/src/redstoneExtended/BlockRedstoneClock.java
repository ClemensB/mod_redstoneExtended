package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;

import java.util.Random;

public class BlockRedstoneClock extends BlockContainer {
    private static final int texture = ModLoader.addOverride("/terrain.png", "/redstoneExtended/clock/clock.png");

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
        return mod_redstoneExtended.getInstance().renderBlockRedstoneClock;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
        switch (side) {
            case 6:
                return texture;
            default:
                return Block.stairSingle.getBlockTextureFromSideAndMetadata(side, 0);
        }
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        byte oldDelaySetting = getDelaySetting(world, x, y, z);
        byte newDelaySetting = (byte) ((oldDelaySetting >= 5) ? 0 : oldDelaySetting + 1);
        setDelaySetting(world, x, y, z, newDelaySetting);
        ((TileEntityRedstoneClock) world.getBlockTileEntity(x, y, z)).delaySettingChanged();

        return true;
    }

    @Override
    public int idDropped(int i, Random random) {
        return mod_redstoneExtended.getInstance().itemRedstoneClock.shiftedIndex;
    }

    private static boolean getStateFromMetadata(int metadata) {
        return ((metadata & 0x8) >> 3) == 1;
    }

    private static byte getDelaySettingFromMetadata(int metadata) {
        return (byte) (metadata & 0x7);
    }

    private static int setStateInMetadata(int metadata, boolean state) {
        return ((metadata & 0x7) | ((state ? 1 : 0) << 3) & 0x8);
    }

    private static int setDelaySettingInMetadata(int metadata, byte delaySetting) {
        return ((metadata & 0x8) | ((int) delaySetting & 0x7));
    }

    public static boolean getState(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getStateFromMetadata(metadata);
    }

    public static byte getDelaySetting(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getDelaySettingFromMetadata(metadata);
    }

    public static void setState(World world, int x, int y, int z, boolean state) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setStateInMetadata(oldMetadata, state);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }

    private static void setDelaySetting(World world, int x, int y, int z, byte delaySetting) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setDelaySettingInMetadata(oldMetadata, delaySetting);
        world.setBlockMetadataWithNotify(x, y, z, newMetadata);
    }
}
