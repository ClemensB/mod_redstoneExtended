package net.minecraft.src.redstoneExtended;

import net.minecraft.src.NBTTagCompound;

public class ColorRYB implements Cloneable {
    public byte R = (byte)0;
    public byte Y = (byte)0;
    public byte B = (byte)0;

    public ColorRYB() {
    }

    public ColorRYB(byte r, byte y, byte b) {
        R = r;
        Y = y;
        B = b;
    }

    public static ColorRYB readFromNBT(NBTTagCompound nbtTagCompound) {
        byte r = nbtTagCompound.getByte("R");
        byte g = nbtTagCompound.getByte("Y");
        byte b = nbtTagCompound.getByte("B");
        return new ColorRYB(r, g, b);
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, ColorRYB colorRYB) {
        nbtTagCompound.setByte("R", colorRYB.R);
        nbtTagCompound.setByte("Y", colorRYB.Y);
        nbtTagCompound.setByte("B", colorRYB.B);
    }

    public ColorRGB toColorRGB() {
        float iR = (float)(R & 0xff) / 255f, iY = (float)(Y & 0xff) / 255f, iB = (float)(B & 0xff) / 255f;
        float oR, oG, oB;

        float x0, x1, x2, x3, y0, y1;

        x0 = MathUtil.cubicInterpolation(iB, 1.0f, 0.163f);
        x1 = MathUtil.cubicInterpolation(iB, 1.0f, 0.0f);
        x2 = MathUtil.cubicInterpolation(iB, 1.0f, 0.5f);
        x3 = MathUtil.cubicInterpolation(iB, 1.0f, 0.2f);
        y0 = MathUtil.cubicInterpolation(iY, x0, x1);
        y1 = MathUtil.cubicInterpolation(iY, x2, x3);
        oR = MathUtil.cubicInterpolation(iR, y0, y1);

        x0 = MathUtil.cubicInterpolation(iB, 1.0f, 0.373f);
        x1 = MathUtil.cubicInterpolation(iB, 1.0f, 0.66f);
        x2 = MathUtil.cubicInterpolation(iB, 0.0f, 0.0f);
        x3 = MathUtil.cubicInterpolation(iB, 0.5f, 0.094f);
        y0 = MathUtil.cubicInterpolation(iY, x0, x1);
        y1 = MathUtil.cubicInterpolation(iY, x2, x3);
        oG = MathUtil.cubicInterpolation(iR, y0, y1);

        x0 = MathUtil.cubicInterpolation(iB, 1.0f, 0.6f);
        x1 = MathUtil.cubicInterpolation(iB, 0.0f, 0.2f);
        x2 = MathUtil.cubicInterpolation(iB, 0.0f, 0.5f);
        x3 = MathUtil.cubicInterpolation(iB, 0.0f, 0.0f);
        y0 = MathUtil.cubicInterpolation(iY, x0, x1);
        y1 = MathUtil.cubicInterpolation(iY, x2, x3);
        oB = MathUtil.cubicInterpolation(iR, y0, y1);

        byte bR = (byte)(oR * 255f), bG = (byte)(oG * 255f), bB = (byte)(oB * 255f);
        return new ColorRGB(bR, bG, bB);
    }

    public int toRGBInt() {
        return toColorRGB().toRGBInt();
    }

    public ColorRYB getClone() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            return new ColorRYB();
        }
    }

    @Override
    protected ColorRYB clone() throws CloneNotSupportedException {
        return (ColorRYB)super.clone();
    }

    public boolean equals(ColorRYB colorRYB) {
        return ((R == colorRYB.R) &&
                (Y == colorRYB.Y) &&
                (B == colorRYB.B));
    }
}
