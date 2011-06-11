package net.minecraft.src.redstoneExtended.Util;

import net.minecraft.src.NBTTagCompound;

public class ColorRGB implements Cloneable {
    public byte R = (byte)255;
    public byte G = (byte)255;
    public byte B = (byte)255;

    public ColorRGB() {
    }

    public ColorRGB(byte r, byte g, byte b) {
        R = r;
        G = g;
        B = b;
    }

    public ColorRGB(int r, int g, int b) {
        R = (byte)r;
        G = (byte)g;
        B = (byte)b;
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
    public ColorRGB clone() throws CloneNotSupportedException {
        return (ColorRGB)super.clone();
    }

    public boolean equals(ColorRGB color) {
        return ((R == color.R) &&
                (G == color.G) &&
                (B == color.B));
    }

    public static final class Colors {
        public static final ColorRGB White = new ColorRGB(255, 255, 255);
        public static final ColorRGB Black = new ColorRGB(0, 0, 0);
        public static final ColorRGB Red = new ColorRGB(255, 0, 0);
        public static final ColorRGB Green = new ColorRGB(0, 255, 0);
        public static final ColorRGB Blue = new ColorRGB(0, 0, 255);
    }
}
