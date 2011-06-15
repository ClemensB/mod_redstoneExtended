package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;
import net.minecraft.src.redstoneExtended.Util.TextureManager;

import java.util.Random;

public class BlockRedstoneLightBulb extends Block {
    private final boolean active;

    private static final int textureOff = TextureManager.getInstance().getTerrainTexture("/lightBulb/off.png");
    private static final int textureOn = TextureManager.getInstance().getTerrainTexture("/lightBulb/on.png");

    static final double positionInCeilingY = 0.3D;

    public BlockRedstoneLightBulb(int id, boolean isActive) {
        super(id, isActive ? textureOn : textureOff, Material.circuits);
        active = !isActive;
        setTickOnLoad(true);
    }

    @Override
    public int idDropped(int i, Random random) {
        return mod_redstoneExtended.getInstance().blockRedstoneLightBulbOff.blockID;
    }

    @Override
    public int tickRate() {
        return 2;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return mod_redstoneExtended.getInstance().renderBlockTorchExtended;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
        switch (side) {
            case 1:
                return Block.redstoneWire.getBlockTextureFromSideAndMetadata(side, metadata);
            default:
                return super.getBlockTextureFromSideAndMetadata(side, metadata);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return (world.isBlockOpaqueCube(x - 1, y, z) || world.isBlockOpaqueCube(x + 1, y, z) ||
                world.isBlockOpaqueCube(x, y, z - 1) || world.isBlockOpaqueCube(x, y, z + 1) ||
                world.isBlockOpaqueCube(x, y - 1, z) || world.isBlockOpaqueCube(x, y + 1, z));
    }

    @Override
    public void onBlockPlaced(World world, int x, int y, int z, int direction) {
        int orientation = world.getBlockMetadata(x, y, z);
        if (direction == 1 && world.isBlockOpaqueCube(x, y - 1, z))
            orientation = 5;
        if (direction == 2 && world.isBlockOpaqueCube(x, y, z + 1))
            orientation = 4;
        if (direction == 3 && world.isBlockOpaqueCube(x, y, z - 1))
            orientation = 3;
        if (direction == 4 && world.isBlockOpaqueCube(x + 1, y, z))
            orientation = 2;
        if (direction == 5 && world.isBlockOpaqueCube(x - 1, y, z))
            orientation = 1;
        if (direction == 0 && world.isBlockOpaqueCube(x, y + 1, z))
            orientation = 6;
        world.setBlockMetadataWithNotify(x, y, z, orientation);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.getBlockMetadata(x, y, z) == 0) {
            if (world.isBlockOpaqueCube(x - 1, y, z))
                world.setBlockMetadataWithNotify(x, y, z, 1);
            else if (world.isBlockOpaqueCube(x + 1, y, z))
                world.setBlockMetadataWithNotify(x, y, z, 2);
            else if (world.isBlockOpaqueCube(x, y, z - 1))
                world.setBlockMetadataWithNotify(x, y, z, 3);
            else if (world.isBlockOpaqueCube(x, y, z + 1))
                world.setBlockMetadataWithNotify(x, y, z, 4);
            else if (world.isBlockOpaqueCube(x, y - 1, z))
                world.setBlockMetadataWithNotify(x, y, z, 5);
            else if (world.isBlockOpaqueCube(x, y + 1, z))
                world.setBlockMetadataWithNotify(x, y, z, 6);
            dropTorchIfCannotStay(world, x, y, z);
        }
        if (active) {
            world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
            world.notifyBlocksOfNeighborChange(x, y + 1, z, blockID);
            world.notifyBlocksOfNeighborChange(x - 1, y, z, blockID);
            world.notifyBlocksOfNeighborChange(x + 1, y, z, blockID);
            world.notifyBlocksOfNeighborChange(x, y, z - 1, blockID);
            world.notifyBlocksOfNeighborChange(x, y, z + 1, blockID);
        }
    }

    @Override
    public void onBlockRemoval(World world, int x, int y, int z) {
        if (active) {
            world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
            world.notifyBlocksOfNeighborChange(x, y + 1, z, blockID);
            world.notifyBlocksOfNeighborChange(x - 1, y, z, blockID);
            world.notifyBlocksOfNeighborChange(x + 1, y, z, blockID);
            world.notifyBlocksOfNeighborChange(x, y, z - 1, blockID);
            world.notifyBlocksOfNeighborChange(x, y, z + 1, blockID);
        }
    }

    private boolean dropTorchIfCannotStay(World world, int x, int y, int z) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
            world.setBlockWithNotify(x, y, z, 0);
            return false;
        } else
            return true;
    }

    private boolean isBeingPowered(World world, int x, int y, int z) {
        int orientation = world.getBlockMetadata(x, y, z);
        return (orientation == 5 && world.isBlockIndirectlyProvidingPowerTo(x, y - 1, z, 0)) ||
                (orientation == 3 && world.isBlockIndirectlyProvidingPowerTo(x, y, z - 1, 2)) ||
                (orientation == 4 && world.isBlockIndirectlyProvidingPowerTo(x, y, z + 1, 3)) ||
                (orientation == 1 && world.isBlockIndirectlyProvidingPowerTo(x - 1, y, z, 4)) ||
                (orientation == 2 && world.isBlockIndirectlyProvidingPowerTo(x + 1, y, z, 5)) ||
                (orientation == 6 && world.isBlockIndirectlyProvidingPowerTo(x, y + 1, z, 1));
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        boolean powered = isBeingPowered(world, x, y, z);
        if (active) {
            if (powered) {
                world.setBlockAndMetadataWithNotify(x, y, z, mod_redstoneExtended.getInstance().blockRedstoneLightBulbOn.blockID, world.getBlockMetadata(x, y, z));
            }
        } else if (!powered)
            world.setBlockAndMetadataWithNotify(x, y, z, mod_redstoneExtended.getInstance().blockRedstoneLightBulbOff.blockID, world.getBlockMetadata(x, y, z));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int direction) {
        if (dropTorchIfCannotStay(world, x, y, z)) {
            int orientation = world.getBlockMetadata(x, y, z);
            if ((!world.isBlockOpaqueCube(x - 1, y, z) && orientation == 1) ||
                    (!world.isBlockOpaqueCube(x + 1, y, z) && orientation == 2) ||
                    (!world.isBlockOpaqueCube(x, y, z - 1) && orientation == 3) ||
                    (!world.isBlockOpaqueCube(x, y, z + 1) && orientation == 4) ||
                    (!world.isBlockOpaqueCube(x, y - 1, z) && orientation == 5) ||
                    (!world.isBlockOpaqueCube(x, y + 1, z) && orientation == 6)) {
                dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
                world.setBlockWithNotify(x, y, z, 0);
            }

        }
        world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3D vec3D, Vec3D vec3D1) {
        int orientation = world.getBlockMetadata(x, y, z);
        float f = 0.15F;
        float f1 = 0.1F;
        switch (orientation) {
            case 1:
                setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
                break;
            case 2:
                setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
                break;
            case 3:
                setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
                break;
            case 4:
                setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
                break;
            case 5:
                setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
                break;
            case 6:
                setBlockBounds(0.5F - f1, 0.4F + (float)positionInCeilingY, 0.5F - f1, 0.5F + f1, 1.0F + (float)positionInCeilingY, 0.5F + f1);
                break;
        }
        return super.collisionRayTrace(world, x, y, z, vec3D, vec3D1);
    }

}
