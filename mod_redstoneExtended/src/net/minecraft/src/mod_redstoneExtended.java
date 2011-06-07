package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.redstoneExtended.*;

import java.io.*;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public final Block blockLaserEmitter;
    public final Block blockLaser;
    public final Block blockLaserFocusLens;
    public final Block blockLaserMirror;
    public final Block blockCheat;


    public final Item itemRedstoneLogicGateNOT;
    public final Item itemRedstoneLogicGateAND;
    public final Item itemRedstoneLogicGateNAND;
    public final Item itemRedstoneLogicGateOR;
    public final Item itemRedstoneLogicGateNOR;
    public final Item itemRedstoneLogicGateXOR;
    public final Item itemRedstoneLogicGateXNOR;
    public final Item itemRedstoneClock;
    public final Item itemRedstoneLightSensor;
    public final Item itemRedstoneRSNORLatch;
    public final Item itemRedstoneDFlipFlop;
    public final Item itemRedstoneTFlipFlop;
    public final Item itemRedstoneJKFlipFlop;
    public final Item itemRedstoneRandom;


    public final int renderBlockRedstoneLogicGate;
    public final int renderBlockRedstoneClock;
    public final int renderBlockRedstoneLightSensor;
    public final int renderBlockRedstoneFlipFlop;
    public final int renderBlockTorchExtended;

    public final int emptyTexture = ModLoader.addOverride("/terrain.png", "/redstoneExtended/empty.png");

    private LinkedList<Integer> reservedIds;

    private int getBlockOrItemId(String name, boolean isItem) {
        File configDir = new File(Minecraft.getMinecraftDir(), "/mods/mod_redstoneExtended/");
        if (configDir.exists() || configDir.mkdir()) {
            File configFile = new File(configDir, "config.properties");

            if (!configFile.exists()) {
                try {
                    if (!configFile.createNewFile())
                        log("Config file has not been created");
                } catch (IOException e) {
                    log("Couldn't create config file: " + e.getMessage());
                }
            }

            Properties config = new Properties();

            try {
                config.load(new FileInputStream(configFile));
            } catch (FileNotFoundException e) {
                log("Couldn't load config file: Config file not found");
                return getFirstFreeBlock();
            } catch (IOException e) {
                log("Couldn't load config file: " + e.getMessage());
                return getFirstFreeBlock();
            }

            if (reservedIds == null) {
                logDebug("Initializing reserved id list");
                reservedIds = new LinkedList<Integer>();
                Pattern pattern = Pattern.compile("^Id\\.(item|block)\\.[a-zA-Z0-9]+$");
                for (String propertyName : config.stringPropertyNames()) {
                    Matcher matcher = pattern.matcher(propertyName);
                    if (matcher.matches()) {
                        String stringReservedId = config.getProperty(propertyName);
                        int reservedId = Integer.parseInt(stringReservedId);
                        reservedIds.add(reservedId);
                    }
                }
            }

            String propertyName = "Id." + (isItem ? "item" : "block") + "." + name;
            String stringId = config.getProperty(propertyName);
            int Id;

            if (stringId == null) {
                Id = isItem ? getFirstFreeItem() : getFirstFreeBlock();
                stringId = Integer.toString(Id);
                config.setProperty(propertyName, stringId);
                logDebug("Assigned Id " + stringId + " to " + name);

                try {
                    config.store(new FileOutputStream(configFile), null);
                } catch (FileNotFoundException e) {
                    log("Couldn't save config file: Config file not found");
                } catch (IOException e) {
                    log("Couldn't save config file: " + e.getMessage());
                }
            } else {
                Id = Integer.parseInt(stringId);

                if (!isItem && Block.blocksList[Id] != null) {
                    int altId = getFirstFreeBlock();
                    String stringAltId = Integer.toString(altId);
                    config.setProperty(propertyName, stringAltId);
                    log("Conflict detected: Id " + stringId + " already in use. Now using Id " + stringAltId + " for " + name);
                    Id = altId;

                    try {
                        config.store(new FileOutputStream(configFile), null);
                    } catch (FileNotFoundException e) {
                        log("Couldn't save config file: Config file not found");
                    } catch (IOException e) {
                        log("Couldn't save config file: " + e.getMessage());
                    }
                }

                logDebug("Using Id " + stringId + " for " + name);
            }

            return Id;
        }

        log("Couldn't create/access config directory, using dynamic Id");
        return getFirstFreeBlock();
    }

    private int getFirstFreeBlock() {
        for (int i = Block.blocksList.length - 1; i >= 0; --i) {
            if ((Block.blocksList[i] == null) && !reservedIds.contains(i))
                return i;
        }

        return -1;
    }

    private int getFirstFreeItem() {
        for (int i = Item.itemsList.length - 1; i >= 0; --i) {
            if ((Item.itemsList[i] == null) && !reservedIds.contains(i))
                return i - 256;
        }

        return -1;
    }

    boolean isDebug() {
        return System.getenv().containsKey("mcDebug");
    }

    public void log(String message) {
        System.out.println("[redstoneExtended] " + message);
    }

    public void logDebug(String message) {
        if (isDebug())
            System.out.println("[redstoneExtended][Debug] " + message);
    }

    public mod_redstoneExtended() {
        instance = this;

        renderBlockRedstoneLogicGate = ModLoader.getUniqueBlockModelID(this, false);
        renderBlockRedstoneClock = ModLoader.getUniqueBlockModelID(this, false);
        renderBlockRedstoneLightSensor = ModLoader.getUniqueBlockModelID(this, false);
        renderBlockRedstoneFlipFlop = ModLoader.getUniqueBlockModelID(this, false);
        renderBlockTorchExtended = ModLoader.getUniqueBlockModelID(this, false);


        blockRedstoneLogicGateNOTIdle = (new BlockRedstoneLogicGateNOT(getBlockOrItemId("logicGateNOTIdle", false), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOT");
        blockRedstoneLogicGateNOTActive = (new BlockRedstoneLogicGateNOT(getBlockOrItemId("logicGateNOTActive", false), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOT");
        blockRedstoneLogicGateANDIdle = (new BlockRedstoneLogicGateAND(getBlockOrItemId("logicGateANDIdle", false), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateAND");
        blockRedstoneLogicGateANDActive = (new BlockRedstoneLogicGateAND(getBlockOrItemId("logicGateANDActive", false), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateAND");
        blockRedstoneLogicGateNANDIdle = (new BlockRedstoneLogicGateNAND(getBlockOrItemId("logicGateNANDIdle", false), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNAND");
        blockRedstoneLogicGateNANDActive = (new BlockRedstoneLogicGateNAND(getBlockOrItemId("logicGateNANDActive", false), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNAND");
        blockRedstoneLogicGateORIdle = (new BlockRedstoneLogicGateOR(getBlockOrItemId("logicGateORIdle", false), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateOR");
        blockRedstoneLogicGateORActive = (new BlockRedstoneLogicGateOR(getBlockOrItemId("logicGateORActive", false), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateOR");
        blockRedstoneLogicGateNORIdle = (new BlockRedstoneLogicGateNOR(getBlockOrItemId("logicGateNORIdle", false), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOR");
        blockRedstoneLogicGateNORActive = (new BlockRedstoneLogicGateNOR(getBlockOrItemId("logicGateNORActive", false), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateNOR");
        blockRedstoneLogicGateXORIdle = (new BlockRedstoneLogicGateXOR(getBlockOrItemId("logicGateXORIdle", false), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXOR");
        blockRedstoneLogicGateXORActive = (new BlockRedstoneLogicGateXOR(getBlockOrItemId("logicGateXORActive", false), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXOR");
        blockRedstoneLogicGateXNORIdle = (new BlockRedstoneLogicGateXNOR(getBlockOrItemId("logicGateXNORIdle", false), false)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXNOR");
        blockRedstoneLogicGateXNORActive = (new BlockRedstoneLogicGateXNOR(getBlockOrItemId("logicGateXNORActive", false), true)).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("logicGateXNOR");
        blockRedstoneClock = (new BlockRedstoneClock(getBlockOrItemId("redstoneClock", false))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneClock");
        blockRedstoneLightSensor = (new BlockRedstoneLightSensor(getBlockOrItemId("redstoneLightSensor", false))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneLightSensor");
        blockRedstoneRSNORLatch = (new BlockRedstoneRSNORLatch(getBlockOrItemId("redstoneRSNORLatch", false))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneRSNORLatch");
        blockRedstoneLightBulbOn = (new BlockRedstoneLightBulb(getBlockOrItemId("lightBulbOn", false), true)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(Block.soundWoodFootstep).setBlockName("lightBulbOn");
        blockRedstoneLightBulbOff = (new BlockRedstoneLightBulb(getBlockOrItemId("lightBulbOff", false), false)).setHardness(0.0F).setLightValue(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("lightBulbOff");
        blockRedstoneDFlipFlop = (new BlockRedstoneDFlipFlop(getBlockOrItemId("redstoneDFlipFlop", false))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneDFlipFlop");
        blockRedstoneTFlipFlop = (new BlockRedstoneTFlipFlop(getBlockOrItemId("redstoneTFlipFlop", false))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneTFlipFlop");
        blockRedstoneJKFlipFlop = (new BlockRedstoneJKFlipFlop(getBlockOrItemId("redstoneJKFlipFlop", false))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneJKFlipFlop");
        blockRedstoneRandom = (new BlockRedstoneRandom(getBlockOrItemId("redstoneRandom", false))).setHardness(0.0F).setStepSound(Block.soundStoneFootstep).setBlockName("redstoneRandom");
        blockRedstoneHardenedTorchIdle = (new BlockRedstoneHardenedTorch(getBlockOrItemId("redstoneHardenedTorchIdle", false), ModLoader.addOverride("/terrain.png", "/redstoneExtended/hardenedTorch/idle.png"), false)).setHardness(0.0F).setStepSound(Block.soundMetalFootstep).setBlockName("redstoneHardenedTorch");
        blockRedstoneHardenedTorchActive = (new BlockRedstoneHardenedTorch(getBlockOrItemId("redstoneHardenedTorchActive", false), ModLoader.addOverride("/terrain.png", "/redstoneExtended/hardenedTorch/active.png"), true)).setHardness(0.0F).setLightValue(0.5F).setStepSound(Block.soundMetalFootstep).setBlockName("redstoneHardenedTorch");
        blockLaser = (new BlockLaser(getBlockOrItemId("laser", false))).setHardness(-1.0F).setResistance(6000000F).setLightValue(0.625F).setStepSound(Block.soundGlassFootstep).setBlockName("laser");
        blockLaserEmitter = (new BlockLaserEmitter(getBlockOrItemId("laserEmitter", false))).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setBlockName("laserEmitter");
        blockLaserFocusLens = (new BlockLaserFocusLens(getBlockOrItemId("laserFocusLens", false))).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setBlockName("laserFocusLens");
        blockLaserMirror = (new BlockLaserMirror(getBlockOrItemId("laserMirror", false))).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setBlockName("laserMirror");
        blockCheat = (new BlockCheat(getBlockOrItemId("cheatBlock", false))).setHardness(0.0F).setStepSound(Block.soundMetalFootstep).setBlockName("cheatBlock");


        itemRedstoneLogicGateNOT = (new ItemReed(getBlockOrItemId("logicGateNOT", true), blockRedstoneLogicGateNOTIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/NOT/icon.png")).setItemName("logicGateNOT");
        itemRedstoneLogicGateAND = (new ItemReed(getBlockOrItemId("logicGateAND", true), blockRedstoneLogicGateANDIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/AND/icon.png")).setItemName("logicGateAND");
        itemRedstoneLogicGateNAND = (new ItemReed(getBlockOrItemId("logicGateNAND", true), blockRedstoneLogicGateNANDIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/NAND/icon.png")).setItemName("logicGateNAND");
        itemRedstoneLogicGateOR = (new ItemReed(getBlockOrItemId("logicGateOR", true), blockRedstoneLogicGateORIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/OR/icon.png")).setItemName("logicGateOR");
        itemRedstoneLogicGateNOR = (new ItemReed(getBlockOrItemId("logicGateNOR", true), blockRedstoneLogicGateNORIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/NOR/icon.png")).setItemName("logicGateNOR");
        itemRedstoneLogicGateXOR = (new ItemReed(getBlockOrItemId("logicGateXOR", true), blockRedstoneLogicGateXORIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/XOR/icon.png")).setItemName("logicGateXOR");
        itemRedstoneLogicGateXNOR = (new ItemReed(getBlockOrItemId("logicGateXNOR", true), blockRedstoneLogicGateXNORIdle)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/logicGates/XNOR/icon.png")).setItemName("logicGateXNOR");
        itemRedstoneClock = (new ItemReed(getBlockOrItemId("redstoneClock", true), blockRedstoneClock)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/clock/icon.png")).setItemName("redstoneClock");
        itemRedstoneLightSensor = (new ItemReed(getBlockOrItemId("redstoneLightSensor", true), blockRedstoneLightSensor)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/lightSensor/icon.png")).setItemName("redstoneLightSensor");
        itemRedstoneRSNORLatch = (new ItemReed(getBlockOrItemId("redstoneRSNORLatch", true), blockRedstoneRSNORLatch)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/RSNORLatch/icon.png")).setItemName("redstoneRSNORLatch");
        itemRedstoneDFlipFlop = (new ItemReed(getBlockOrItemId("redstoneDFlipFlop", true), blockRedstoneDFlipFlop)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/DFlipFlop/icon.png")).setItemName("redstoneDFlipFlop");
        itemRedstoneTFlipFlop = (new ItemReed(getBlockOrItemId("redstoneTFlipFlop", true), blockRedstoneTFlipFlop)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/TFlipFlop/icon.png")).setItemName("redstoneTFlipFlop");
        itemRedstoneJKFlipFlop = (new ItemReed(getBlockOrItemId("redstoneJKFlipFlop", true), blockRedstoneJKFlipFlop)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/JKFlipFlop/icon.png")).setItemName("redstoneJKFlipFlop");
        itemRedstoneRandom = (new ItemReed(getBlockOrItemId("redstoneRandom", true), blockRedstoneRandom)).setIconIndex(ModLoader.addOverride("/gui/items.png", "/redstoneExtended/flipFlops/Random/icon.png")).setItemName("redstoneRandom");


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
        ModLoader.RegisterBlock(blockLaserEmitter);
        ModLoader.RegisterBlock(blockLaser);
        ModLoader.RegisterBlock(blockLaserFocusLens);
        ModLoader.RegisterBlock(blockLaserMirror);
        ModLoader.RegisterBlock(blockCheat);


        ModLoader.AddName(itemRedstoneLogicGateNOT, "NOT Gate");
        ModLoader.AddName(itemRedstoneLogicGateAND, "AND Gate");
        ModLoader.AddName(itemRedstoneLogicGateNAND, "NAND Gate");
        ModLoader.AddName(itemRedstoneLogicGateOR, "OR Gate");
        ModLoader.AddName(itemRedstoneLogicGateNOR, "NOR Gate");
        ModLoader.AddName(itemRedstoneLogicGateXOR, "XOR Gate");
        ModLoader.AddName(itemRedstoneLogicGateXNOR, "XNOR Gate");
        ModLoader.AddName(itemRedstoneClock, "Redstone Clock");
        ModLoader.AddName(itemRedstoneLightSensor, "Light Sensor");
        ModLoader.AddName(itemRedstoneRSNORLatch, "RS NOR Latch");
        ModLoader.AddName(blockRedstoneLightBulbOff, "Light Bulb");
        ModLoader.AddName(itemRedstoneDFlipFlop, "D flip-flop");
        ModLoader.AddName(itemRedstoneTFlipFlop, "T flip-flop");
        ModLoader.AddName(itemRedstoneJKFlipFlop, "JK flip-flop");
        ModLoader.AddName(itemRedstoneRandom, "Random Number Generator");
        ModLoader.AddLocalization(blockRedstoneHardenedTorchActive.getBlockName() + ".hardened.name", "Hardened Redstone Torch");
        ModLoader.AddLocalization(blockRedstoneHardenedTorchActive.getBlockName() + ".highSpeed.name", "High Speed Redstone Torch");
        ModLoader.AddName(blockLaserEmitter, "Laser Emitter");
        ModLoader.AddName(blockLaserFocusLens, "Laser Focus Lens");
        ModLoader.AddName(blockLaserMirror, "Laser Mirror");
        ModLoader.AddName(blockCheat, "Cheat Block");


        ModLoader.RegisterTileEntity(TileEntityRedstoneClock.class, "RedstoneClock");
        ModLoader.RegisterTileEntity(TileEntityLightSensor.class, "RedstoneLightSensor");
        ModLoader.RegisterTileEntity(TileEntityLaser.class, "Laser");
        ModLoader.RegisterTileEntity(TileEntityLaserEmitter.class, "LaserEmitter");
        ModLoader.RegisterTileEntity(TileEntityLaserFocusLens.class, "LaserFocusLens");
        ModLoader.RegisterTileEntity(TileEntityLaserMirror.class, "LaserMirror");

        registerRecipes();
    }

    private void registerRecipes() {
        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateNOT, 1), new Object[] {
                "_OI", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateAND, 1), new Object[] {
                "I_I", "OOO", " I ", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });

        ModLoader.AddShapelessRecipe(new ItemStack(itemRedstoneLogicGateNAND, 1), new Object[] {
                itemRedstoneLogicGateNOT, itemRedstoneLogicGateAND
        });

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateOR, 1), new Object[] {
                " I ", "_O_", " _ ", 'I', Block.torchRedstoneActive, '_', Item.redstone, 'O', Block.stone
        });

        ModLoader.AddShapelessRecipe(new ItemStack(itemRedstoneLogicGateNOR, 1), new Object[] {
                itemRedstoneLogicGateNOT, itemRedstoneLogicGateOR
        });

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLogicGateXOR, 1), new Object[] {
                "O N", "_A_", " _ ", '_', Item.redstone, 'O', itemRedstoneLogicGateOR, 'N', itemRedstoneLogicGateNAND, 'A', itemRedstoneLogicGateAND
        });

        ModLoader.AddShapelessRecipe(new ItemStack(itemRedstoneLogicGateXNOR, 1), new Object[] {
                itemRedstoneLogicGateNOT, itemRedstoneLogicGateXOR
        });

        ModLoader.AddRecipe(new ItemStack(itemRedstoneClock, 1), new Object[] {
                " I ", "IOI", " I ", 'O', Block.stone, 'I', Block.torchRedstoneActive
        });

        ModLoader.AddRecipe(new ItemStack(itemRedstoneLightSensor, 1), new Object[] {
                "###", "_X_", "OOO", '_', Item.redstone, '#', Block.glass, 'X', new ItemStack(Item.dyePowder, 1, 4), 'O', Block.stone
        });

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

        ModLoader.AddRecipe(new ItemStack(blockRedstoneLightBulbOff, 1), new Object[] {
                " # ", "#_#", " / ", '#', Block.glass, '_', Item.redstone, '/', Item.stick
        });

        ModLoader.AddRecipe(new ItemStack(itemRedstoneDFlipFlop, 1), new Object[] {
                " L ", "_R_", '_', Item.redstone, 'L', Block.lever, 'R', itemRedstoneRSNORLatch
        });

        ModLoader.AddRecipe(new ItemStack(itemRedstoneTFlipFlop, 1), new Object[] {
                " B ", "_R_", '_', Item.redstone, 'B', Block.button, 'R', itemRedstoneRSNORLatch
        });

        ModLoader.AddShapelessRecipe(new ItemStack(itemRedstoneJKFlipFlop, 1), new Object[] {
                itemRedstoneTFlipFlop, itemRedstoneRSNORLatch
        });

        ModLoader.AddRecipe(new ItemStack(itemRedstoneRandom, 1), new Object[] {
                " I ", "I_I", " I ", '_', Item.redstone, 'I', Block.torchRedstoneActive
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneHardenedTorchActive, 1, 0), new Object[] {
                "_", "/", '_', Item.redstone, '/', Item.ingotIron
        });

        ModLoader.AddRecipe(new ItemStack(blockRedstoneHardenedTorchActive, 1, 1), new Object[] {
                " _ ", "_I_", '_', Item.redstone, 'I', new ItemStack(blockRedstoneHardenedTorchActive, 1, 0)
        });

        ModLoader.AddRecipe(new ItemStack(blockLaserEmitter, 1), new Object[] {
                "OOO", "O_#", "OOO", 'O', Block.stone, '_', Item.redstone, '#', Block.glass
        });

        ModLoader.AddRecipe(new ItemStack(blockLaserFocusLens, 1), new Object[] {
                "OOO", "###", "OOO", 'O', Block.stone, '#', Block.glass
        });

        ModLoader.AddRecipe(new ItemStack(blockLaserMirror, 1), new Object[] {
                "#OO", "###", "#OO", 'O', Block.stone, '#', Block.glass
        });

        if (isDebug()) {
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
        if (modelID == renderBlockRedstoneLogicGate)
            return MyRenderBlocks.renderBlockRedstoneLogicGate(renderBlocks, iBlockAccess, block, x, y, z);
        else if (modelID == renderBlockRedstoneClock)
            return MyRenderBlocks.renderBlockRedstoneClock(renderBlocks, iBlockAccess, block, x, y, z);
        else if (modelID == renderBlockRedstoneLightSensor)
            return MyRenderBlocks.renderBlockRedstoneLightSensor(renderBlocks, iBlockAccess, block, x, y, z);
        else if (modelID == renderBlockRedstoneFlipFlop)
            return MyRenderBlocks.renderBlockRedstoneFlipFlop(renderBlocks, iBlockAccess, block, x, y, z);
        else
            return modelID == renderBlockTorchExtended && MyRenderBlocks.renderBlockTorch(renderBlocks, iBlockAccess, block, x, y, z);
    }

    public static mod_redstoneExtended getInstance() {
        return instance;
    }
}
