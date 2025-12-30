package http;

import enums.HttpStatus;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static Logger logger = LoggerFactory.getLogger(HttpResponse.class);
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

    public String getHttpVersionAndResponseCode(){
        StringBuilder sb = new StringBuilder();
        sb.append(version);
        sb.append(" ");
        sb.append(statusCode.getResponseCode());
        sb.append(" ");
        sb.append(statusCode.getResponseCodeString());
        sb.append(" \r\n");
        return sb.toString();
    }

    public void writeTo(OutputStream out) {
        try{
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(this.getHttpVersionAndResponseCode());

            if(headers != null){
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
                }
            }
            dos.writeBytes("\r\n");

            dos.write(body, 0, body.length);
            dos.flush();
        }catch(Exception e){
            logger.error("Error writing response to http response", e);
        }
    }
}

