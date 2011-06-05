package net.minecraft.src.redstoneExtended;

import net.minecraft.src.Block;
import net.minecraft.src.NBTTagCompound;

public class LaserMode implements Cloneable {
    public float width = 1f;

    public boolean collision = false;

    public short damage = (short)0;

    public byte texture = (byte)Block.blockSnow.blockIndexInTexture;

    public Color color = new Color((byte)255, (byte)255, (byte)255);

    public LaserMode() {
    }

    public LaserMode(float width, boolean collision, short damage, byte texture, /*byte colorR, byte colorG, byte colorB*/Color color) {
        this.width = width;
        this.collision = collision;
        this.damage = damage;
        this.texture = texture;
        this.color = color;
    }

    public static LaserMode readFromNBT(NBTTagCompound nbtTagCompound) {
        float width = nbtTagCompound.getFloat("Width");
        boolean collision = nbtTagCompound.getBoolean("Collision");
        short damage = nbtTagCompound.getShort("Damage");
        byte texture = nbtTagCompound.getByte("Texture");
        NBTTagCompound colorTag = nbtTagCompound.getCompoundTag("Color");
        Color color = Color.readFromNBT(colorTag);
        return new LaserMode(width, collision, damage, texture, color);
    }

    public static void writeToNBT(NBTTagCompound nbtTagCompound, LaserMode laserMode) {
        nbtTagCompound.setFloat("Width", laserMode.width);
        nbtTagCompound.setBoolean("Collision", laserMode.collision);
        nbtTagCompound.setShort("Damage", laserMode.damage);
        nbtTagCompound.setByte("Texture", laserMode.texture);
        NBTTagCompound colorTag = new NBTTagCompound();
        Color.writeToNBT(colorTag, laserMode.color);
        nbtTagCompound.setCompoundTag("Color", colorTag);
    }

    public Object copy() {
        try {
            return clone();
        } catch (CloneNotSupportedException e) {
            return new LaserMode();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(LaserMode laserMode) {
        return ((width == laserMode.width) &&
                (collision == laserMode.collision) &&
                (damage == laserMode.damage) &&
                (texture == laserMode.texture) &&
                (color.equals(laserMode.color)));
    }
}
