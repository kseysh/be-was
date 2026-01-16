package model.policy;

import exception.BadRequestException;

public class PasswordChangePolicy {

    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 20;

    public void validate(String password) {
        if (password == null || password.isEmpty()) {
            throw new BadRequestException("Nickname cannot be null or empty");
        }
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new BadRequestException("Nickname must be between 3 and 20 characters");
        }
    }
}
