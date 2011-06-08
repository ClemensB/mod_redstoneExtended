package net.minecraft.src.redstoneExtended.Util;

public class MathUtil {
    public static int clamp(int min, int max, int value) {
        return (value > max) ? max : ((value < min) ? min : value);
    }

    public static float cubicInterpolation(float t, float a, float b) {
        float weight = t * t * (3 - 2 * t);
        return a + weight * (b - a);
    }
}
