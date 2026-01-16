package model.policy;

import db.UserDatabase;
import db.config.DatabaseConfig;
import exception.BadRequestException;

public class NameChangePolicy {

    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 20;
    private static final UserDatabase userDatabase = DatabaseConfig.userDatabase;

    public void validate(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            throw new BadRequestException("Nickname cannot be null or empty");
        }
        if (nickname.length() < MIN_LENGTH || nickname.length() > MAX_LENGTH) {
            throw new BadRequestException("Nickname must be between 4 and 20 characters");
        }
        if(userDatabase.findByName(nickname).isPresent()){
            throw new BadRequestException("Nickname already in use: " + nickname);
        }
    }
}
