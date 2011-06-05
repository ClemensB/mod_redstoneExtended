package net.minecraft.src.redstoneExtended;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLaserEmitter extends TileEntity {
    public byte operatingMode;

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
        nbtTagCompound.setByte("OperatingMode", operatingMode);
    }
}
