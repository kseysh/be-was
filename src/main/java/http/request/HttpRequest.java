package http.request;

import enums.ContentTypes;
import enums.HttpMethod;
import java.util.Map;

public class HttpRequest {

    private static final String CRLF = "\r\n";

    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    private final HttpRequestBody body;

    public HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, HttpRequestBody body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public HttpRequestBody getBody() {
        return body;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public Map<String, String> getQuery() {
        return requestLine.getQueries();
    }

    public ContentTypes getContentType() {
        return headers.getContentType();
    }

    public void setPath(String path) {
        requestLine.setPath(path);
    }

    public String writeHttpRequest() {
        return requestLine.toString()
                + headers.toString()
                + CRLF
                + body.toString()
                + CRLF + CRLF;
    }
}
