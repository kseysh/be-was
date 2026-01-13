package handler;

import static fixture.HttpRequestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpMethod;
import enums.HttpStatus;
import http.request.HttpRequest;
import http.request.HttpRequestLine;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileReader;

class HomeHandlerTest {

    @Test
    @DisplayName("기본 경로(/)를 보내면 /index.html로 변환하여 반환한다.")
    void homeHandlerTest(){
        // given
        String requestPath = "/";
        String expectedPath = "/index.html";
        HttpRequestLine requestLine = new HttpRequestLine(
                HttpMethod.GET,
                requestPath,
                DEFAULT_VERSION,
                DEFAULT_QUERIES
        );
        HttpRequest request = new HttpRequest(requestLine, DEFAULT_HTTP_HEADERS, DEFAULT_HTTP_REQUEST_BODY, DEFAULT_HTTP_COOKIES);

        // when
        HttpResponse response = new HttpResponse(request.getVersion());
        Handler handler = new HomeHandler();
        handler.handle(request, response);

        // then

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(FileReader.readFile(expectedPath));
        assertEquals(
                response.getHeaders().get(HttpHeader.CONTENT_TYPE.getValue()),
                ContentTypes.TEXT_HTML.getMimeType()
        );
    }
}
