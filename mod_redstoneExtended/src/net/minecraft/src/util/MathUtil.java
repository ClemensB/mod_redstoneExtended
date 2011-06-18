package net.minecraft.src.util;

public class MathUtil {
    public static int clamp(int min, int max, int value) {
        return (value > max) ? max : ((value < min) ? min : value);
    }
}
