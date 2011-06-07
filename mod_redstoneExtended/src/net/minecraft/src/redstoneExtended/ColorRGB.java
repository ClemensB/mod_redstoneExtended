package net.minecraft.src.redstoneExtended;

import net.minecraft.src.NBTTagCompound;

public class ColorRGB implements Cloneable {
    public byte R = (byte)0;
    public byte G = (byte)0;
    public byte B = (byte)0;

    public ColorRGB() {
    }

    public ColorRGB(byte r, byte g, byte b) {
        R = r;
        G = g;
        B = b;
    }

    public static ColorRGB readFromNBT(NBTTagCompound nbtTagCompound) {
        byte r = nbtTagCompound.getByte("R");
        byte g = nbtTagCompound.getByte("G");
        byte b = nbtTagCompound.getByte("B");
        return new ColorRGB(r, g, b);
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, ColorRGB color) {
        nbtTagCompound.setByte("R", color.R);
        nbtTagCompound.setByte("G", color.G);
        nbtTagCompound.setByte("B", color.B);
    }

    public int toRGBInt() {
        return ((R & 0xff) << 16) | ((G & 0xff) << 8) | (B & 0xff);
    }

    public ColorRGB getClone() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            return new ColorRGB();
        }
    }

    @Override
    protected ColorRGB clone() throws CloneNotSupportedException {
        return (ColorRGB)super.clone();
    }

    public boolean equals(ColorRGB color) {
        return ((R == color.R) &&
                (G == color.G) &&
                (B == color.B));
    }
}
