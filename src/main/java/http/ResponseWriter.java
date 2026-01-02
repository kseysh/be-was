package http;

import enums.HttpHeader;
import enums.HttpStatus;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseWriter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    private static final String CRLF = "\r\n";
    private static final String STATUS_LINE_FORMAT = "%s %s %s" + CRLF;
    private static final String HEADER_FORMAT = "%s: %s" + CRLF;

    private ResponseWriter() {
    }

    public static void writeTo(OutputStream out, HttpResponse response) throws IOException {
        try {
            DataOutputStream dos = new DataOutputStream(out);

            writeStatusLine(dos, response);
            writeHeaders(dos, response);
            writeBody(dos, response);

            dos.flush();
        } catch (Exception e) {
            logger.error("Error writing response to http response", e);
        }
    }

    private static void writeStatusLine(DataOutputStream dos, HttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        dos.writeBytes(STATUS_LINE_FORMAT.formatted(
                response.getVersion(),
                statusCode.getResponseCode(),
                statusCode.getResponseCodeString()
        ));
    }

    private static void writeHeaders(DataOutputStream dos, HttpResponse response) throws IOException {
        Map<String, String> headers = response.getHeaders();
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                dos.writeBytes(HEADER_FORMAT.formatted(header.getKey(), header.getValue()));
            }
        }

        byte[] body = response.getBody();
        if (body != null && body.length > 0) {
            dos.writeBytes(HEADER_FORMAT.formatted(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length)));
        }
    }

    private static void writeBody(DataOutputStream dos, HttpResponse response) throws IOException {
        byte[] body = response.getBody();
        if (body != null && body.length > 0) {
            dos.writeBytes(CRLF);
            dos.write(body, 0, body.length);
        }
    }
}
