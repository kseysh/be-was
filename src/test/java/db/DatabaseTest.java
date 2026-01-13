package db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DatabaseTest {

    @Test
    @DisplayName("동시 요청이 들어와도 정합성을 유지한다.")
    void concurrencyTest() throws InterruptedException {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    String randomId = UUID.randomUUID().toString();
                    Database.addUser(new User(randomId, "testPassword", "testName", "testEmail", "testImageId"));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        assertEquals(threadCount, Database.findAll().size());
    }

}
