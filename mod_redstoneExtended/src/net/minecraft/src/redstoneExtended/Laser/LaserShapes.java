package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.Block;

class LaserShapes {
    public static final LaserShape Default;
    public static final LaserShape Deadly;
    public static final LaserShape Bridge;

    static {
        Default = new LaserShape(4f / 16f, false, 0, Block.blockSnow.blockIndexInTexture);
        Deadly = new LaserShape(2f / 16f, false, 4, Block.blockSnow.blockIndexInTexture);
        Bridge = new LaserShape(14f / 16f, true, 0, Block.glass.blockIndexInTexture);
    }
}
