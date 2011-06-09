package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.Block;

public class LaserShapes {
    public static final LaserShape Default;
    public static final LaserShape Deadly;
    public static final LaserShape Bridge;

    static {
        Default = new LaserShape(4f / 16f, false, (short)0, (byte)Block.blockSnow.blockIndexInTexture);
        Deadly = new LaserShape(2f / 16f, false, (short)4, (byte)Block.blockSnow.blockIndexInTexture);
        Bridge = new LaserShape(14f / 16f, true, (short)0, (byte)Block.glass.blockIndexInTexture);
    }
}
