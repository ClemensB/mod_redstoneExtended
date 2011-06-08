package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.TileEntity;
import net.minecraft.src.redstoneExtended.BlockRedstoneLightSensor;

public class TileEntityLightSensor extends TileEntity {
    public TileEntityLightSensor() {
    }

    @Override
    public void updateEntity() {
        BlockRedstoneLightSensor.update(worldObj, xCoord, yCoord, zCoord);
    }
}
