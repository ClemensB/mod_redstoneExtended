package net.minecraft.src.redstoneExtended;

import net.minecraft.src.NBTTagCompound;

public class Color {
    public byte R = (byte)0;
    public byte G = (byte)0;
    public byte B = (byte)0;

    public Color(byte r, byte g, byte b) {
        R = r;
        G = g;
        B = b;
    }

    public static Color readFromNBT(NBTTagCompound nbtTagCompound) {
        byte r = nbtTagCompound.getByte("R");
        byte g = nbtTagCompound.getByte("G");
        byte b = nbtTagCompound.getByte("B");
        return new Color(r, g, b);
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, Color color) {
        nbtTagCompound.setByte("R", color.R);
        nbtTagCompound.setByte("G", color.G);
        nbtTagCompound.setByte("B", color.B);
    }

    public int toRGBInt() {
        return ((R & 0xff) << 16) | ((G & 0xff) << 8) | (B & 0xff);
    }

    public boolean equals(Color color) {
        return ((R == color.R) &&
                (G == color.G) &&
                (B == color.B));
    }
}
