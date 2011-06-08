package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLaserFocusLens extends TileEntity {
    public net.minecraft.src.redstoneExtended.Laser.LaserMode mode = new net.minecraft.src.redstoneExtended.Laser.LaserMode();

    public short distance = 0;

    public byte operatingMode = 0;

    public TileEntityLaserFocusLens() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        mode = net.minecraft.src.redstoneExtended.Laser.LaserMode.readFromNBT(nbtTagCompound.getCompoundTag("Mode"));
        distance = nbtTagCompound.getShort("Distance");
        operatingMode = nbtTagCompound.getByte("OperatingMode");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        NBTTagCompound modeTag = new NBTTagCompound();
        net.minecraft.src.redstoneExtended.Laser.LaserMode.writeToNBT(modeTag, mode);
        nbtTagCompound.setCompoundTag("Mode", modeTag);
        nbtTagCompound.setShort("Distance", distance);
        nbtTagCompound.setByte("OperatingMode", operatingMode);
    }
}
