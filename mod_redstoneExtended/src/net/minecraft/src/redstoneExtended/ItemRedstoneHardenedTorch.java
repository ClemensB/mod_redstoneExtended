package net.minecraft.src.redstoneExtended;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.mod_redstoneExtended;

public class ItemRedstoneHardenedTorch extends ItemBlock {
    public ItemRedstoneHardenedTorch(int id) {
        super(id);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int func_21012_a(int itemDamage)
    {
        return ((itemDamage << 3) & 0x8);
    }

    @Override
    public String getItemNameIS(ItemStack itemStack) {
        return mod_redstoneExtended.getInstance().blockRedstoneHardenedTorchActive.getBlockName() + "." + ((itemStack.getItemDamage() == 0) ? "hardened" : "highSpeed");
    }
}
