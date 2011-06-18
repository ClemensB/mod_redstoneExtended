package net.minecraft.src.util;

import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import sun.reflect.Reflection;

import java.util.HashMap;

public class TextureManager {
    private static TextureManager instance;

    private final HashMap<String, HashMap<String, Integer>> loadedTextures;

    public final int emptyTexture;

    private TextureManager() {
        loadedTextures = new HashMap<String, HashMap<String, Integer>>();

        emptyTexture = getTerrainTexture("/empty.png");
    }

    public static TextureManager getInstance() {
        if (instance == null)
            instance = new TextureManager();

        return instance;
    }

    public int getTerrainTexture(String path) {
        return getTexture("/terrain.png", path);
    }

    public int getItemTexture(String path) {
        return getTexture("/gui/items.png", path);
    }

    public int getTexture(String file, String path) {
        String prefix;

        Class callerClass = Reflection.getCallerClass(2);
        if (callerClass.getSimpleName().equals("TextureManager"))
            callerClass = Reflection.getCallerClass(3);

        String callerClassName = callerClass.getSimpleName();
        Package callerClassPackage = callerClass.getPackage();

        if (callerClassPackage != null && !callerClassPackage.getName().equals("net.minecraft.src")) {
            String callerPackageName = callerClass.getPackage().getName();

            if (callerPackageName.startsWith("net.minecraft.src."))
                callerPackageName = callerPackageName.substring(("net.minecraft.src.").length());

            prefix = "/" + callerPackageName;
        } else if (BaseMod.class.isAssignableFrom(callerClass) && callerClassName.startsWith("mod_") &&
                callerClassName.length() > 4) {
            prefix = "/" + callerClassName.substring(4);
        } else
            throw new IllegalCallerException("Caller must either be in an mod-unique package " +
                    "or be a BaseMod with the prefix mod_");

        if (!loadedTextures.containsKey(file))
            loadedTextures.put(file, new HashMap<String, Integer>());

        if (loadedTextures.get(file).containsKey(prefix + path)) {
            return loadedTextures.get(file).get(prefix + path);
        } else {
            int textureId = ModLoader.addOverride(file, prefix + path);
            loadedTextures.get(file).put(prefix + path, textureId);
            return textureId;
        }
    }
}
