package net.minecraft.src.redstoneExtended;

import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_redstoneExtended;

public class BlockRedstoneLogicGateXNOR extends BlockRedstoneLogicGateBase {
    private static final int textureBase = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/XNOR/base.png");
    private static final int textureInputA = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/XNOR/inputA.png");
    private static final int textureInputB = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/XNOR/inputB.png");
    private static final int textureInputC = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/XNOR/inputC.png");

    public BlockRedstoneLogicGateXNOR(int id, boolean isActive) {
        super(id, isActive);
    }

    @Override
    public int blockId(boolean isActive) {
        return isActive ? mod_redstoneExtended.getInstance().blockRedstoneLogicGateXNORActive.blockID : mod_redstoneExtended.getInstance().blockRedstoneLogicGateXNORIdle.blockID;
    }

    @Override
    public int itemId() {
        return mod_redstoneExtended.getInstance().itemRedstoneLogicGateXNOR.shiftedIndex;
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
                return (operatingMode == 0 || operatingMode == 1 || operatingMode == 2) ? textureInputA : mod_redstoneExtended.getInstance().emptyTexture;
            case 2:
                return (operatingMode == 0 || operatingMode == 2 || operatingMode == 3) ? textureInputB : mod_redstoneExtended.getInstance().emptyTexture;
            case 3:
                return (operatingMode == 0 || operatingMode == 1 || operatingMode == 3) ? textureInputC : mod_redstoneExtended.getInstance().emptyTexture;
            default:
                return mod_redstoneExtended.getInstance().emptyTexture;
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
