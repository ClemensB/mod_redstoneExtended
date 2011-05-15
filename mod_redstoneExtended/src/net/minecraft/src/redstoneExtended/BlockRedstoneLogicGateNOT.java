package net.minecraft.src.redstoneExtended;

import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_redstoneExtended;

public class BlockRedstoneLogicGateNOT extends BlockRedstoneLogicGateBase {
    private static final int textureBase = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/NOT/base.png");
    private static final int textureInputA = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/NOT/inputA.png");
    private static final int textureInputB = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/NOT/inputB.png");
    private static final int textureInputC = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/NOT/inputC.png");

    public BlockRedstoneLogicGateNOT(int id, boolean isActive) {
        super(id, isActive);
    }

    @Override
    public int blockId(boolean isActive) {
        return isActive ? mod_redstoneExtended.getInstance().blockRedstoneLogicGateNOTActive.blockID : mod_redstoneExtended.getInstance().blockRedstoneLogicGateNOTIdle.blockID;
    }

    @Override
    public int itemId() {
        return mod_redstoneExtended.getInstance().itemRedstoneLogicGateNOT.shiftedIndex;
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
                return (operatingMode == 1) ? textureInputA : emptyTexture;
            case 2:
                return (operatingMode == 0) ? textureInputB : emptyTexture;
            case 3:
                return (operatingMode == 2) ? textureInputC : emptyTexture;
            default:
                return emptyTexture;
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
