package exception;

import enums.HttpStatus;

public class HttpException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public HttpException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
