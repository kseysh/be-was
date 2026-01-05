package http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpMethod;
import fixture.FakeHttpRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        final String headerKey = HttpHeader.CONTENT_TYPE.getValue();
        final String headerValue = ContentTypes.APPLICATION_JSON.getMimeType();
        final byte[] body = "hello body".getBytes();

        FakeHttpRequest request = FakeHttpRequest.build()
                .withMethod(method)
                .withPath(path)
                .withVersion(version)
                .withHeader(headerKey, headerValue)
                .withBody(body);

        // when
        InputStream in = new ByteArrayInputStream(request.writeHttpRequest().getBytes());
        RequestParser parser = new RequestParser(in);

        HttpRequestLine requestLine = parser.getRequestLine();
        Map<String, String[]> realHeaders = parser.getHeaders().getHeaders();
        byte[] realBody = parser.getBody().getBody();

        // then
        // 1. Request Line 검증
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getPath()).isEqualTo(path);
        assertThat(requestLine.getVersion()).isEqualTo(version);

        // 2. Header 검증 (String[] 내용 비교)
        assertThat(realHeaders).containsKey(headerKey);
        assertThat(realHeaders.get(headerKey)).containsExactly(headerValue);

        // 3. Body 검증 (byte[] 내용 비교)
        assertEquals(body.length, realBody.length);
        assertThat(new String(realBody)).isEqualTo(new String(body));
    }
}
