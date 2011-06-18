package net.minecraft.src.redstoneExtended;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;
import net.minecraft.src.util.TextureManager;

public class BlockRedstoneJKFlipFlop extends BlockRedstoneFlipFlop {
    private static final int textureInputs = TextureManager.getInstance().getTerrainTexture("/flipFlops/JKFlipFlop/inputs.png");
    private static final int textureOutput = TextureManager.getInstance().getTerrainTexture("/flipFlops/JKFlipFlop/output.png");

    public BlockRedstoneJKFlipFlop(int id) {
        super(id);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int direction) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
            world.setBlockWithNotify(x, y, z, 0);
        }

        boolean inputClockBeingPowered = isInputLeftBeingPowered(world, x, y, z);

        if (inputClockBeingPowered && !getLastClockState(world, x, y, z)) {
            boolean inputJBeingPowered = isInputTopBeingPowered(world, x, y, z);
            boolean inputKBeingPowered = isInputBottomBeingPowered(world, x, y, z);
            boolean active = getState(world, x, y, z);
            if ((inputJBeingPowered && inputKBeingPowered) || (active && inputKBeingPowered) || (!active && inputJBeingPowered))
                world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
        }

        setLastClockState(world, x, y, z, inputClockBeingPowered);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        boolean state = getState(iBlockAccess, x, y, z);
        int orientation = getOrientation(iBlockAccess, x, y, z);
        return (state && isOutputRight(direction, orientation));
    }

    @Override
    public int getBlockOverlayTextureInGUI(int side, int layer) {
        switch (layer) {
            case 1:
                return textureInputs;
            case 2:
                return textureOutput;
            default:
                return TextureManager.getInstance().emptyTexture;
        }
    }

    private static boolean getLastClockStateFromMetadata(int metadata) {
        return ((metadata & 0x4) >> 2) == 1;
    }

    private static int setLastClockStateInMetadata(int metadata, boolean lastClockState) {
        return ((metadata & 0xB) | (((lastClockState ? 1 : 0) << 2) & 0x4));
    }

    private static boolean getLastClockState(IBlockAccess iBlockAccess, int x, int y, int z) {
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        return getLastClockStateFromMetadata(metadata);
    }

    private static void setLastClockState(World world, int x, int y, int z, boolean lastClockState) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = setLastClockStateInMetadata(oldMetadata, lastClockState);
        world.setBlockMetadata(x, y, z, newMetadata);
    }
}
