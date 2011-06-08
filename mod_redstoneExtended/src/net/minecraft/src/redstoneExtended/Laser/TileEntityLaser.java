package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLaser extends TileEntity {
    public byte orientation = 0;
    public short distance = 0;

    public net.minecraft.src.redstoneExtended.Laser.LaserMode mode = new net.minecraft.src.redstoneExtended.Laser.LaserMode();

    public TileEntityLaser() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        orientation = nbtTagCompound.getByte("Orientation");
        distance = nbtTagCompound.getShort("Distance");
        mode = net.minecraft.src.redstoneExtended.Laser.LaserMode.readFromNBT(nbtTagCompound.getCompoundTag("Mode"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("Orientation", orientation);
        nbtTagCompound.setShort("Distance", distance);
        NBTTagCompound modeTag = new NBTTagCompound();
        net.minecraft.src.redstoneExtended.Laser.LaserMode.writeToNBT(modeTag, mode);
        nbtTagCompound.setCompoundTag("Mode", modeTag);
    }
}
