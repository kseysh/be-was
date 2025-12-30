package http;

import enums.HttpHeader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private static final String HEADER_KEY_DELIMITER = ":";
    private static final String HEADER_VALUE_DELIMITER = ",";
    private final Map<String, String[]> headers;

    public HttpHeaders(BufferedReader br) throws IOException {
        String line;
        headers = new HashMap<>();
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] pair = line.split(HEADER_KEY_DELIMITER, 2);
            if (pair.length == 2) {
                String headerKey = pair[0].trim();
                String[] headerValues = pair[1].trim().split(HEADER_VALUE_DELIMITER);
                headers.put(headerKey, headerValues);
            }
        }
    }

    public int getContentLength() {
        String length = headers.get(HttpHeader.CONTENT_LENGTH.getValue())[0];
        return (length != null) ? Integer.parseInt(length) : 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(HEADER_KEY_DELIMITER);

            String[] values = entry.getValue();
            for (int i = 0; i < values.length - 1; i++) {
                sb.append(values[i]).append(HEADER_VALUE_DELIMITER);
            }
            sb.append(values[values.length - 1]);
        }
        return sb.toString();
    }
}
