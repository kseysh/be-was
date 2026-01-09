package webserver;

import http.request.RequestParser;
import http.response.ResponseWriter;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestParser parser = new RequestParser(in);
            HttpRequest request = new HttpRequest(
                    parser.getRequestLine(),
                    parser.getHeaders(),
                    parser.getBody(),
                    parser.getCookies()
            );
            logger.debug("New Request : {}", request.writeHttpRequest());

            HttpResponse response = new HttpResponse();
            DispatcherServlet.getInstance().doDispatch(request, response);

            ResponseWriter.writeTo(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
