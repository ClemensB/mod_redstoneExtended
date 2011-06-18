package net.minecraft.src.lasers;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLaserEmitter extends TileEntity {
    public int operatingMode;

    public TileEntityLaserEmitter() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        operatingMode = nbtTagCompound.getByte("OperatingMode");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("OperatingMode", (byte)operatingMode);
    }
}
