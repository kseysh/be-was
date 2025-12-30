package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest{
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private byte[] body;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestLine = new HttpRequestLine(br);
        headers = new HttpHeaders(br);
        body = readBody(br, headers.getContentLength());
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
}
