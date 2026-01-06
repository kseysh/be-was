package http.request;

import java.util.Map;

import enums.HttpMethod;
import java.util.StringJoiner;

public class HttpRequestLine {

    private static final String CRLF = "\r\n";
    private static final String QUERY_STRING_PREFIX = "?";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String REQUEST_LINE_FORMAT = "%s %s %s" + CRLF;
    private static final String REQUEST_LINE_FORMAT_WITH_QUERIES = "%s %s" + QUERY_STRING_PREFIX + "%s %s" + CRLF;

    private final HttpMethod method;
    private String path;
    private final String version;
    private final Map<String, String> queries;

    public HttpRequestLine(HttpMethod method, String path, String version, Map<String, String> queries) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.queries = queries;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        StringJoiner queryJoiner = new StringJoiner(PARAMETER_SEPARATOR);

        for (Map.Entry<String, String> entry : queries.entrySet()) {
            queryJoiner.add(entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue());
        }

        if (queryJoiner.length() > 0) {
            return REQUEST_LINE_FORMAT_WITH_QUERIES.formatted(method, path, queryJoiner.toString(), version);
        } else {
            return REQUEST_LINE_FORMAT.formatted(method.name(), path, version);
        }
    }
}