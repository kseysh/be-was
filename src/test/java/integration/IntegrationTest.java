package integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


import enums.ContentTypes;
import enums.HttpHeader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.WebServer;

class IntegrationTest {

    private static final Integer TEST_PORT = 9090;
    private static final String TEST_URL = "http://localhost:" + TEST_PORT;
    private static final String RESOURCES_PATH = "./src/main/resources/static";
    private static final HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    static void setUp() throws Exception {
        Thread serverThread = new Thread(() -> {
            try {
                WebServer.main(new String[]{TEST_PORT.toString()});
            } catch (Exception e) {
                fail(e);
            }
        });
        serverThread.setDaemon(true); // 테스트 종료 시 함께 종료되도록 데몬 스레드로 설정
        serverThread.start();
        Thread.sleep(500);
    }

    @Test
    @DisplayName("index.html을 반환할 수 있다")
    void returnHtmlFile() throws Exception {
        // given
        String path = "/index.html";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TEST_URL + path))
                .GET()
                .build();

        // when
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo(getStaticResource(path));
        assertEquals(
                response.headers().map().get(HttpHeader.CONTENT_TYPE.getValue()).get(0),
                ContentTypes.TEXT_HTML.getMimeType()
        );
    }

    @Test
    @DisplayName("main.css를 반환할 수 있다")
    void returnCssFile() throws Exception {
        // given
        String path = "/main.css";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TEST_URL + path))
                .GET()
                .build();

        // when
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo(getStaticResource(path));
        assertEquals(
                response.headers().map().get(HttpHeader.CONTENT_TYPE.getValue()).get(0),
                ContentTypes.TEXT_CSS.getMimeType()
        );
    }

    @Test
    @DisplayName("회원가입 페이지로 이동할 수 있다.")
    void registrationPageTest() throws Exception {
        // given
        String path = "/registration";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TEST_URL + path))
                .GET()
                .build();

        // when
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo(getStaticResource(path + "/index.html"));
        assertEquals(
                response.headers().map().get(HttpHeader.CONTENT_TYPE.getValue()).get(0),
                ContentTypes.TEXT_HTML.getMimeType()
        );
    }

    @Test
    @DisplayName("GET으로 회원가입을 진행할 수 있다.")
    void registerUserTest() throws Exception {
        // given
        String path = "/create";
        String userId = "javajigi";
        String password = "testPassword";
        String name = "%EB%B0%95%EC%9E%AC%EC%84%B1";
        String email = "javajigi%40slipp.net";
        String queryFormat = "?userId=%s&password=%s&name=%s&email=%s";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TEST_URL + path + queryFormat.formatted(userId, password, name, email)))
                .GET()
                .build();

        // when
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        // then
        assertThat(response.statusCode()).isEqualTo(302);
    }

    @Test
    @DisplayName("Email이 존재하지 않아도 회원가입을 진행할 수 있다.")
    void registerUserWithoutEmailTest() throws Exception {
        // given
        String path = "/create";
        String userId = "javajigi";
        String password = "testPassword";
        String name = "%EB%B0%95%EC%9E%AC%EC%84%B1";
        String queryFormat = "?userId=%s&password=%s&name=%s&";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TEST_URL + path + queryFormat.formatted(userId, password, name)))
                .GET()
                .build();

        // when
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        // then
        assertThat(response.statusCode()).isEqualTo(302);
    }

    private byte[] getStaticResource(String path) throws IOException {
        File file = new File(RESOURCES_PATH + path);
        return Files.readAllBytes(file.toPath());
    }

}

