package dev.kcterala.tinyurl.exceptions;

public class ErrorResponse {
    public int status;
    public String errorMessage;

    public ErrorResponse(final int status, final String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
