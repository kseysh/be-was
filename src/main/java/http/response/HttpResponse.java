package http.response;

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

