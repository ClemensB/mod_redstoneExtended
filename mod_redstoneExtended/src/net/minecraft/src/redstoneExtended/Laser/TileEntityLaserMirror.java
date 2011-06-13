package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLaserMirror extends TileEntity {
    public LaserMode mode = new LaserMode();

    public int distance = 0;

    public TileEntityLaserMirror() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        mode = net.minecraft.src.redstoneExtended.Laser.LaserMode.readFromNBT(nbtTagCompound.getCompoundTag("Mode"));
        distance = nbtTagCompound.getShort("Distance");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        NBTTagCompound modeTag = new NBTTagCompound();
        net.minecraft.src.redstoneExtended.Laser.LaserMode.writeToNBT(modeTag, mode);
        nbtTagCompound.setCompoundTag("Mode", modeTag);
        nbtTagCompound.setShort("Distance", (short)distance);
    }
}
