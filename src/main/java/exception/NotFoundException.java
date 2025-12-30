package exception;

import enums.HttpStatus;

public class NotFoundException extends ErrorException{
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
