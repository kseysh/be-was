package exception;

import enums.HttpStatus;

public class UnauthorizedException extends HttpException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
