package net.minecraft.src.redstoneExtended;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;
import net.minecraft.src.util.TextureManager;

public class BlockRedstoneDFlipFlop extends BlockRedstoneFlipFlop {
    private static final int textureInputs = TextureManager.getInstance().getTerrainTexture("/flipFlops/DFlipFlop/inputs.png");
    private static final int textureOutput = TextureManager.getInstance().getTerrainTexture("/flipFlops/DFlipFlop/output.png");
    private static final int textureInvOutput = TextureManager.getInstance().getTerrainTexture("/flipFlops/DFlipFlop/invOutput.png");

    public BlockRedstoneDFlipFlop(int id) {
        super(id);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int direction) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
            world.setBlockWithNotify(x, y, z, 0);
        }

        boolean inputDataBeingPowered = isInputTopBeingPowered(world, x, y, z);
        boolean inputClockBeingPowered = isInputLeftBeingPowered(world, x, y, z);

        if (inputClockBeingPowered && (inputDataBeingPowered != getState(world, x, y, z)))
            world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess, int x, int y, int z, int direction) {
        boolean state = getState(iBlockAccess, x, y, z);
        int orientation = getOrientation(iBlockAccess, x, y, z);
        return (state && isOutputBottom(direction, orientation) || (!state && isOutputRight(direction, orientation)));
    }

    @Override
    public int getBlockOverlayTextureInGUI(int side, int layer) {
        switch (layer) {
            case 1:
                return textureInputs;
            case 2:
                return textureOutput;
            case 3:
                return textureInvOutput;
            default:
                return TextureManager.getInstance().emptyTexture;
        }
    }
}
