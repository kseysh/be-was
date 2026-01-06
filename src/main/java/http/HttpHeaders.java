package http;

import enums.HttpHeader;
import java.util.Map;
import java.util.StringJoiner;

public class HttpHeaders {

    private static final String CRLF = "\r\n";
    private static final String REQUEST_HEADER_FORMAT = "%s: %s" + CRLF;
    private static final String VALUE_DELIMITER = ",";

    private final Map<String, String[]> headers;

    public HttpHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }

    public int getContentLength() {
        String[] values = headers.get(HttpHeader.CONTENT_LENGTH.getValue());
        if (values == null || values.length == 0) {
            return 0;
        }
        return Integer.parseInt(values[0]);
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> header : headers.entrySet()) {
            String[] values = header.getValue();
            StringJoiner sj = new StringJoiner(VALUE_DELIMITER);

            for (String value : values) {
                sj.add(value);
            }

            sb.append(REQUEST_HEADER_FORMAT.formatted(header.getKey(), sj.toString()));
        }
        return sb.toString();
    }
}
