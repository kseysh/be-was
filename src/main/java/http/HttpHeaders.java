package http;

import enums.HttpHeader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private static final String HEADER_DELIMITER = ":";
    private final Map<String, String> headers;

    public HttpHeaders(BufferedReader br) throws IOException {
        String line;
        headers = new HashMap<>();
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] pair = line.split(HEADER_DELIMITER, 2);
            if (pair.length == 2) {
                headers.put(pair[0].trim(), pair[1].trim());
            }
        }
    }

    public int getContentLength() {
        String length = headers.get(HttpHeader.CONTENT_LENGTH.getValue());
        return (length != null) ? Integer.parseInt(length) : 0;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(HEADER_DELIMITER).append(entry.getValue()).append('\n');
        }
        return sb.toString();
    }
}
