package http;

import java.util.Map;

import enums.HttpMethod;

public class HttpRequestLine {
    private static final String REQUEST_LINE_SPACE = " ";

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

    public String toString(){
        return this.method.name() + REQUEST_LINE_SPACE + this.path + REQUEST_LINE_SPACE + this.version;
    }
}