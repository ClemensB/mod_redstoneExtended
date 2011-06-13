package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLaser extends TileEntity {
    public int orientation = 0;
    public int distance = 0;

    public LaserMode mode = new LaserMode();

    public TileEntityLaser() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        orientation = nbtTagCompound.getByte("Orientation");
        distance = nbtTagCompound.getShort("Distance");
        mode = LaserMode.readFromNBT(nbtTagCompound.getCompoundTag("Mode"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("Orientation", (byte)orientation);
        nbtTagCompound.setShort("Distance", (short)distance);
        NBTTagCompound modeTag = new NBTTagCompound();
        LaserMode.writeToNBT(modeTag, mode);
        nbtTagCompound.setCompoundTag("Mode", modeTag);
    }
}
