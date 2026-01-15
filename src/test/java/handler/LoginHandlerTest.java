package handler;

import static fixture.HttpRequestFixture.DEFAULT_HTTP_COOKIES;
import static fixture.HttpRequestFixture.DEFAULT_HTTP_HEADERS;
import static fixture.HttpRequestFixture.DEFAULT_HTTP_REQUEST_BODY;
import static fixture.HttpRequestFixture.DEFAULT_QUERIES;
import static fixture.HttpRequestFixture.DEFAULT_VERSION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.config.DatabaseConfig;
import db.UserDatabase;
import enums.ContentTypes;
import enums.HttpHeader;
import enums.HttpMethod;
import enums.HttpStatus;
import http.request.HttpHeaders;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestLine;
import http.response.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileReader;

class LoginHandlerTest {

    private final UserDatabase userDatabase = DatabaseConfig.userDatabase;

    @Test
    @DisplayName("/login에 GET요청을 보내면 /login/index.html로 변환하여 반환한다.")
    void loginHandlerTest(){
        // given
        String requestPath = "/login";
        String expectedPath = "/login/index.html";
        HttpRequestLine requestLine = new HttpRequestLine(
                HttpMethod.GET,
                requestPath,
                DEFAULT_VERSION,
                DEFAULT_QUERIES
        );
        HttpRequest request = new HttpRequest(requestLine, DEFAULT_HTTP_HEADERS, DEFAULT_HTTP_REQUEST_BODY, DEFAULT_HTTP_COOKIES);

        // when
        HttpResponse response = new HttpResponse(request.getVersion());
        Handler handler = new LoginHandler();
        handler.handle(request, response);

        // then

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(FileReader.readFile(expectedPath));
        assertEquals(
                response.getHeaders().get(HttpHeader.CONTENT_TYPE.getValue()),
                ContentTypes.TEXT_HTML.getMimeType()
        );
    }

    @Test
    @DisplayName("/login에 POST요청을 보낼 때,유저가 존재하지 않으면 Not Found 에러를 반환한다.")
    void throwNotFoundExceptionWhenUserNotFound(){
        // given
        String requestPath = "/login";
        HttpRequestLine requestLine = new HttpRequestLine(
                HttpMethod.POST,
                requestPath,
                DEFAULT_VERSION,
                DEFAULT_QUERIES
        );

        String userId = "testUserId";
        String password = "testPassword";
        String urlEncodedFormat = "userId=%s&password=%s";
        String urlEncodedString = urlEncodedFormat.formatted(userId, password);

        Map<String, String[]> headers = new HashMap<>();
        headers.put(
                HttpHeader.CONTENT_TYPE.getValue(),
                new String[]{ContentTypes.APPLICATION_FORM_URL_ENCODED.getMimeType()}
        );
        HttpHeaders httpHeaders = new HttpHeaders(headers);

        HttpRequest request = new HttpRequest(
                requestLine,
                httpHeaders,
                new HttpRequestBody(urlEncodedString.getBytes(StandardCharsets.UTF_8)),
                DEFAULT_HTTP_COOKIES
        );

        // when & then
        Handler handler = new LoginHandler();
        assertThrows(Exception.class, () -> handler.handle(request, new HttpResponse(request.getVersion())));
    }

    @Test
    @DisplayName("/login에 POST요청을 보내면 Set-Cookie header를 반환한다")
    void shouldReturnSetCookieHeaderWhenPostRequest(){
        // given
        String requestPath = "/login";
        String expectedPath = "/";
        HttpRequestLine requestLine = new HttpRequestLine(
                HttpMethod.POST,
                requestPath,
                DEFAULT_VERSION,
                DEFAULT_QUERIES
        );

        String userId = "testUserId";
        String password = "testPassword";
        User user = new User(userId, password, "name", "email", "imageId");
        userDatabase.save(user);

        String urlEncodedFormat = "userId=%s&password=%s";
        String urlEncodedString = urlEncodedFormat.formatted(userId, password);

        Map<String, String[]> headers = new HashMap<>();
        headers.put(
                HttpHeader.CONTENT_TYPE.getValue(),
                new String[]{ContentTypes.APPLICATION_FORM_URL_ENCODED.getMimeType()}
        );
        HttpHeaders httpHeaders = new HttpHeaders(headers);

        HttpRequest request = new HttpRequest(
                requestLine,
                httpHeaders,
                new HttpRequestBody(urlEncodedString.getBytes(StandardCharsets.UTF_8)),
                DEFAULT_HTTP_COOKIES
        );

        // when
        HttpResponse response = new HttpResponse(request.getVersion());
        Handler handler = new LoginHandler();
        handler.handle(request, response);

        // then
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        Map<String, String> responseHeaders = response.getHeaders();
        assertEquals(expectedPath, response.getHeaders().get(HttpHeader.LOCATION.getValue()));
        assertNotNull(responseHeaders.get(HttpHeader.SET_COOKIE.getValue()));
    }
}
