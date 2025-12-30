package enums;

public enum HttpStatus {
    OK("200", "OK");

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
