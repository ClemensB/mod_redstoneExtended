package net.minecraft.src.redstoneExtended;

import net.minecraft.src.TileEntity;

public class TileEntityLightSensor extends TileEntity {
    public TileEntityLightSensor() {
    }

    @Override
    public void updateEntity() {
        BlockRedstoneLightSensor.update(worldObj, xCoord, yCoord, zCoord);
    }
}
