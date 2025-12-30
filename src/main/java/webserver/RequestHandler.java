package webserver;

import enums.HttpHeader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.DispatcherServlet;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()){
            HttpRequest request = new HttpRequest(in);
            DispatcherServlet dispatcherServlet = DispatcherServlet.getInstance();
            HttpResponse response = dispatcherServlet.doService(request);
            sendMessage(response, out);
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendMessage(HttpResponse response, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(response.getHttpVersionAndResponseCode());

        Map<String, String> headers = response.getHeaders();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");

        dos.write(response.getBody(), 0, response.getBody().length);
        dos.flush();
    }
}
