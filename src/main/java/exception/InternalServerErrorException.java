package exception;

import enums.HttpStatus;

public class InternalServerErrorException extends ErrorException {
    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
