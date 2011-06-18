package net.minecraft.src.util;

public class Vector3d {
    public double X = 0D;
    public double Y = 0D;
    public double Z = 0D;

    public Vector3d() {
    }

    public Vector3d(double x, double y, double z) {
        X = x;
        Y = y;
        Z = z;
    }

    public Vector3d(double value) {
        X = Y = Z = value;
    }

    public Vector3d multiply(double factor) {
        X *= factor;
        Y *= factor;
        Z *= factor;
        return this;
    }
}
