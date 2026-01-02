package enums;

public enum HttpStatus {

    // 2XX Response
    OK("200", "OK"),

    // 3XX Response
    FOUND("302", "Found"),

    // 4XX Response
    BAD_REQUEST("400", "Bad Request"),
    NOT_FOUND("404", "Not Found"),

    // 5XX Response
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
