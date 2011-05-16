package net.minecraft.src;

import net.minecraft.src.redstoneExtended.*;

public class mod_redstoneExtended extends BaseMod {
    private static mod_redstoneExtended instance;

    public final Block blockRedstoneLogicGateANDIdle;
    public final Block blockRedstoneLogicGateANDActive;
    public final Block blockRedstoneLogicGateNANDIdle;
    public final Block blockRedstoneLogicGateNANDActive;
    public final Block blockRedstoneLogicGateORIdle;
    public final Block blockRedstoneLogicGateORActive;
    public final Block blockRedstoneLogicGateNORIdle;
    public final Block blockRedstoneLogicGateNORActive;
    public final Block blockRedstoneLogicGateXORIdle;
    public final Block blockRedstoneLogicGateXORActive;
    public final Block blockRedstoneLogicGateXNORIdle;
    public final Block blockRedstoneLogicGateXNORActive;
    public final Block blockRedstoneLogicGateNOTIdle;
    public final Block blockRedstoneLogicGateNOTActive;
    public final Block blockRedstoneClock;
    public final Block blockRedstoneLightSensor;
    public final Block blockRedstoneRSNORLatch;
    public final Block blockRedstoneLightBulbOn;
    public final Block blockRedstoneLightBulbOff;
    public final Block blockRedstoneDFlipFlop;
    public final Block blockRedstoneTFlipFlop;
    public final Block blockRedstoneJKFlipFlop;
    public final Block blockCheat;


    public final Item itemRedstoneLogicGateAND;
    public final Item itemRedstoneLogicGateNAND;
    public final Item itemRedstoneLogicGateOR;
    public final Item itemRedstoneLogicGateNOR;
    public final Item itemRedstoneLogicGateXOR;
    public final Item itemRedstoneLogicGateXNOR;
    public final Item itemRedstoneLogicGateNOT;
    public final Item itemRedstoneClock;
    public final Item itemRedstoneLightSensor;
    public final Item itemRedstoneRSNORLatch;
    public final Item itemRedstoneDFlipFlop;
    public final Item itemRedstoneTFlipFlop;
    public final Item itemRedstoneJKFlipFlop;


    public final int renderBlockRedstoneLogicGate;
    public final int renderBlockRedstoneClock;
    public final int renderBlockRedstoneLightSensor;
    public final int renderBlockRedstoneFlipFlop;
    public final int renderBlockRedstoneLightBulb;

    public mod_redstoneExtended() {
        instance = this;

        renderBlockRedstoneLogicGate = ModLoader.getUniqueBlockModelID(this, false);

        blockRedstoneLogicGateANDIdle = (new BlockRedstoneLogicGateAND(getFirstFreeBlock(), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateAND");
        ModLoader.RegisterBlock(blockRedstoneLogicGateANDIdle);

        blockRedstoneLogicGateANDActive = (new BlockRedstoneLogicGateAND(getFirstFreeBlock(), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateAND");
        ModLoader.RegisterBlock(blockRedstoneLogicGateANDActive);

        itemRedstoneLogicGateAND = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneLogicGateANDIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/AND/icon.png")).setItemName("logicGateAND");
        ModLoader.AddName(itemRedstoneLogicGateAND, "AND Gate");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateAND, 1), new Object[]{
                "II ", "___", "OOO", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });

        blockRedstoneLogicGateNANDIdle = (new BlockRedstoneLogicGateNAND(getFirstFreeBlock(), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNAND");
        ModLoader.RegisterBlock(blockRedstoneLogicGateNANDIdle);

        blockRedstoneLogicGateNANDActive = (new BlockRedstoneLogicGateNAND(getFirstFreeBlock(), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNAND");
        ModLoader.RegisterBlock(blockRedstoneLogicGateNANDActive);

        itemRedstoneLogicGateNAND = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneLogicGateNANDIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/NAND/icon.png")).setItemName("logicGateNAND");
        ModLoader.AddName(itemRedstoneLogicGateNAND, "NAND Gate");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateNAND, 1), new Object[]{
                "___", "II ", "OOO", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });


        blockRedstoneLogicGateORIdle = (new BlockRedstoneLogicGateOR(getFirstFreeBlock(), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateOR");
        ModLoader.RegisterBlock(blockRedstoneLogicGateORIdle);

        blockRedstoneLogicGateORActive = (new BlockRedstoneLogicGateOR(getFirstFreeBlock(), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateOR");
        ModLoader.RegisterBlock(blockRedstoneLogicGateORActive);

        itemRedstoneLogicGateOR = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneLogicGateORIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/OR/icon.png")).setItemName("logicGateOR");
        ModLoader.AddName(itemRedstoneLogicGateOR, "OR Gate");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateOR, 1), new Object[]{
                " I ", "___", "OOO", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });


        blockRedstoneLogicGateNORIdle = (new BlockRedstoneLogicGateNOR(getFirstFreeBlock(), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOR");
        ModLoader.RegisterBlock(blockRedstoneLogicGateNORIdle);

        blockRedstoneLogicGateNORActive = (new BlockRedstoneLogicGateNOR(getFirstFreeBlock(), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOR");
        ModLoader.RegisterBlock(blockRedstoneLogicGateNORActive);

        itemRedstoneLogicGateNOR = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneLogicGateNORIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/NOR/icon.png")).setItemName("logicGateNOR");
        ModLoader.AddName(itemRedstoneLogicGateNOR, "NOR Gate");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateNOR, 1), new Object[]{
                "___", " I ", "OOO", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });


        blockRedstoneLogicGateXORIdle = (new BlockRedstoneLogicGateXOR(getFirstFreeBlock(), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXOR");
        ModLoader.RegisterBlock(blockRedstoneLogicGateXORIdle);

        blockRedstoneLogicGateXORActive = (new BlockRedstoneLogicGateXOR(getFirstFreeBlock(), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXOR");
        ModLoader.RegisterBlock(blockRedstoneLogicGateXORActive);

        itemRedstoneLogicGateXOR = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneLogicGateXORIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/XOR/icon.png")).setItemName("logicGateXOR");
        ModLoader.AddName(itemRedstoneLogicGateXOR, "XOR Gate");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateXOR, 1), new Object[]{
                "III", "___", "OOO", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });


        blockRedstoneLogicGateXNORIdle = (new BlockRedstoneLogicGateXNOR(getFirstFreeBlock(), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXNOR");
        ModLoader.RegisterBlock(blockRedstoneLogicGateXNORIdle);

        blockRedstoneLogicGateXNORActive = (new BlockRedstoneLogicGateXNOR(getFirstFreeBlock(), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXNOR");
        ModLoader.RegisterBlock(blockRedstoneLogicGateXNORActive);

        itemRedstoneLogicGateXNOR = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneLogicGateXNORIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/XNOR/icon.png")).setItemName("logicGateXNOR");
        ModLoader.AddName(itemRedstoneLogicGateXNOR, "XNOR Gate");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateXNOR, 1), new Object[]{
                "___", "III", "OOO", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });


        blockRedstoneLogicGateNOTIdle = (new BlockRedstoneLogicGateNOT(getFirstFreeBlock(), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOT");
        ModLoader.RegisterBlock(blockRedstoneLogicGateNOTIdle);

        blockRedstoneLogicGateNOTActive = (new BlockRedstoneLogicGateNOT(getFirstFreeBlock(), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOT");
        ModLoader.RegisterBlock(blockRedstoneLogicGateNOTActive);

        itemRedstoneLogicGateNOT = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneLogicGateNOTIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/NOT/icon.png")).setItemName("logicGateNOT");
        ModLoader.AddName(itemRedstoneLogicGateNOT, "NOT Gate");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateNOT, 1), new Object[]{
                "_", "I", "O", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });


        blockRedstoneClock = (new BlockRedstoneClock(getFirstFreeBlock())).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneClock");
        ModLoader.RegisterBlock(blockRedstoneClock);

        ModLoader.RegisterTileEntity(TileEntityRedstoneClock.class, "RedstoneClock");

        itemRedstoneClock = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneClock)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/clock/icon.png")).setItemName("redstoneClock");
        ModLoader.AddName(itemRedstoneClock, "Redstone Clock");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneClock, 1), new Object[]{
                "I_I", "_O_", "I_I", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });

        renderBlockRedstoneClock = ModLoader.getUniqueBlockModelID(this, false);


        blockRedstoneLightSensor = (new BlockRedstoneLightSensor(getFirstFreeBlock())).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneLightSensor");
        ModLoader.RegisterBlock(blockRedstoneLightSensor);

        ModLoader.RegisterTileEntity(TileEntityLightSensor.class, "RedstoneLightSensor");

        itemRedstoneLightSensor = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneLightSensor)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/lightSensor/icon.png")).setItemName("redstoneLightSensor");
        ModLoader.AddName(itemRedstoneLightSensor, "Light Sensor");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLightSensor, 1), new Object[]{
                "###", "_X_", "OOO", '_', Item.redstone, '#', Block.glass, 'X', new ItemStack(Item.dyePowder, 1, 4), 'O', Block.stone
        });

        renderBlockRedstoneLightSensor = ModLoader.getUniqueBlockModelID(this, false);


        blockRedstoneRSNORLatch = (new BlockRedstoneRSNORLatch(getFirstFreeBlock())).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneRSNORLatch");
        ModLoader.RegisterBlock(blockRedstoneRSNORLatch);

        itemRedstoneRSNORLatch = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneRSNORLatch)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/RSNORLatch/icon.png")).setItemName("redstoneRSNORLatch");
        ModLoader.AddName(itemRedstoneRSNORLatch, "RS NOR Latch");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneRSNORLatch, 1), new Object[] {
                "__O", "I I", "O__", '_', Item.redstone, 'I', Block.torchRedstoneActive, 'O', Block.stone
        });
        ModLoader.AddRecipe(new ItemStack(itemRedstoneRSNORLatch, 1), new Object[] {
                "_IO", "_ _", "OI_", '_', Item.redstone, 'I', Block.torchRedstoneActive, 'O', Block.stone
        });
        ModLoader.AddRecipe(new ItemStack(itemRedstoneRSNORLatch, 1), new Object[] {
                "O__", "I I", "__O", '_', Item.redstone, 'I', Block.torchRedstoneActive, 'O', Block.stone
        });
        ModLoader.AddRecipe(new ItemStack(itemRedstoneRSNORLatch, 1), new Object[] {
                "OI_", "_ _", "_IO", '_', Item.redstone, 'I', Block.torchRedstoneActive, 'O', Block.stone
        });

        renderBlockRedstoneFlipFlop = ModLoader.getUniqueBlockModelID(this, false);


        blockRedstoneLightBulbOn = (new BlockRedstoneLightBulb(getFirstFreeBlock(), true)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(Block.soundWoodFootstep).setBlockName("lightBulb");
        ModLoader.RegisterBlock(blockRedstoneLightBulbOn);

        blockRedstoneLightBulbOff = (new BlockRedstoneLightBulb(getFirstFreeBlock(), false)).setHardness(0.0F).setLightValue(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("lightBulb");
        ModLoader.AddName(blockRedstoneLightBulbOff, "Light Bulb");
        ModLoader.RegisterBlock(blockRedstoneLightBulbOff);

        ModLoader.AddRecipe(new ItemStack(blockRedstoneLightBulbOff, 1), new Object[] {
                " # ", "#_#", " / ", '#', Block.glass, '_', Item.redstone, '/', Item.stick
        });

        renderBlockRedstoneLightBulb = ModLoader.getUniqueBlockModelID(this, false);


        blockRedstoneDFlipFlop = (new BlockRedstoneDFlipFlop(getFirstFreeBlock())).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneDFlipFlop");
        ModLoader.RegisterBlock(blockRedstoneDFlipFlop);

        itemRedstoneDFlipFlop = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneDFlipFlop)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/DFlipFlop/icon.png")).setItemName("redstoneDFlipFlop");
        ModLoader.AddName(itemRedstoneDFlipFlop, "D flip-flop");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneDFlipFlop, 1), new Object[] {
                "I  ", "#RI", "I  ", 'I', Block.torchRedstoneActive, '#', Item.redstone, 'R', itemRedstoneRSNORLatch
        });


        blockRedstoneTFlipFlop = (new BlockRedstoneTFlipFlop(getFirstFreeBlock())).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneTFlipFlop");
        ModLoader.RegisterBlock(blockRedstoneTFlipFlop);

        itemRedstoneTFlipFlop = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneTFlipFlop)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/TFlipFlop/icon.png")).setItemName("redstoneTFlipFlop");
        ModLoader.AddName(itemRedstoneTFlipFlop, "T flip-flop");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneTFlipFlop, 1), new Object[] {
                "I  ", "IRI", "I  ", 'I', Block.torchRedstoneActive, '#', Item.redstone, 'R', itemRedstoneRSNORLatch
        });


        blockRedstoneJKFlipFlop = (new BlockRedstoneJKFlipFlop(getFirstFreeBlock())).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneJKFlipFlop");
        ModLoader.RegisterBlock(blockRedstoneJKFlipFlop);

        itemRedstoneJKFlipFlop = (new ItemReed(ModLoader.getUniqueEntityId(), blockRedstoneJKFlipFlop)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/JKFlipFlop/icon.png")).setItemName("redstoneJKFlipFlop");
        ModLoader.AddName(itemRedstoneJKFlipFlop, "JK flip-flop");

        ModLoader.AddRecipe(new ItemStack(itemRedstoneJKFlipFlop, 1), new Object[] {
                "#  ", "IRI", "#  ", 'I', Block.torchRedstoneActive, '#', Item.redstone, 'R', itemRedstoneRSNORLatch
        });


        blockCheat = (new BlockCheat(getFirstFreeBlock())).setHardness(0.0F).setStepSound(Block.soundMetalFootstep).setBlockName("cheatBlock");
        ModLoader.AddName(blockCheat, "Cheat Block");
        ModLoader.RegisterBlock(blockCheat);
        if (System.getenv().containsKey("mcDebug")) {
            ModLoader.AddRecipe(new ItemStack(blockCheat, 1), new Object[]{
                    "#", '#', Block.dirt
            });
        }
    }

    private int getFirstFreeBlock() {
        for (int i = Block.blocksList.length - 1; i >= 0; --i) {
            if (Block.blocksList[i] == null)
                return i;
        }

        return -1;
    }

    @Override
    public String Version() {
        return "1.5_01_Dev";
    }

    @Override
    public boolean RenderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelID) {
        if (modelID == renderBlockRedstoneLogicGate)
            return MyRenderBlocks.renderBlockRedstoneLogicGate(renderBlocks, iBlockAccess, block, x, y, z);
        else if (modelID == renderBlockRedstoneClock)
            return MyRenderBlocks.renderBlockRedstoneClock(renderBlocks, iBlockAccess, block, x, y, z);
        else if (modelID == renderBlockRedstoneLightSensor)
            return MyRenderBlocks.renderBlockRedstoneLightSensor(renderBlocks, iBlockAccess, block, x, y, z);
        else if (modelID == renderBlockRedstoneFlipFlop)
            return MyRenderBlocks.renderBlockRedstoneFlipFlop(renderBlocks, iBlockAccess, block, x, y, z);
        else
            return modelID == renderBlockRedstoneLightBulb && MyRenderBlocks.renderBlockRedstoneLightBulb(renderBlocks, iBlockAccess, block, x, y, z);
    }

    public static mod_redstoneExtended getInstance() {
        return instance;
    }
}