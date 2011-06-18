package net.minecraft.src.util;

public class IllegalCallerException extends RuntimeException {
    public IllegalCallerException() {
        super();
    }

    public IllegalCallerException(String message) {
        super(message);
    }

    public IllegalCallerException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCallerException(Throwable cause) {
        super(cause);
    }
}
