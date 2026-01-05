package http;

import enums.HttpMethod;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    private final HttpRequestBody body;

    public HttpRequest(InputStream in) throws IOException {
        RequestParser parser = new RequestParser(in);
        requestLine = parser.getRequestLine();
        headers = parser.getHeaders();
        body = parser.getBody();
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

    public void setPath(String path) {
        requestLine.setPath(path);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(requestLine.toString());
        sb.append(headers.toString());
        sb.append(body.toString());
        return sb.toString();
    }
}
