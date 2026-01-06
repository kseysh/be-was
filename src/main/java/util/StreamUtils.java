package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StreamUtils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static String copyToString(InputStream in, Charset charset) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            if (b == '\r') {
                int next = in.read();
                if (next == '\n') {
                    break;
                }
                bos.write(b);
                bos.write(next);
            } else {
                bos.write(b);
            }
        }
        if (bos.size() == 0 && b == -1) return "";
        if (charset == null) charset = DEFAULT_CHARSET;
        return bos.toString(charset);
    }

    public static byte[] copyToByteArray(InputStream in, int length) throws IOException {
        byte[] body = new byte[length];
        int totalRead = 0;
        while (totalRead < length) {
            int read = in.read(body, totalRead, length - totalRead);
            if (read == -1) {
                break;
            }
            totalRead += read;
        }
        return body;
    }
}
