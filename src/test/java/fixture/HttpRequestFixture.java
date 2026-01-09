package fixture;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpMethod;
import http.request.HttpCookies;
import http.request.HttpHeaders;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestLine;
import java.util.Map;

public class HttpRequestFixture {

    // requestLine
    public static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.GET;
    public static final String DEFAULT_PATH = "/test";
    public static final String DEFAULT_VERSION = "HTTP/1.1";
    public static final String DEFAULT_QUERY_KEY = "testQueryKey";
    public static final String DEFAULT_QUERY_VALUE = "testQueryValue";
    public static final Map<String, String> DEFAULT_QUERIES = Map.of(DEFAULT_QUERY_KEY, DEFAULT_QUERY_VALUE);
    public static final HttpRequestLine DEFAULT_REQUEST_LINE = new HttpRequestLine(
            DEFAULT_HTTP_METHOD, DEFAULT_PATH, DEFAULT_VERSION, DEFAULT_QUERIES
    );

    // request body
    public static final byte[] DEFAULT_BODY = "test body".getBytes();
    public static final HttpRequestBody DEFAULT_HTTP_REQUEST_BODY = new HttpRequestBody(DEFAULT_BODY);

    // request header
    public static final String DEFAULT_HEADER_KEY = HttpHeader.CONTENT_TYPE.getValue();
    public static final String DEFAULT_HEADER_VALUE = ContentTypes.APPLICATION_JSON.getMimeType();
    public static final Map<String, String[]> DEFAULT_HEADERS = Map.of(
            DEFAULT_HEADER_KEY, new String[]{DEFAULT_HEADER_VALUE},
            HttpHeader.CONTENT_LENGTH.getValue(), new String[]{Integer.toString(DEFAULT_BODY.length)}
    );
    public static final HttpHeaders DEFAULT_HTTP_HEADERS = new HttpHeaders(DEFAULT_HEADERS);

    // Cookie
    public static final Map<String, String> DEFAULT_COOKIES = Map.of("sid", "value");
    public static final HttpCookies DEFAULT_HTTP_COOKIES = new HttpCookies(DEFAULT_COOKIES);

    // http request
    public static final HttpRequest DEFAULT_HTTP_REQUEST = new HttpRequest(
            DEFAULT_REQUEST_LINE,
            DEFAULT_HTTP_HEADERS,
            DEFAULT_HTTP_REQUEST_BODY,
            DEFAULT_HTTP_COOKIES
    );
}
