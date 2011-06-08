package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.redstoneExtended.BlockRedstoneClock;

public class TileEntityRedstoneClock extends TileEntity {
    private boolean state = false;
    private short time = 0;
    private byte delaySetting = 0;

    private static final short[] delays;

    static {
        delays = new short[] {1, 2, 4, 8, 16, 32};
    }

    public TileEntityRedstoneClock() {
    }

    @Override
    public void updateEntity() {
        time++;
        if (time > delays[delaySetting]) {
            time = 0;
            state = !state;
            BlockRedstoneClock.setState(worldObj, xCoord, yCoord, zCoord, state);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        state = nbtTagCompound.getBoolean("State");
        time = nbtTagCompound.getShort("Time");
        delaySetting = nbtTagCompound.getByte("DelaySetting");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("State", state);
        nbtTagCompound.setShort("Time", time);
        nbtTagCompound.setByte("DelaySetting", delaySetting);
    }

    public void delaySettingChanged() {
        delaySetting = BlockRedstoneClock.getDelaySetting(worldObj, xCoord, yCoord, zCoord);
    }
}
