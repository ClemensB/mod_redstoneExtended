package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;

import java.util.Random;

public class BlockRedstoneRSNORLatch extends BlockRedstoneFlipFlop {
    private static final int textureInputs = ModLoader.addOverride("/terrain.png", "/redstoneExtended/flipFlops/RSNORLatch/inputs.png");
    private static final int textureOutput = ModLoader.addOverride("/terrain.png", "/redstoneExtended/flipFlops/RSNORLatch/output.png");
    private static final int textureInvOutput = ModLoader.addOverride("/terrain.png", "/redstoneExtended/flipFlops/RSNORLatch/invOutput.png");

    public BlockRedstoneRSNORLatch(int id) {
        super(id);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int direction) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
            world.setBlockWithNotify(x, y, z, 0);
        }

        boolean inputSBeingPowered = isInputSBeingPowered(world, x, y, z);
        boolean inputRBeingPowered = isInputRBeingPowered(world, x, y, z);

        if (inputSBeingPowered ^ inputRBeingPowered) {
            boolean active = getState(world, x, y, z);

            if ((active && inputRBeingPowered) || (!active && inputSBeingPowered))
                world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        setState(world, x, y, z, !getState(world, x, y, z));
    }

    @Override
    public int idDropped(int i, Random random) {
        return mod_redstoneExtended.getInstance().itemRedstoneRSNORLatch.shiftedIndex;
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        boolean state = getState(iBlockAccess, x, y, z);
        int orientation = getOrientation(iBlockAccess, x, y, z);
        return (state && ((orientation == 0 && direction == 3) || (orientation == 1 && direction == 4) ||
                (orientation == 2 && direction == 2) || (orientation == 3 && direction == 5))) ||
                (!state && ((orientation == 0 && direction == 2) || (orientation == 1 && direction == 5) ||
                        (orientation == 2 && direction == 3) || (orientation == 3 && direction == 4)));
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        switch (side) {
            case 6:
                return textureInputs;
            case 7:
                return textureOutput;
            case 8:
                return textureInvOutput;
            default:
                return Block.stairSingle.getBlockTextureFromSideAndMetadata(side, 0);
        }
    }

    private boolean isInputRBeingPowered(World world, int x, int y, int z) {
        switch (getOrientation(world, x, y, z)) {
            case 0:
                return world.isBlockIndirectlyProvidingPowerTo(x - 1, y, z, 4);
            case 2:
                return world.isBlockIndirectlyProvidingPowerTo(x + 1, y, z, 5);
            case 3:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z + 1, 3);
            case 1:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z - 1, 2);
            default:
                return false;
        }
    }

    private boolean isInputSBeingPowered(World world, int x, int y, int z) {
        switch (getOrientation(world, x, y, z)) {
            case 0:
                return world.isBlockIndirectlyProvidingPowerTo(x + 1, y, z, 5);
            case 2:
                return world.isBlockIndirectlyProvidingPowerTo(x - 1, y, z, 4);
            case 3:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z - 1, 2);
            case 1:
                return world.isBlockIndirectlyProvidingPowerTo(x, y, z + 1, 3);
            default:
                return false;
        }
    }
}
