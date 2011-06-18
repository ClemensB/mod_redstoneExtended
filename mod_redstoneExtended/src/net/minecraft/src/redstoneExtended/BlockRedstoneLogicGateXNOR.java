package net.minecraft.src.redstoneExtended;

import net.minecraft.src.mod_redstoneExtended;
import net.minecraft.src.redstoneExtended.Util.TextureManager;

public class BlockRedstoneLogicGateXNOR extends BlockRedstoneLogicGate {
    private static final int textureBase = TextureManager.getInstance().getTerrainTexture("/logicGates/XNOR/base.png");
    private static final int textureInputA = TextureManager.getInstance().getTerrainTexture("/logicGates/XNOR/inputA.png");
    private static final int textureInputB = TextureManager.getInstance().getTerrainTexture("/logicGates/XNOR/inputB.png");
    private static final int textureInputC = TextureManager.getInstance().getTerrainTexture("/logicGates/XNOR/inputC.png");

    public BlockRedstoneLogicGateXNOR(int id, boolean isActive) {
        super(id, isActive);
    }

    @Override
    public int blockId(boolean isActive) {
        return isActive ? mod_redstoneExtended.getInstance().blockRedstoneLogicGateXNORActive.blockID : mod_redstoneExtended.getInstance().blockRedstoneLogicGateXNORIdle.blockID;
    }

    @Override
    public int operatingModeCount() {
        return 4;
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
                return (operatingMode == 0 || operatingMode == 1 || operatingMode == 2) ? textureInputA : TextureManager.getInstance().emptyTexture;
            case 2:
                return (operatingMode == 0 || operatingMode == 2 || operatingMode == 3) ? textureInputB : TextureManager.getInstance().emptyTexture;
            case 3:
                return (operatingMode == 0 || operatingMode == 1 || operatingMode == 3) ? textureInputC : TextureManager.getInstance().emptyTexture;
            default:
                return TextureManager.getInstance().emptyTexture;
        }
    }

    @Override
    public boolean operatingModeCondition(int operatingMode, boolean inputA, boolean inputB, boolean inputC) {
        switch (operatingMode) {
            case 0:
                return !((inputA && inputB && inputC) || (inputA && !inputB && !inputC) || (!inputA && inputB && !inputC) || (!inputA && !inputB && inputC));
            case 1:
                return !(inputA ^ inputC);
            case 2:
                return !(inputA ^ inputB);
            case 3:
                return !(inputB ^ inputC);
            default:
                return false;
        }
    }
}
