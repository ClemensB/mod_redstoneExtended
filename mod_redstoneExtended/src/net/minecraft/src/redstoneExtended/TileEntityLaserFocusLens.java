package net.minecraft.src.redstoneExtended;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityLaserFocusLens extends TileEntity {
    LaserMode mode = new LaserMode();

    byte operatingMode = 0;

    public TileEntityLaserFocusLens() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        mode = LaserMode.readFromNBT(nbtTagCompound.getCompoundTag("Mode"));
        operatingMode = nbtTagCompound.getByte("OperatingMode");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        NBTTagCompound modeTag = new NBTTagCompound();
        LaserMode.writeToNBT(modeTag, mode);
        nbtTagCompound.setCompoundTag("Mode", modeTag);
        nbtTagCompound.setByte("OperatingMode", operatingMode);
    }
}
