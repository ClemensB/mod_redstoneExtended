package net.minecraft.src.redstoneExtended.Laser;

import net.minecraft.src.NBTTagCompound;

public class LaserMode implements Cloneable {
    public net.minecraft.src.redstoneExtended.Laser.LaserShape shape = new net.minecraft.src.redstoneExtended.Laser.LaserShape();

    public net.minecraft.src.redstoneExtended.Util.ColorRGB color = new net.minecraft.src.redstoneExtended.Util.ColorRGB((byte)255, (byte)255, (byte)255);

    public LaserMode() {
    }

    public LaserMode(net.minecraft.src.redstoneExtended.Laser.LaserShape laserShape, net.minecraft.src.redstoneExtended.Util.ColorRGB color) {
        this.shape = laserShape;
        this.color = color;
    }

    public static LaserMode readFromNBT(NBTTagCompound nbtTagCompound) {
        NBTTagCompound shapeTag = nbtTagCompound.getCompoundTag("Shape");
        net.minecraft.src.redstoneExtended.Laser.LaserShape laserShape = net.minecraft.src.redstoneExtended.Laser.LaserShape.readFromNBT(shapeTag);
        NBTTagCompound colorTag = nbtTagCompound.getCompoundTag("Color");
        net.minecraft.src.redstoneExtended.Util.ColorRGB color = net.minecraft.src.redstoneExtended.Util.ColorRGB.readFromNBT(colorTag);
        return new LaserMode(laserShape, color);
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, LaserMode laserMode) {
        NBTTagCompound shapeTag = new NBTTagCompound();
        net.minecraft.src.redstoneExtended.Laser.LaserShape.writeToNBT(shapeTag, laserMode.shape);
        nbtTagCompound.setCompoundTag("Shape", shapeTag);
        NBTTagCompound colorTag = new NBTTagCompound();
        net.minecraft.src.redstoneExtended.Util.ColorRGB.writeToNBT(colorTag, laserMode.color);
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
