package http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpMethod;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestParserTest {

    @Test
    @DisplayName("Request를 Parsing할 수 있다.")
    void parseRequestTest() throws IOException {
        // given
        final HttpMethod method = HttpMethod.GET;
        final String path = "/test";
        final String version = "HTTP/1.1";
        final String queryKey = "testQueryKey";
        final String queryValue = "testQueryValue";
        final Map<String, String> queries = new HashMap<>();
        queries.put(queryKey, queryValue);
        HttpRequestLine requestLine = new HttpRequestLine(method, path, version, queries);

        final Map<String, String[]> headers = new HashMap<>();
        final String headerKey = HttpHeader.CONTENT_TYPE.getValue();
        final String headerValue = ContentTypes.APPLICATION_JSON.getMimeType();
        headers.put(headerKey, new String[]{headerValue});
        HttpHeaders httpHeaders = new HttpHeaders(headers);

        final byte[] body = "hello body".getBytes();
        HttpRequestBody requestBody = new HttpRequestBody(body);
        headers.put(HttpHeader.CONTENT_LENGTH.getValue(), new String[]{Integer.toString(body.length)});

        // when
        HttpRequest httpRequest = new HttpRequest(requestLine, httpHeaders, requestBody);
        InputStream in = new ByteArrayInputStream(httpRequest.writeHttpRequest().getBytes());
        RequestParser parser = new RequestParser(in);

        HttpRequestLine parsedRequestLine = parser.getRequestLine();
        Map<String, String[]> parsedHeaders = parser.getHeaders().getHeaders();
        byte[] parsedBody = parser.getBody().getBody();

        // then
        // 1. Request Line 검증
        assertEquals(method, parsedRequestLine.getMethod());
        assertEquals(path, parsedRequestLine.getPath());
        assertEquals(version, parsedRequestLine.getVersion());
        assertThat(parsedRequestLine.getQueries()).containsExactlyEntriesOf(queries);

        // 2. Header 검증 (String[] 내용 비교)
        assertThat(parsedHeaders).containsExactlyEntriesOf(headers);

        // 3. Body 검증 (byte[] 내용 비교)
        assertEquals(body.length, parsedBody.length);
        assertEquals(new String(body), new String(parsedBody));
    }
}
