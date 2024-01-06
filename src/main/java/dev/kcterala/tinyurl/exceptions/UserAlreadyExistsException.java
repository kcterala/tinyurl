package dev.kcterala.tinyurl.exceptions;

public class UserAlreadyExistsException extends AppException{
    public UserAlreadyExistsException() {
        super(404, "User already exists, please try with another email");
    }
}
