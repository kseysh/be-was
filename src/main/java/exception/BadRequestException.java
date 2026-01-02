package exception;

import enums.HttpStatus;

public class BadRequestException extends HttpException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
