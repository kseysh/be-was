package http.request;

import enums.ContentTypes;
import enums.HttpHeader;
import exception.UnsupportedMediaTypeException;
import java.util.Map;
import java.util.StringJoiner;

public class HttpHeaders {

    private static final String CRLF = "\r\n";
    private static final String REQUEST_HEADER_FORMAT = "%s: %s" + CRLF;
    private static final String VALUE_DELIMITER = ",";
    private static final String CONTENT_TYPE_DELIMITER = ";";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String BOUNDARY = "boundary";

    private final Map<String, String[]> headers;

    public HttpHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }

    public int getContentLength() {
        String[] values = headers.get(HttpHeader.CONTENT_LENGTH.getValue());
        if (values == null || values.length == 0) {
            return 0;
        }
        return Integer.parseInt(values[0]);
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> header : headers.entrySet()) {
            String[] values = header.getValue();
            StringJoiner sj = new StringJoiner(VALUE_DELIMITER);

            for (String value : values) {
                sj.add(value);
            }

            sb.append(REQUEST_HEADER_FORMAT.formatted(header.getKey(), sj.toString()));
        }
        return sb.toString();
    }

    public ContentTypes getContentType() {
        String[] headerValues = headers.get(HttpHeader.CONTENT_TYPE.getValue());
        String headerValue;
        if (headerValues == null || headerValues.length == 0) {
            headerValue = null;
        }else{
            headerValue = headerValues[0].split(CONTENT_TYPE_DELIMITER)[0].trim();
        }
        return ContentTypes.fromMimeType(headerValue);
    }

    public String getMultipartBoundary() {
        String[] headerValues = headers.get(HttpHeader.CONTENT_TYPE.getValue());

        if (headerValues == null || headerValues.length == 0) {
            throw new UnsupportedMediaTypeException("Content-Type header not found");
        }

        String[] parts = headerValues[0].split(CONTENT_TYPE_DELIMITER);

        for (String part : parts) {
            String trimmedPart = part.trim();

            if (trimmedPart.toLowerCase().startsWith(BOUNDARY.toLowerCase())) {
                String[] values = trimmedPart.split(KEY_VALUE_SEPARATOR);
                if (values.length == 2) {
                    return values[1].trim();
                }
            }
        }

        throw new UnsupportedMediaTypeException("Boundary parameter not found in Content-Type");
    }
}
