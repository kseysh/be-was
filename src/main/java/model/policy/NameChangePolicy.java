package model.policy;

import db.UserDatabase;
import db.config.DatabaseConfig;
import exception.BadRequestException;

public class NameChangePolicy {

    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 20;
    private static final UserDatabase userDatabase = DatabaseConfig.userDatabase;

    public void validate(String prevName, String newName) {
        if (newName == null || newName.isEmpty()) {
            throw new BadRequestException("Nickname cannot be null or empty");
        }
        if (newName.length() < MIN_LENGTH || newName.length() > MAX_LENGTH) {
            throw new BadRequestException("Nickname must be between 4 and 20 characters");
        }

        if(prevName.equals(newName)) return;
        if(userDatabase.findByName(newName).isPresent()){
            throw new BadRequestException("Nickname already in use: " + newName);
        }
    }
}
