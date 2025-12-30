package http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestBody {
    private final byte[] body;

    public HttpRequestBody(BufferedReader br, int length) throws IOException {
        if (length == 0){
            body = new byte[0];
            return;
        }

        char[] buffer = new char[length];
        int totalRead = 0;
        while (totalRead < length) {
            int read = br.read(buffer, totalRead, length - totalRead);
            if (read == -1) break;
            totalRead += read;
        }
        body = String.valueOf(buffer).getBytes();
    }

    public byte[] getBody() {
        return body;
    }

    public String toString(){
        return new String(body);
    }
}
