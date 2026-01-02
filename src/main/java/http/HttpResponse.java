package http;

import enums.HttpHeader;
import enums.HttpStatus;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CRLF = "\r\n";
    private static final String STATUS_LINE_FORMAT = "%s %s %s" + CRLF;
    private static final String HEADER_FORMAT = "%s: %s" + CRLF;

    private Map<String, String> headers;
    private byte[] body;
    private HttpStatus statusCode;
    private String version;

    public HttpResponse() {}

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getBody() {
        return body;
    }

    public void writeTo(OutputStream out) {
        try{
            DataOutputStream dos = new DataOutputStream(out);

            writeStatusLine(dos);
            writeHeaders(dos);
            writeBody(dos);

            dos.flush();
        }catch(Exception e){
            logger.error("Error writing response to http response", e);
        }
    }

    private void writeStatusLine(DataOutputStream dos) throws IOException {
        dos.writeBytes(STATUS_LINE_FORMAT.formatted(
                version,
                statusCode.getResponseCode(),
                statusCode.getResponseCodeString()
        ));
    }

    private void writeHeaders(DataOutputStream dos) throws IOException {
        if(headers != null){
            for (Map.Entry<String, String> header : headers.entrySet()) {
                dos.writeBytes(HEADER_FORMAT.formatted(header.getKey(), header.getValue()));
            }
        }

        if (body != null && body.length > 0) {
            dos.writeBytes(HEADER_FORMAT.formatted(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length)));
        }
    }

    private void writeBody(DataOutputStream dos) throws IOException {
        if (body != null && body.length > 0) {
            dos.writeBytes(CRLF);
            dos.write(body, 0, body.length);
        }
    }
}

