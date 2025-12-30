package exception;

import enums.HttpStatus;

public class ErrorException extends RuntimeException{
    private final HttpStatus status;
    private final String message;

    public ErrorException(HttpStatus status, String message) {
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
