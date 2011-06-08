package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.IBlockAccess;

public interface ILaserEmitter {
    public boolean canProvideLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction);

    public boolean isProvidingLaserPowerInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction);

    public net.minecraft.src.redstoneExtended.Laser.LaserMode getLaserModeProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction);

    public short getInitialDistanceProvidedInDirection(IBlockAccess iBlockAccess, int x, int y, int z, int direction);
}
