package exception;

import enums.HttpStatus;

public class MethodNotAllowedException extends HttpException {

    public MethodNotAllowedException(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }
}
