package net.minecraft.src;

import net.minecraft.src.redstoneExtended.*;
import net.minecraft.src.util.IdManager;
import net.minecraft.src.util.LoggingUtil;
import net.minecraft.src.util.Renderers;
import net.minecraft.src.util.TextureManager;

public class mod_redstoneExtended extends BaseMod {
    private static mod_redstoneExtended instance;

    public final Block blockRedstoneLogicGateNOTIdle;
    public final Block blockRedstoneLogicGateNOTActive;
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
    public final Block blockRedstoneClock;
    public final Block blockRedstoneLightSensor;
    public final Block blockRedstoneRSNORLatch;
    public final Block blockRedstoneLightBulbOn;
    public final Block blockRedstoneLightBulbOff;
    public final Block blockRedstoneDFlipFlop;
    public final Block blockRedstoneTFlipFlop;
    public final Block blockRedstoneJKFlipFlop;
    public final Block blockRedstoneRandom;
    public final Block blockRedstoneHardenedTorchActive;
    public final Block blockRedstoneHardenedTorchIdle;
    public final Block blockCheat;


    public final int renderStandardBlockWithOverlay;
    public final int renderBlockTorchExtended;

    public mod_redstoneExtended() {
        instance = this;

        renderStandardBlockWithOverlay = ModLoader.getUniqueBlockModelID(this, true);
        renderBlockTorchExtended = ModLoader.getUniqueBlockModelID(this, false);


        blockRedstoneLogicGateNOTIdle = (new BlockRedstoneLogicGateNOT(IdManager.getInstance().getId("logicGateNOTIdle", IdManager.IdType.Block), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOT");
        blockRedstoneLogicGateNOTActive = (new BlockRedstoneLogicGateNOT(IdManager.getInstance().getId("logicGateNOTActive", IdManager.IdType.Block), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOT");
        blockRedstoneLogicGateANDIdle = (new BlockRedstoneLogicGateAND(IdManager.getInstance().getId("logicGateANDIdle", IdManager.IdType.Block), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateAND");
        blockRedstoneLogicGateANDActive = (new BlockRedstoneLogicGateAND(IdManager.getInstance().getId("logicGateANDActive", IdManager.IdType.Block), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateAND");
        blockRedstoneLogicGateNANDIdle = (new BlockRedstoneLogicGateNAND(IdManager.getInstance().getId("logicGateNANDIdle", IdManager.IdType.Block), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNAND");
        blockRedstoneLogicGateNANDActive = (new BlockRedstoneLogicGateNAND(IdManager.getInstance().getId("logicGateNANDActive", IdManager.IdType.Block), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNAND");
        blockRedstoneLogicGateORIdle = (new BlockRedstoneLogicGateOR(IdManager.getInstance().getId("logicGateORIdle", IdManager.IdType.Block), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateOR");
        blockRedstoneLogicGateORActive = (new BlockRedstoneLogicGateOR(IdManager.getInstance().getId("logicGateORActive", IdManager.IdType.Block), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateOR");
        blockRedstoneLogicGateNORIdle = (new BlockRedstoneLogicGateNOR(IdManager.getInstance().getId("logicGateNORIdle", IdManager.IdType.Block), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOR");
        blockRedstoneLogicGateNORActive = (new BlockRedstoneLogicGateNOR(IdManager.getInstance().getId("logicGateNORActive", IdManager.IdType.Block), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOR");
        blockRedstoneLogicGateXORIdle = (new BlockRedstoneLogicGateXOR(IdManager.getInstance().getId("logicGateXORIdle", IdManager.IdType.Block), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXOR");
        blockRedstoneLogicGateXORActive = (new BlockRedstoneLogicGateXOR(IdManager.getInstance().getId("logicGateXORActive", IdManager.IdType.Block), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXOR");
        blockRedstoneLogicGateXNORIdle = (new BlockRedstoneLogicGateXNOR(IdManager.getInstance().getId("logicGateXNORIdle", IdManager.IdType.Block), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXNOR");
        blockRedstoneLogicGateXNORActive = (new BlockRedstoneLogicGateXNOR(IdManager.getInstance().getId("logicGateXNORActive", IdManager.IdType.Block), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXNOR");
        blockRedstoneClock = (new BlockRedstoneClock(IdManager.getInstance().getId("redstoneClock", IdManager.IdType.Block))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneClock");
        blockRedstoneLightSensor = (new BlockRedstoneLightSensor(IdManager.getInstance().getId("redstoneLightSensor", IdManager.IdType.Block))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneLightSensor");
        blockRedstoneRSNORLatch = (new BlockRedstoneRSNORLatch(IdManager.getInstance().getId("redstoneRSNORLatch", IdManager.IdType.Block))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneRSNORLatch");
        blockRedstoneLightBulbOn = (new BlockRedstoneLightBulb(IdManager.getInstance().getId("lightBulbOn", IdManager.IdType.Block), true)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(Block.soundWoodFootstep).setBlockName("lightBulbOn");
        blockRedstoneLightBulbOff = (new BlockRedstoneLightBulb(IdManager.getInstance().getId("lightBulbOff", IdManager.IdType.Block), false)).setHardness(0.0F).setLightValue(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("lightBulbOff");
        blockRedstoneDFlipFlop = (new BlockRedstoneDFlipFlop(IdManager.getInstance().getId("redstoneDFlipFlop", IdManager.IdType.Block))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneDFlipFlop");
        blockRedstoneTFlipFlop = (new BlockRedstoneTFlipFlop(IdManager.getInstance().getId("redstoneTFlipFlop", IdManager.IdType.Block))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneTFlipFlop");
        blockRedstoneJKFlipFlop = (new BlockRedstoneJKFlipFlop(IdManager.getInstance().getId("redstoneJKFlipFlop", IdManager.IdType.Block))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneJKFlipFlop");
        blockRedstoneRandom = (new BlockRedstoneRandom(IdManager.getInstance().getId("redstoneRandom", IdManager.IdType.Block))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneRandom");
        blockRedstoneHardenedTorchIdle = (new BlockRedstoneHardenedTorch(IdManager.getInstance().getId("redstoneHardenedTorchIdle", IdManager.IdType.Block), TextureManager.getInstance().getTerrainTexture("/hardenedTorch/idle.png"), false)).setHardness(0.0F).setStepSound(Block.soundMetalFootstep).setBlockName("redstoneHardenedTorch");
        blockRedstoneHardenedTorchActive = (new BlockRedstoneHardenedTorch(IdManager.getInstance().getId("redstoneHardenedTorchActive", IdManager.IdType.Block), TextureManager.getInstance().getTerrainTexture("/hardenedTorch/active.png"), true)).setHardness(0.0F).setLightValue(0.5F).setStepSound(Block.soundMetalFootstep).setBlockName("redstoneHardenedTorch");
        blockCheat = (new BlockCheat(IdManager.getInstance().getId("cheatBlock", IdManager.IdType.Block))).setHardness(0.0F).setStepSound(Block.soundMetalFootstep).setBlockName("cheatBlock");


        ModLoader.RegisterBlock(blockRedstoneLogicGateNOTIdle);
        ModLoader.RegisterBlock(blockRedstoneLogicGateNOTActive);
        ModLoader.RegisterBlock(blockRedstoneLogicGateANDIdle);
        ModLoader.RegisterBlock(blockRedstoneLogicGateANDActive);
        ModLoader.RegisterBlock(blockRedstoneLogicGateNANDIdle);
        ModLoader.RegisterBlock(blockRedstoneLogicGateNANDActive);
        ModLoader.RegisterBlock(blockRedstoneLogicGateORIdle);
        ModLoader.RegisterBlock(blockRedstoneLogicGateORActive);
        ModLoader.RegisterBlock(blockRedstoneLogicGateNORIdle);
        ModLoader.RegisterBlock(blockRedstoneLogicGateNORActive);
        ModLoader.RegisterBlock(blockRedstoneLogicGateXORIdle);
        ModLoader.RegisterBlock(blockRedstoneLogicGateXORActive);
        ModLoader.RegisterBlock(blockRedstoneLogicGateXNORIdle);
        ModLoader.RegisterBlock(blockRedstoneLogicGateXNORActive);
        ModLoader.RegisterBlock(blockRedstoneClock);
        ModLoader.RegisterBlock(blockRedstoneLightSensor);
        ModLoader.RegisterBlock(blockRedstoneRSNORLatch);
        ModLoader.RegisterBlock(blockRedstoneLightBulbOn);
        ModLoader.RegisterBlock(blockRedstoneLightBulbOff);
        ModLoader.RegisterBlock(blockRedstoneDFlipFlop);
        ModLoader.RegisterBlock(blockRedstoneTFlipFlop);
        ModLoader.RegisterBlock(blockRedstoneJKFlipFlop);
        ModLoader.RegisterBlock(blockRedstoneRandom);
        ModLoader.RegisterBlock(blockRedstoneHardenedTorchIdle);
        ModLoader.RegisterBlock(blockRedstoneHardenedTorchActive, ItemRedstoneHardenedTorch.class);
        ModLoader.RegisterBlock(blockCheat);


        ModLoader.AddName(blockRedstoneLogicGateNOTIdle, "NOT Gate");
        ModLoader.AddName(blockRedstoneLogicGateANDIdle, "AND Gate");
        ModLoader.AddName(blockRedstoneLogicGateNANDIdle, "NAND Gate");
        ModLoader.AddName(blockRedstoneLogicGateORIdle, "OR Gate");
        ModLoader.AddName(blockRedstoneLogicGateNORIdle, "NOR Gate");
        ModLoader.AddName(blockRedstoneLogicGateXORIdle, "XOR Gate");
        ModLoader.AddName(blockRedstoneLogicGateXNORIdle, "XNOR Gate");
        ModLoader.AddName(blockRedstoneClock, "Redstone Clock");
        ModLoader.AddName(blockRedstoneLightSensor, "Light Sensor");
        ModLoader.AddName(blockRedstoneRSNORLatch, "RS NOR Latch");
        ModLoader.AddName(blockRedstoneLightBulbOff, "Light Bulb");
        ModLoader.AddName(blockRedstoneDFlipFlop, "D flip-flop");
        ModLoader.AddName(blockRedstoneTFlipFlop, "T flip-flop");
        ModLoader.AddName(blockRedstoneJKFlipFlop, "JK flip-flop");
        ModLoader.AddName(blockRedstoneRandom, "Random Number Generator");
        ModLoader.AddLocalization(blockRedstoneHardenedTorchActive.getBlockName() + ".hardened.name", "Hardened Redstone Torch");
        ModLoader.AddLocalization(blockRedstoneHardenedTorchActive.getBlockName() + ".highSpeed.name", "High Speed Redstone Torch");
        ModLoader.AddName(blockCheat, "Cheat Block");


        ModLoader.RegisterTileEntity(TileEntityRedstoneClock.class, "RedstoneClock");
        ModLoader.RegisterTileEntity(TileEntityLightSensor.class, "RedstoneLightSensor");


        registerRecipes();
    }

    private void registerRecipes() {
        ModLoader.AddRecipe(new ItemStack(blockRedstoneLogicGateNOTIdle, 1), new Object[] {
                "_OI", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneLogicGateANDIdle, 1), new Object[] {
                "I_I", "OOO", " I ", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });

        ModLoader.AddShapelessRecipe(new ItemStack(blockRedstoneLogicGateNANDIdle, 1), new Object[] {
                blockRedstoneLogicGateNOTIdle, blockRedstoneLogicGateANDIdle
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneLogicGateORIdle, 1), new Object[] {
                " I ", "_O_", " _ ", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });

        ModLoader.AddShapelessRecipe(new ItemStack(blockRedstoneLogicGateNORIdle, 1), new Object[] {
                blockRedstoneLogicGateNOTIdle, blockRedstoneLogicGateORIdle
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneLogicGateXORIdle, 1), new Object[] {
                "O N", "_A_", " _ ", '_', Item.redstone, 'O', blockRedstoneLogicGateORIdle, 'N', blockRedstoneLogicGateNANDIdle, 'A', blockRedstoneLogicGateANDIdle
        });

        ModLoader.AddShapelessRecipe(new ItemStack(blockRedstoneLogicGateXNORIdle, 1), new Object[] {
                blockRedstoneLogicGateNOTIdle, blockRedstoneLogicGateXORIdle
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneClock, 1), new Object[] {
                " I ", "IOI", " I ", 'O', Block.stone, 'I', Block.torchRedstoneActive
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneLightSensor, 1), new Object[] {
                "###", "_X_", "OOO", '_', Item.redstone, '#', Block.glass, 'X', new ItemStack(Item.dyePowder, 1, 4), 'O', Block.stone
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneRSNORLatch, 1), new Object[] {
                "__O", "I I", "O__", '_', Item.redstone, 'I', Block.torchRedstoneActive, 'O', Block.stone
        });
        ModLoader.AddRecipe(new ItemStack(blockRedstoneRSNORLatch, 1), new Object[] {
                "_IO", "_ _", "OI_", '_', Item.redstone, 'I', Block.torchRedstoneActive, 'O', Block.stone
        });
        ModLoader.AddRecipe(new ItemStack(blockRedstoneRSNORLatch, 1), new Object[] {
                "O__", "I I", "__O", '_', Item.redstone, 'I', Block.torchRedstoneActive, 'O', Block.stone
        });
        ModLoader.AddRecipe(new ItemStack(blockRedstoneRSNORLatch, 1), new Object[] {
                "OI_", "_ _", "_IO", '_', Item.redstone, 'I', Block.torchRedstoneActive, 'O', Block.stone
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneLightBulbOff, 1), new Object[] {
                " # ", "#_#", " / ", '#', Block.glass, '_', Item.redstone, '/', Item.stick
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneDFlipFlop, 1), new Object[] {
                " L ", "_R_", '_', Item.redstone, 'L', Block.lever, 'R', blockRedstoneRSNORLatch
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneTFlipFlop, 1), new Object[] {
                " B ", "_R_", '_', Item.redstone, 'B', Block.button, 'R', blockRedstoneRSNORLatch
        });

        ModLoader.AddShapelessRecipe(new ItemStack(blockRedstoneJKFlipFlop, 1), new Object[] {
                blockRedstoneTFlipFlop, blockRedstoneRSNORLatch
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneRandom, 1), new Object[] {
                " I ", "I_I", " I ", '_', Item.redstone, 'I', Block.torchRedstoneActive
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneHardenedTorchActive, 1, 0), new Object[] {
                "_", "/", '_', Item.redstone, '/', Item.ingotIron
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneHardenedTorchActive, 1, 1), new Object[] {
                " _ ", "_I_", '_', Item.redstone, 'I', new ItemStack(blockRedstoneHardenedTorchActive, 1, 0)
        });

        if (LoggingUtil.isDebug()) {
            ModLoader.AddRecipe(new ItemStack(blockCheat, 1), new Object[] {
                    "#", '#', Block.dirt
            });
        }
    }

    @Override
    public String Version() {
        return "1.6.6_01";
    }

    @Override
    public boolean RenderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelID) {
        if (modelID == renderStandardBlockWithOverlay)
            return Renderers.renderStandardBlockWithOverlay(renderBlocks, iBlockAccess, block, x, y, z);
        else
            return modelID == renderBlockTorchExtended && Renderers.renderBlockTorch(renderBlocks, iBlockAccess, block, x, y, z, BlockRedstoneLightBulb.positionInCeilingY);
    }

    @Override
    public void RenderInvBlock(RenderBlocks renderBlocks, Block block, int metadata, int renderType) {
        if (renderType == renderStandardBlockWithOverlay) {
            Renderers.renderStandardBlockWithOverlayInv(renderBlocks, block, metadata);
        }
    }

    public static mod_redstoneExtended getInstance() {
        return instance;
    }
}
