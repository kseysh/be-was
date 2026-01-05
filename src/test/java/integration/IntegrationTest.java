package integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


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

    @DisplayName("index.html을 반환할 수 있다")
    @Test
    void index() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TEST_URL + "/index.html"))
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo(getStaticResource("/index.html"));
    }

    private byte[] getStaticResource(String path) throws IOException {
        File file = new File(RESOURCES_PATH + path);
        return Files.readAllBytes(file.toPath());
    }

}

