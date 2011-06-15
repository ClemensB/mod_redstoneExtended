package net.minecraft.src.redstoneExtended;

import net.minecraft.src.mod_redstoneExtended;
import net.minecraft.src.redstoneExtended.Util.TextureManager;

public class BlockRedstoneLogicGateNOT extends BlockRedstoneLogicGateBase {
    private static final int textureBase = TextureManager.getInstance().getTerrainTexture("/logicGates/NOT/base.png");
    private static final int textureInputA = TextureManager.getInstance().getTerrainTexture("/logicGates/NOT/inputA.png");
    private static final int textureInputB = TextureManager.getInstance().getTerrainTexture("/logicGates/NOT/inputB.png");
    private static final int textureInputC = TextureManager.getInstance().getTerrainTexture("/logicGates/NOT/inputC.png");

    public BlockRedstoneLogicGateNOT(int id, boolean isActive) {
        super(id, isActive);
    }

    @Override
    public int blockId(boolean isActive) {
        return isActive ? mod_redstoneExtended.getInstance().blockRedstoneLogicGateNOTActive.blockID : mod_redstoneExtended.getInstance().blockRedstoneLogicGateNOTIdle.blockID;
    }

    @Override
    public int operatingModeCount() {
        return 3;
    }

    @Override
    public int operatingModeDelay(int operatingMode) {
        return 2;
    }

    @Override
    public int operatingModeTexture(int operatingMode, int part, boolean isActive) {
        switch (part) {
            case 0:
                return textureBase;
            case 1:
                return (operatingMode == 1) ? textureInputA : TextureManager.getInstance().emptyTexture;
            case 2:
                return (operatingMode == 0) ? textureInputB : TextureManager.getInstance().emptyTexture;
            case 3:
                return (operatingMode == 2) ? textureInputC : TextureManager.getInstance().emptyTexture;
            default:
                return TextureManager.getInstance().emptyTexture;
        }
    }

    @Override
    public boolean operatingModeCondition(int operatingMode, boolean inputA, boolean inputB, boolean inputC) {
        switch (operatingMode) {
            case 0:
                return !inputB;
            case 1:
                return !inputA;
            case 2:
                return !inputC;
            default:
                return false;
        }
    }
}
