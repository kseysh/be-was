package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import enums.HttpMethod;

public class HttpRequestLine {
    private static final String REQUEST_LINE_SPACE = " ";
    private static final String QUERY_SEPARATOR = "?";
    private static final Integer REQUEST_LINE_LENGTH = 3;
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String PARAM_SEPARATOR = "&";

    private final HttpMethod method;
    private final String path;
    private final String version;
    private final Map<String, String> queries;

    public HttpRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalArgumentException("Empty request line");
        }

        String[] parts = requestLine.strip().split(REQUEST_LINE_SPACE);

        if (parts.length != REQUEST_LINE_LENGTH) {
            throw new IllegalArgumentException("Malformed request line: " + requestLine);
        }

        this.method = HttpMethod.valueOf(parts[0]);
        this.path = extractPath(parts[1]);
        this.version = parts[2];
        this.queries = extractQueries(parts[1]);
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

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }
}