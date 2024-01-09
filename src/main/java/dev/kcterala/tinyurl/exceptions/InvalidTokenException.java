package dev.kcterala.tinyurl.exceptions;

public class InvalidTokenException extends AppException{
    public InvalidTokenException() {
        super(401, "Session Token Missing or Invalid");
    }
}
