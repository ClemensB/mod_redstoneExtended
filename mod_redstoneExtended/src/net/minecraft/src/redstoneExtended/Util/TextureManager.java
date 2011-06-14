package net.minecraft.src.redstoneExtended.Util;

import net.minecraft.src.ModLoader;

import java.util.HashMap;

public class TextureManager {
    private static TextureManager instance;

    private String prefix;

    private HashMap<String, Integer> loadedTerrainTextures;
    private HashMap<String, Integer> loadedItemTextures;

    public final int emptyTexture;

    public TextureManager() {
        prefix = "/redstoneExtended";

        loadedTerrainTextures = new HashMap<String, Integer>();
        loadedItemTextures = new HashMap<String, Integer>();

        emptyTexture = getTerrainTexture("/empty.png");
    }

    public static TextureManager getInstance() {
        if (instance == null)
            instance = new TextureManager();

        return instance;
    }

    public int getTerrainTexture(String path) {
        if (loadedTerrainTextures.containsKey(prefix + path)) {
            return loadedTerrainTextures.get(prefix + path);
        } else {
            int textureId = ModLoader.addOverride("/terrain.png", prefix + path);
            loadedTerrainTextures.put(prefix + path, textureId);
            return textureId;
        }
    }

    public int getItemTexture(String path) {
        if (loadedItemTextures.containsKey(prefix + path)) {
            return loadedItemTextures.get(prefix + path);
        } else {
            int textureId = ModLoader.addOverride("/gui/items.png", prefix + path);
            loadedItemTextures.put(prefix + path, textureId);
            return textureId;
        }
    }
}
