package http.request;

import enums.HttpMethod;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import util.StreamUtils;

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

    public RequestParser(InputStream in) throws IOException {
        this.requestLine = parseRequestLine(in);
        this.headers = parseHeaders(in);
        this.body = parseRequestBody(in, headers.getContentLength());
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

    private HttpRequestLine parseRequestLine(InputStream in) throws IOException {
        String line = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Empty request line");
        }

        String[] parts = line.strip().split(REQUEST_LINE_SPACE);
        if (parts.length != REQUEST_LINE_LENGTH) {
            throw new IllegalArgumentException("Malformed request line: " + line);
        }

        HttpMethod method = HttpMethod.valueOf(parts[0]);
        String path = extractPath(parts[1]);
        String version = parts[2];
        Map<String, String> queries = extractQueries(parts[1]);

        return new HttpRequestLine(method, path, version, queries);
    }

    private HttpHeaders parseHeaders(InputStream in) throws IOException {
        String line;
        Map<String, String[]> headerMap = new HashMap<>();
        while ((line = StreamUtils.copyToString(in, StandardCharsets.UTF_8)) != null && !line.isEmpty()) {
            String[] pair = line.split(HEADER_KEY_DELIMITER, 2);
            if (pair.length == 2) {
                String headerKey = pair[0].trim();
                String[] headerValues = pair[1].trim().split(HEADER_VALUE_DELIMITER);
                headerMap.put(headerKey, headerValues);
            }
        }
        return new HttpHeaders(headerMap);
    }

    private HttpRequestBody parseRequestBody(InputStream in, int length) throws IOException {
        if (length <= 0) {
            return new HttpRequestBody(new byte[0]);
        }

        byte[] body = StreamUtils.copyToByteArray(in, length);
        return new HttpRequestBody(body);
    }

    private static String extractPath(String fullPath) {
        int queryIndex = fullPath.indexOf(QUERY_SEPARATOR);
        String path = queryIndex == -1 ? fullPath : fullPath.substring(0, queryIndex);
        return URLDecoder.decode(path, StandardCharsets.UTF_8);
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

            String decodedKey = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String decodedValue = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            queryMap.put(decodedKey, decodedValue);
        }
        return queryMap;
    }
}
