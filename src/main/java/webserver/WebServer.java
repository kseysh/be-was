package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import db.config.DatabaseInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int THREAD_POOL_SIZE = 100;
    private static final ExecutorService executor = new ThreadPoolExecutor(
            THREAD_POOL_SIZE, // Thread Pool min Size
            THREAD_POOL_SIZE, // Thread Pool max Size
            0, // Keep Alive Time
            TimeUnit.MILLISECONDS, // Keep Alive Time 단위
            new LinkedBlockingQueue<>(1000), // 대기열 크기 제한
            new ThreadPoolExecutor.AbortPolicy() // BlockingQueue가 꽉 차면 예외 발생
    );

    public static void main(String[] args) throws Exception {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        DatabaseInitializer.init();

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executor.submit(new RequestHandler(connection));
            }
        }
    }
}
