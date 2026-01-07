package exception;

import enums.HttpStatus;

public class UnsupportedMediaTypeException extends HttpException {

    public UnsupportedMediaTypeException(String message) {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
    }
}
