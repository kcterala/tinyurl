package dev.kcterala.tinyurl.exceptions;

public class InvalidCredentialsException extends AppException{

    public InvalidCredentialsException() {
        super(401, "Invalid Credentials");
    }
}
