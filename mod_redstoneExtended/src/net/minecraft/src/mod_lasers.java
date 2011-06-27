package net.minecraft.src;

import net.minecraft.src.lasers.*;
import net.minecraft.src.util.IdManager;
import net.minecraft.src.util.Renderers;

public class mod_lasers extends BaseMod {
    private static mod_lasers instance;


    public final Block blockLaserEmitter;
    public final Block blockLaser;
    public final Block blockLaserFocusLens;
    public final Block blockLaserMirror;


    public final int renderStandardBlockWithOverlay;

    public mod_lasers() {
        instance = this;

        renderStandardBlockWithOverlay = ModLoader.getUniqueBlockModelID(this, true);

        blockLaser = (new BlockLaser(IdManager.getInstance().getId("laser", IdManager.IdType.Block))).setHardness(-1.0F).setResistance(6000000F).setLightValue(0.625F).setStepSound(Block.soundGlassFootstep).setBlockName("laser");
        blockLaserEmitter = (new BlockLaserEmitter(IdManager.getInstance().getId("laserEmitter", IdManager.IdType.Block))).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setBlockName("laserEmitter");
        blockLaserFocusLens = (new BlockLaserFocusLens(IdManager.getInstance().getId("laserFocusLens", IdManager.IdType.Block))).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setBlockName("laserFocusLens");
        blockLaserMirror = (new BlockLaserMirror(IdManager.getInstance().getId("laserMirror", IdManager.IdType.Block))).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setBlockName("laserMirror");

        ModLoader.RegisterBlock(blockLaserEmitter);
        ModLoader.RegisterBlock(blockLaser);
        ModLoader.RegisterBlock(blockLaserFocusLens);
        ModLoader.RegisterBlock(blockLaserMirror);

        ModLoader.AddName(blockLaserEmitter, "Laser Emitter");
        ModLoader.AddName(blockLaserFocusLens, "Laser Focus Lens");
        ModLoader.AddName(blockLaserMirror, "Laser Mirror");

        ModLoader.RegisterTileEntity(TileEntityLaser.class, "Laser");
        ModLoader.RegisterTileEntity(TileEntityLaserEmitter.class, "LaserEmitter");
        ModLoader.RegisterTileEntity(TileEntityLaserFocusLens.class, "LaserFocusLens");
        ModLoader.RegisterTileEntity(TileEntityLaserMirror.class, "LaserMirror");

        registerRecipes();
    }

    private void registerRecipes() {
        ModLoader.AddRecipe(new ItemStack(blockLaserEmitter, 1),
                "OOO", "O_#", "OOO", 'O', Block.stone, '_', Item.redstone, '#', Block.glass
        );

        ModLoader.AddRecipe(new ItemStack(blockLaserFocusLens, 1),
                "OOO", "###", "OOO", 'O', Block.stone, '#', Block.glass
        );

        ModLoader.AddRecipe(new ItemStack(blockLaserMirror, 1),
                "#OO", "###", "#OO", 'O', Block.stone, '#', Block.glass
        );
    }

    @Override
    public String Version() {
        return "1.6.6_01";
    }

    @Override
    public boolean RenderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelID) {
        return modelID == renderStandardBlockWithOverlay &&
                Renderers.renderStandardBlockWithOverlay(renderBlocks, iBlockAccess, block, x, y, z);
    }

    @Override
    public void RenderInvBlock(RenderBlocks renderBlocks, Block block, int metadata, int renderType) {
        if (renderType == renderStandardBlockWithOverlay) {
            Renderers.renderStandardBlockWithOverlayInv(renderBlocks, block, metadata);
        }
    }

    public static mod_lasers getInstance() {
        return instance;
    }
}
