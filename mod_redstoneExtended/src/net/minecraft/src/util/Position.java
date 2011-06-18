package net.minecraft.src.util;

public class Position implements Cloneable {
    public int X = 0;
    public int Y = 0;
    public int Z = 0;

    public Position() {
    }

    public Position(int x, int y, int z) {
        X = x;
        Y = y;
        Z = z;
    }

    public Position getClone() {
        try {
            return (Position)clone();
        } catch (CloneNotSupportedException e) {
            return new Position();
        }
    }

    public Vector3d toVector() {
        return new Vector3d((double)X, (double)Y, (double)Z);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Position moveInDirection(int direction) {
        if (direction < 0 || direction > 5)
            throw new IllegalArgumentException("Direction must be in range of 0 and 5");
        switch (DirectionUtil.invertDirection(direction)) {
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
