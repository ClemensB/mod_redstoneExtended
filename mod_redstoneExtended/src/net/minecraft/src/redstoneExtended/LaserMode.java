package net.minecraft.src.redstoneExtended;

import net.minecraft.src.NBTTagCompound;

public class LaserMode implements Cloneable {
    public LaserShape shape = new LaserShape();

    public Color color = new Color((byte)255, (byte)255, (byte)255);

    public LaserMode() {
    }

    public LaserMode(LaserShape laserShape, Color color) {
        this.shape = laserShape;
        this.color = color;
    }

    public static LaserMode readFromNBT(NBTTagCompound nbtTagCompound) {
        NBTTagCompound shapeTag = nbtTagCompound.getCompoundTag("Shape");
        LaserShape laserShape = LaserShape.readFromNBT(shapeTag);
        NBTTagCompound colorTag = nbtTagCompound.getCompoundTag("Color");
        Color color = Color.readFromNBT(colorTag);
        return new LaserMode(laserShape, color);
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, LaserMode laserMode) {
        NBTTagCompound shapeTag = new NBTTagCompound();
        LaserShape.writeToNBT(shapeTag, laserMode.shape);
        nbtTagCompound.setCompoundTag("Shape", shapeTag);
        NBTTagCompound colorTag = new NBTTagCompound();
        Color.writeToNBT(colorTag, laserMode.color);
        nbtTagCompound.setCompoundTag("Color", colorTag);
    }

    public LaserMode getClone() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            return new LaserMode();
        }
    }

    @Override
    protected LaserMode clone() throws CloneNotSupportedException {
        LaserMode clone = (LaserMode)super.clone();
        clone.shape = shape.clone();
        clone.color = color.clone();
        return clone;
    }

    public boolean equals(LaserMode laserMode) {
        return ((shape.equals(laserMode.shape)) &&
                (color.equals(laserMode.color)));
    }
}
