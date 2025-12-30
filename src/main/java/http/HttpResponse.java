package http;

import enums.HttpStatus;
import java.util.Map;

public class HttpResponse {
    private Map<String, String> headers;
    private byte[] body;
    private HttpStatus statusCode;
    private String version;

    public HttpResponse() {}

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public void setVersion(String version) {
        this.version = version;
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

