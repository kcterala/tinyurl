package dev.kcterala.tinyurl.exceptions;

public class UserNotFoundException extends AppException{
    public UserNotFoundException() {
        super(404, "User does not exist");
    }
}
