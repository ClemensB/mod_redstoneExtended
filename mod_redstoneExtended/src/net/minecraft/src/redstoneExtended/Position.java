package net.minecraft.src.redstoneExtended;

public class Position {
    public int X;
    public int Y;
    public int Z;

    public Position(int x, int y, int z) {
        X = x;
        Y = y;
        Z = z;
    }

    public Position positionMoveInDirection(int direction) {
        if (direction < 0 || direction > 5)
            throw new IllegalArgumentException("Direction must be in range of 0 and 5");
        switch (Util.invertDirection(direction)) {
            case 0:
                Y--;
                break;
            case 1:
                Y++;
                break;
            case 2:
                Z++;
                break;
            case 3:
                Z--;
                break;
            case 4:
                X++;
                break;
            case 5:
                X--;
                break;
        }
        return this;
    }
}
