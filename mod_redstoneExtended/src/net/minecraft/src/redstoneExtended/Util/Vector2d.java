package net.minecraft.src.redstoneExtended.Util;

public class Vector2d {
    public double X = 0D;
    public double Y = 0D;

    public Vector2d() {
    }

    public Vector2d(double x, double y) {
        X = x;
        Y = y;
    }

    public Vector2d(double value) {
        X = Y = value;
    }

    public Vector2d multiply(double factor) {
        X *= factor;
        Y *= factor;
        return this;
    }
}
