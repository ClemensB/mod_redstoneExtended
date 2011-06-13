package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLaserFocusLens extends TileEntity {
    public LaserMode mode = new LaserMode();

    public int distance = 0;

    public int operatingMode = 0;

    public TileEntityLaserFocusLens() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        mode = LaserMode.readFromNBT(nbtTagCompound.getCompoundTag("Mode"));
        distance = nbtTagCompound.getShort("Distance");
        operatingMode = nbtTagCompound.getByte("OperatingMode");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        NBTTagCompound modeTag = new NBTTagCompound();
        LaserMode.writeToNBT(modeTag, mode);
        nbtTagCompound.setCompoundTag("Mode", modeTag);
        nbtTagCompound.setShort("Distance", (short)distance);
        nbtTagCompound.setByte("OperatingMode", (byte)operatingMode);
    }
}
