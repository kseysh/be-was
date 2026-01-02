package http;

import enums.HttpHeader;
import java.util.Map;

public class HttpHeaders {
    private static final String HEADER_KEY_DELIMITER = ":";
    private static final String HEADER_VALUE_DELIMITER = ",";

    private final Map<String, String[]> headers;

    public HttpHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }

    public int getContentLength() {
        String[] values = headers.get(HttpHeader.CONTENT_LENGTH.getValue());
        if (values == null || values.length == 0) return 0;
        return Integer.parseInt(values[0]);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(HEADER_KEY_DELIMITER);

            String[] values = entry.getValue();
            for (int i = 0; i < values.length - 1; i++) {
                sb.append(values[i]).append(HEADER_VALUE_DELIMITER);
            }
            sb.append(values[values.length - 1]).append('\n');
        }
        return sb.toString();
    }
}
