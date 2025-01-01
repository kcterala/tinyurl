package dev.kcterala.tinyurl.exceptions;

public class UrlNotValidException extends AppException {

    public UrlNotValidException() {
        super(404, "Invalid URL passed. Please check");
    }
}
