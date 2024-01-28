package dev.kcterala.tinyurl.exceptions;

public class UrlNotFoundException extends AppException{
    public UrlNotFoundException() {
        super(404, "Uh oh, this url is not a valid one. Please check.");
    }
}
