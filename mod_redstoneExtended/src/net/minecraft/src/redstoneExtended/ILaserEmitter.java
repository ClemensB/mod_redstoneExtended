package net.minecraft.src.redstoneExtended;

import net.minecraft.src.IBlockAccess;

public interface ILaserEmitter {
    public boolean canProvideLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction);

    public LaserMode getLaserModeProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction);

    public int getInitialDistanceProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction);
}
