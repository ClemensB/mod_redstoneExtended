package net.minecraft.src.redstoneExtended;

import net.minecraft.src.*;

public class BlockCheat extends Block {
    public BlockCheat(int id) {
        super(id, 22, Material.ground);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer) {
        InventoryBasic shop = new InventoryBasic("Cheat Block", 54);

        shop.setInventorySlotContents(0, new ItemStack(Block.stone, -1));
        shop.setInventorySlotContents(1, new ItemStack(Block.sandStone, -1));
        shop.setInventorySlotContents(2, new ItemStack(Block.planks, -1));
        shop.setInventorySlotContents(3, new ItemStack(Block.glass, -1));
        shop.setInventorySlotContents(9, new ItemStack(Block.torchWood, -1));
        shop.setInventorySlotContents(10, new ItemStack(Item.doorWood, -1));
        shop.setInventorySlotContents(11, new ItemStack(Block.workbench, -1));
        shop.setInventorySlotContents(12, new ItemStack(Block.stoneOvenIdle, -1));
        shop.setInventorySlotContents(13, new ItemStack(Item.bed, -1));
        shop.setInventorySlotContents(18, new ItemStack(Item.redstone, -1));
        shop.setInventorySlotContents(19, new ItemStack(Block.torchRedstoneActive, -1));
        shop.setInventorySlotContents(20, new ItemStack(Item.redstoneRepeater, -1));
        shop.setInventorySlotContents(21, new ItemStack(Block.dispenser, -1));
        shop.setInventorySlotContents(22, new ItemStack(Block.lever, -1));
        shop.setInventorySlotContents(23, new ItemStack(Block.button, -1));
        shop.setInventorySlotContents(27, new ItemStack(Item.pickaxeDiamond, -1));
        shop.setInventorySlotContents(28, new ItemStack(Item.shovelDiamond, -1));
        shop.setInventorySlotContents(29, new ItemStack(Item.swordDiamond, -1));
        shop.setInventorySlotContents(36, new ItemStack(Item.dyePowder, -1, 4));

        entityPlayer.displayGUIChest(shop);

        return false;
    }
}
