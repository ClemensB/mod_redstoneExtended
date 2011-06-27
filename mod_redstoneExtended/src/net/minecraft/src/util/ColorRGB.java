package net.minecraft.src.util;

import net.minecraft.src.NBTTagCompound;

public class ColorRGB implements Cloneable {
    public int R = 255;
    public int G = 255;
    public int B = 255;

    public ColorRGB() {
    }

    public ColorRGB(int r, int g, int b) {
        R = MathUtil.clamp(0, 255, r);
        G = MathUtil.clamp(0, 255, g);
        B = MathUtil.clamp(0, 255, b);
    }

    public ColorRGB(float r, float g, float b) {
        this((int)(r * 255f), (int)(g * 255f), (int)(b * 255f));
    }

    public ColorRGB(int value) {
        this(value, value, value);
    }

    public ColorRGB(float value) {
        this((int)(value * 255f));
    }

    public static ColorRGB readFromNBT(NBTTagCompound nbtTagCompound) {
        byte r = nbtTagCompound.getByte("R");
        byte g = nbtTagCompound.getByte("G");
        byte b = nbtTagCompound.getByte("B");
        return new ColorRGB(r & 0xff, g & 0xff, b & 0xff);
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, ColorRGB color) {
        nbtTagCompound.setByte("R", (byte)color.R);
        nbtTagCompound.setByte("G", (byte)color.G);
        nbtTagCompound.setByte("B", (byte)color.B);
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

    public ColorRGB multiply(float factor) {
        R = (int)((float)R * factor);
        G = (int)((float)G * factor);
        B = (int)((float)B * factor);
        return this;
    }

    public static final class Colors {
        public static final ColorRGB White = new ColorRGB(255);
        public static final ColorRGB Gray = new ColorRGB(128);
        public static final ColorRGB Black = new ColorRGB(0);
        public static final ColorRGB Red = new ColorRGB(255, 0, 0);
        public static final ColorRGB Green = new ColorRGB(0, 255, 0);
        public static final ColorRGB Blue = new ColorRGB(0, 0, 255);
    }
}
