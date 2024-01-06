package dev.kcterala.tinyurl.exceptions;

public class AppException extends Exception{
    public final int statusCode;
    public AppException(final int statusCode, final String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
