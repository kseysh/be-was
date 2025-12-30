package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest{

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private byte[] body;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestLine = new HttpRequestLine(br);
        logger.debug("request line: {}", requestLine);
        headers = new HttpHeaders(br);
        logger.debug("request header: {}", headers);
        body = readBody(br, headers.getContentLength());
        logger.debug("request body: {}", Arrays.toString(body));
    }

    private byte[] readBody(BufferedReader br, int length) throws IOException {
        if (length == 0) return new byte[0];

        char[] buffer = new char[length];
        int totalRead = 0;
        while (totalRead < length) {
            int read = br.read(buffer, totalRead, length - totalRead);
            if (read == -1) break;
            totalRead += read;
        }
        return String.valueOf(buffer).getBytes();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getQuery() {
        return requestLine.getQueries();
    }

    public void setPath(String path) {
        requestLine.setPath(path);
    }
}
