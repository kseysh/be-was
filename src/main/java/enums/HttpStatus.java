package enums;

public enum HttpStatus {

    // 2XX Response
    OK("200", "OK"),
    NO_CONTENT("204", "No Content"),

    // 3XX Response
    FOUND("302", "Found"),

    // 4XX Response
    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    NOT_ACCEPTABLE("406", "Not Acceptable"),
    UNSUPPORTED_MEDIA_TYPE("415", "Unsupported Media Type"),

    // 5XX Response
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    ;

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
