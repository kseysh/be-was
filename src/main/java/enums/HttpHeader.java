package enums;

public enum HttpHeader {
    CONTENT_LENGTH("Content-Length");

    private String value;

    HttpHeader(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
