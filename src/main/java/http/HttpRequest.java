package http;

import enums.HttpMethod;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpRequest{
    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    private final HttpRequestBody body;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestLine = new HttpRequestLine(br);
        headers = new HttpHeaders(br);
        body = new HttpRequestBody(br, headers.getContentLength());
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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(requestLine.toString()).append("\n");
        sb.append(headers.toString()).append("\n");
        sb.append(body.toString());
        return sb.toString();
    }
}
