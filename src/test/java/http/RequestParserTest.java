package http;

import static fixture.HttpRequestFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
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
        InputStream in = new ByteArrayInputStream(DEFAULT_HTTP_REQUEST.writeHttpRequest().getBytes());

        // when
        RequestParser parser = new RequestParser(in);

        HttpRequestLine parsedRequestLine = parser.getRequestLine();
        Map<String, String[]> parsedHeaders = parser.getHeaders().getHeaders();
        byte[] parsedBody = parser.getBody().getBody();

        // then
        // 1. Request Line 검증
        assertEquals(DEFAULT_HTTP_METHOD, parsedRequestLine.getMethod());
        assertEquals(DEFAULT_PATH, parsedRequestLine.getPath());
        assertEquals(DEFAULT_VERSION, parsedRequestLine.getVersion());
        assertThat(parsedRequestLine.getQueries()).containsExactlyEntriesOf(DEFAULT_QUERIES);

        // 2. Header 검증 (String[] 내용 비교)
        assertThat(parsedHeaders).containsExactlyInAnyOrderEntriesOf(DEFAULT_HEADERS);

        // 3. Body 검증 (byte[] 내용 비교)
        assertEquals(DEFAULT_HTTP_REQUEST_BODY.getBody().length, parsedBody.length);
        assertEquals(new String(DEFAULT_HTTP_REQUEST_BODY.getBody()), new String(parsedBody));
    }
}
