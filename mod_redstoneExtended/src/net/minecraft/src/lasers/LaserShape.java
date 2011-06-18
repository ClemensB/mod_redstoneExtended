package net.minecraft.src.lasers;

import net.minecraft.src.NBTTagCompound;

public class LaserShape implements Cloneable {
    public final float width;

    public final boolean collision;

    public final int damage;

    public final int texture;

    public LaserShape() {
        width = LaserShapes.Default.width;
        collision = LaserShapes.Default.collision;
        damage = LaserShapes.Default.damage;
        texture = LaserShapes.Default.texture;
    }

    public LaserShape(float width, boolean collision, int damage, int texture) {
        this.width = width;
        this.collision = collision;
        this.damage = damage;
        this.texture = texture;
    }

    public static LaserShape readFromNBT(NBTTagCompound nbtTagCompound) {
        float width = nbtTagCompound.getFloat("Width");
        boolean collision = nbtTagCompound.getBoolean("Collision");
        short damage = nbtTagCompound.getShort("Damage");
        byte texture = nbtTagCompound.getByte("Texture");
        return new LaserShape(width, collision, damage, texture);
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, LaserShape laserShape) {
        nbtTagCompound.setFloat("Width", laserShape.width);
        nbtTagCompound.setBoolean("Collision", laserShape.collision);
        nbtTagCompound.setShort("Damage", (short)laserShape.damage);
        nbtTagCompound.setByte("Texture", (byte)laserShape.texture);
    }

    public LaserShape getClone() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            return new LaserShape();
        }
    }

    @Override
    protected LaserShape clone() throws CloneNotSupportedException {
        return (LaserShape)super.clone();
    }

    public boolean equals(LaserShape laserShape) {
        return ((width == laserShape.width) &&
                (collision == laserShape.collision) &&
                (damage == laserShape.damage) &&
                (texture == laserShape.texture));
    }
}
