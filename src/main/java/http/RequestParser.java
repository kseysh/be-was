package http;

import enums.HttpMethod;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final String REQUEST_LINE_SPACE = " ";
    private static final Integer REQUEST_LINE_LENGTH = 3;
    private static final String QUERY_SEPARATOR = "?";
    private static final String PARAM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    private static final String HEADER_KEY_DELIMITER = ":";
    private static final String HEADER_VALUE_DELIMITER = ",";

    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    private final HttpRequestBody body;

    public RequestParser(BufferedReader br) throws IOException {
        this.requestLine = parseRequestLine(br);
        this.headers = parseHeaders(br);
        this.body = parseRequestBody(br, headers.getContentLength());
    }

    public HttpRequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpRequestBody getBody() {
        return body;
    }

    private HttpHeaders parseHeaders(BufferedReader br) throws IOException {
        String line;
        Map<String, String[]> headerMap = new HashMap<>();
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] pair = line.split(HEADER_KEY_DELIMITER, 2);
            if (pair.length == 2) {
                String headerKey = pair[0].trim();
                String[] headerValues = pair[1].trim().split(HEADER_VALUE_DELIMITER);
                headerMap.put(headerKey, headerValues);
            }
        }

        return new HttpHeaders(headerMap);
    }

    private HttpRequestLine parseRequestLine(BufferedReader br) throws IOException {
        String requestLineString = br.readLine();
        if (requestLineString == null || requestLineString.isEmpty()) {
            throw new IllegalArgumentException("Empty request line");
        }

        String[] parts = requestLineString.strip().split(REQUEST_LINE_SPACE);

        if (parts.length != REQUEST_LINE_LENGTH) {
            throw new IllegalArgumentException("Malformed request line: " + requestLineString);
        }

        HttpMethod method = HttpMethod.valueOf(parts[0]);
        String path = extractPath(parts[1]);
        String version = parts[2];
        Map<String, String> queries = extractQueries(parts[1]);

        return new HttpRequestLine(method, path, version, queries);
    }

    private static String extractPath(String fullPath) {
        int queryIndex = fullPath.indexOf(QUERY_SEPARATOR);
        return queryIndex == -1 ? fullPath : fullPath.substring(0, queryIndex);
    }

    private static Map<String, String> extractQueries(String fullPath) {
        int queryIndex = fullPath.indexOf(QUERY_SEPARATOR);
        if (queryIndex == -1) {
            return Collections.emptyMap();
        }

        String queryString = fullPath.substring(queryIndex + 1);
        String[] queryPairs = queryString.split(PARAM_SEPARATOR);
        Map<String, String> queryMap = new HashMap<>();

        for (String query : queryPairs) {
            String[] keyValue = query.split(KEY_VALUE_SEPARATOR, 2);
            if (keyValue.length < 2) {
                throw new IllegalArgumentException("Malformed query: " + query);
            }
            queryMap.put(keyValue[0], keyValue[1]);

        }
        return queryMap;
    }

    // TODO: body를 BufferedReader로 하는 것은 메모리 성능이 좋지 않고 성능상 비효율적임
    private HttpRequestBody parseRequestBody(BufferedReader br, int length) throws IOException {
        if (length == 0){
            return new HttpRequestBody(new byte[0]);
        }

        char[] buffer = new char[length];
        int totalRead = 0;
        while (totalRead < length) {
            int read = br.read(buffer, totalRead, length - totalRead);
            if (read == -1) break;
            totalRead += read;
        }

        return new HttpRequestBody(String.valueOf(buffer).getBytes());
    }
}
