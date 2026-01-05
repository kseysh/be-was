package fixture;

import enums.HttpHeader;
import enums.HttpMethod;
import java.util.HashMap;
import java.util.Map;

public class FakeHttpRequest {

    private static final String DEFAULT_PATH = "/";
    private static final String DEFAULT_VERSION = "HTTP/1.1";
    private static final String CRLF = "\r\n";
    private static final String REQUEST_LINE_FORMAT = "%s %s %s" + CRLF;
    private static final String REQUEST_HEADER_FORMAT = "%s: %s" + CRLF;

    private HttpMethod method = HttpMethod.GET;
    private String path = DEFAULT_PATH;
    private String version = DEFAULT_VERSION;
    private final Map<String, String[]> headers = new HashMap<>();
    private byte[] body = new byte[0];

    private FakeHttpRequest() {
    }

    public static FakeHttpRequest build() {
        return new FakeHttpRequest();
    }

    public FakeHttpRequest withMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public FakeHttpRequest withPath(String path) {
        this.path = path;
        return this;
    }

    public FakeHttpRequest withVersion(String version) {
        this.version = version;
        return this;
    }

    public FakeHttpRequest withBody(byte[] body) {
        this.body = body;
        this.headers.put(HttpHeader.CONTENT_LENGTH.getValue(), new String[]{String.valueOf(body.length)});
        return this;
    }

    public FakeHttpRequest withHeader(String name, String value) {
        headers.put(name, new String[]{value});
        return this;
    }

    public String writeHttpRequest() {
        return getRequestLine() + getHeaders() + CRLF + getBody() + CRLF + CRLF;
    }

    public String getRequestLine() {
        return REQUEST_LINE_FORMAT.formatted(method.name(), path, version);
    }

    public String getHeaders() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> header : headers.entrySet()) {
            sb.append(REQUEST_HEADER_FORMAT.formatted(header.getKey(), header.getValue()[0]));
        }
        return sb.toString();
    }

    public String getBody() {
        return new String(body);
    }
}
