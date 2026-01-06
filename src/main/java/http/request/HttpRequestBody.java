package http.request;

public class HttpRequestBody {

    private final byte[] body;

    public HttpRequestBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public String toString() {
        return new String(body);
    }
}
