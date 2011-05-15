package net.minecraft.src.redstoneExtended;

import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_redstoneExtended;

public class BlockRedstoneLogicGateNOR extends BlockRedstoneLogicGateBase {
    private static final int textureBase = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/NOR/base.png");
    private static final int textureInputA = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/NOR/inputA.png");
    private static final int textureInputB = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/NOR/inputB.png");
    private static final int textureInputC = ModLoader.addOverride("/terrain.png", "/redstoneExtended/logicGates/NOR/inputC.png");

    public BlockRedstoneLogicGateNOR(int id, boolean isActive) {
        super(id, isActive);
    }

    @Override
    public int blockId(boolean isActive) {
        return isActive ? mod_redstoneExtended.getInstance().blockRedstoneLogicGateNORActive.blockID : mod_redstoneExtended.getInstance().blockRedstoneLogicGateNORIdle.blockID;
    }

    @Override
    public int itemId() {
        return mod_redstoneExtended.getInstance().itemRedstoneLogicGateNOR.shiftedIndex;
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
                return (operatingMode == 0 || operatingMode == 1 || operatingMode == 2) ? textureInputA : emptyTexture;
            case 2:
                return (operatingMode == 0 || operatingMode == 2 || operatingMode == 3) ? textureInputB : emptyTexture;
            case 3:
                return (operatingMode == 0 || operatingMode == 1 || operatingMode == 3) ? textureInputC : emptyTexture;
            default:
                return emptyTexture;
        }
    }

    @Override
    public boolean operatingModeCondition(int operatingMode, boolean inputA, boolean inputB, boolean inputC) {
        switch (operatingMode) {
            case 0:
                return !(inputA || inputB || inputC);
            case 1:
                return !(inputA || inputC);
            case 2:
                return !(inputA || inputB);
            case 3:
                return !(inputB || inputC);
            default:
                return false;
        }
    }
}