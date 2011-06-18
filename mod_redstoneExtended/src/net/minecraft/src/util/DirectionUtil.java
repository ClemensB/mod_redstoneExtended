package net.minecraft.src.util;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;

public class DirectionUtil {
    public static int invertDirection(int direction) {
        if (direction < 0 || direction > 5)
            throw new IllegalArgumentException("Direction must be between 0 and 5");
        switch (direction) {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 4;
            default:
                return 0;
        }
    }

    public static int getOrientationFromPlayer(EntityLiving entityLiving) {
        int orientation = MathHelper.floor_double((double)((entityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 0x3;
        switch (orientation) {
            case 0:
                orientation = 2;
                break;
            case 1:
                orientation = 5;
                break;
            case 2:
                orientation = 3;
                break;
            case 3:
                orientation = 4;
                break;
        }
        return orientation;
    }
}
