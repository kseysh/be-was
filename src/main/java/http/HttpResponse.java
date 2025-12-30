package http;

import enums.HttpStatus;
import java.util.Map;

public class HttpResponse {
    private Map<String, String> headers;
    private byte[] body;
    private HttpStatus statusCode;
    private String version;

    public HttpResponse(Map<String, String> headers, byte[] body, HttpStatus statusCode, String version) {
        this.headers = headers;
        this.body = body;
        this.statusCode = statusCode;
        this.version = version;
    }

    public static HttpResponse ok(Map<String, String> headers,  byte[] body, String httpVersion) {
        return new HttpResponse(headers, body, HttpStatus.OK, httpVersion);
    }

    public static HttpResponse notFound(Map<String, String> headers,  byte[] body, String httpVersion) {
        return new HttpResponse(headers, body, HttpStatus.NOT_FOUND, httpVersion);
    }

    public static HttpResponse internalServerError(Map<String, String> headers,  byte[] body, String httpVersion) {
        return new HttpResponse(headers, body, HttpStatus.NOT_FOUND, httpVersion);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getVersion() {
        return version;
    }

    public String getHttpVersionAndResponseCode(){
        StringBuilder sb = new StringBuilder();
        sb.append(version);
        sb.append(" ");
        sb.append(statusCode.getResponseCode());
        sb.append(" ");
        sb.append(statusCode.getResponseCodeString());
        sb.append(" \r\n");
        return sb.toString();
    }
}

