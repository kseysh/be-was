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
    private byte[] body;
    private HttpStatus statusCode;
    private String version;

    public HttpResponse() {}

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

    public HttpResponse setVersion(String version) {
        this.version = version;
        return this;
    }

    public HttpResponse setCookie(String cookie, String path) {
        headers.put(HttpHeader.SET_COOKIE.getValue(), COOKIE_FORMAT_WITH_PATH.formatted(cookie, path));
        return this;
    }

    public void respondWithStaticFile(String version, String path) {
        this.statusCode = HttpStatus.OK;
        this.version = version;
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

    /** TODO: Builder 패턴 고민해보기
    public static class HttpResponseBuilder {
        private String version;
        private Map<String, String> headers;
        private byte[] body;
        private HttpStatus statusCode;

        private HttpResponseBuilder(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }

        public HttpResponseBuilder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public HttpResponseBuilder body(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpResponseBuilder version(String version) {
            this.version = version;
            return this;
        }

        public HttpResponse build(){
            return new HttpResponse(this.version, this.headers, this.body, this.statusCode);
        }
    }

    public static HttpResponseBuilder ok(){
        return new HttpResponseBuilder(HttpStatus.OK);
    }

    public static HttpResponseBuilder badRequest(){
        return new HttpResponseBuilder(HttpStatus.BAD_REQUEST);
    }

    public static HttpResponseBuilder notFound(){
        return new HttpResponseBuilder(HttpStatus.NOT_FOUND);
    }

    public static HttpResponseBuilder serverError(){
        return new HttpResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR);
    }
     **/
}

