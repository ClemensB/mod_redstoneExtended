package net.minecraft.src.util;

public class LoggingUtil {
    public static void logInfo(String text) {
        System.out.println("[RedstoneExtended] " + text);
    }

    public static void logError(String text) {
        String location = Thread.currentThread().getStackTrace()[2].toString();
        System.out.println("[RedstoneExtended][ERROR] at " + location + "\n\t" + text);
    }

    public static void logDebug(String text) {
        if (isDebug())
            System.out.println("[RedstoneExtended][DEBUG] " + text);
    }

    public static boolean isDebug() {
        return System.getenv().containsKey("mcDebug");
    }
}
