package net.minecraft.src.redstoneExtended;

import net.minecraft.src.Vec3D;

public class Util {
    public static Vec3D positionMoveInDirection(Vec3D position, int direction) {
        if (direction < 0 || direction > 5)
            throw new IllegalArgumentException("Direction must be between 0 and 5");
        switch (invertDirection(direction)) {
            case 0:
                position.yCoord--;
                break;
            case 1:
                position.yCoord++;
                break;
            case 2:
                position.zCoord++;
                break;
            case 3:
                position.zCoord--;
                break;
            case 4:
                position.xCoord++;
                break;
            case 5:
                position.xCoord--;
                break;
        }
        return position;
    }

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
}
