package http.response;

import enums.HttpHeader;
import enums.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import util.ContentTypeExtractor;
import util.FileReader;

public class HttpResponse {

    private static final String COOKIE_FORMAT_WITH_PATH = "sid=%s; Path=%s;";

    private final Map<String, String> headers = new HashMap<>();
    private final String version;
    private HttpStatus statusCode;
    private byte[] body;

    public HttpResponse(String version) {
        this.version = version;
    }

    public HttpResponse setHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpResponse setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public HttpResponse setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public HttpResponse setCookie(String cookie, String path) {
        headers.put(HttpHeader.SET_COOKIE.getValue(), COOKIE_FORMAT_WITH_PATH.formatted(cookie, path));
        return this;
    }

    public void respondWithStaticFile(String path) {
        this.statusCode = HttpStatus.OK;
        this.body = FileReader.readFile(path);
        this.headers.put(HttpHeader.CONTENT_TYPE.getValue(), ContentTypeExtractor.extractContentTypeOnFilePath(path));
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

    public Map<String, String> getHeaders() {
        return headers;
    }
}

