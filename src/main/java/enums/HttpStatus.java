package enums;

public enum HttpStatus {
    OK("200", "OK"),
    NOT_FOUND("404", "Not Found"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),;

    private final String responseCode;

    private final String responseCodeString;

    HttpStatus(String responseCode, String responseCodeString) {
        this.responseCode = responseCode;
        this.responseCodeString = responseCodeString;
    }

    public String getResponseCode() {
        return responseCode;
    }
    public String getResponseCodeString() {
        return responseCodeString;
    }
}
