package http.converter;

import enums.ContentTypes;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipartHttpMessageConverter implements HttpMessageConverter<MultipartData> {

    private static final char QUOTES = '"';
    private static final char HEADER_VALUE_SEPARATOR = ';';
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String KEY_DELIMITER = ":";
    private static final String BOUNDARY_PREFIX = "--";
    private static final String CRLF = "\r\n";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final byte[] DOUBLE_CRLF_BYTES = (CRLF + CRLF).getBytes(DEFAULT_CHARSET);
    private static final byte[] CRLF_BYTES = CRLF.getBytes(DEFAULT_CHARSET);

    private static final List<ContentTypes> supportedContentTypes = List.of(
            ContentTypes.MULTIPART_FORM_DATA
    );

    public MultipartHttpMessageConverter() {
    }

    @Override
    public boolean canRead(Class<?> clazz, ContentTypes contentType) {
        if (!MultipartData.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (contentType == null) {
            return true;
        }
        return supportedContentTypes.contains(contentType);
    }

    @Override
    public MultipartData read(HttpRequest request) {

        MultipartData result = new MultipartData();

        String boundary = request.getMultipartBoundary();
        byte[] boundaryBytes = (BOUNDARY_PREFIX + boundary).getBytes(DEFAULT_CHARSET);

        byte[] bodyBytes = request.getBody().getBody();

        parseBody(bodyBytes, boundaryBytes, result);

        return result;
    }

    private void parseBody(byte[] body, byte[] boundaryBytes, MultipartData result) {
        int currentPos = indexOf(body, boundaryBytes, 0); // 첫 번째 경계선 찾기
        if (currentPos == -1) return;

        while (currentPos < body.length) {
            // 1. 현재 경계선 다음 위치로 이동 (CRLF 포함)
            int partStart = currentPos + boundaryBytes.length + CRLF_BYTES.length;

            // 2. 다음 경계선 위치 찾기 (현재 파트의 끝)
            int nextBoundary = indexOf(body, boundaryBytes, partStart);
            if (nextBoundary == -1) break; // 더 이상 파트가 없음

            // 3. 현재 파트 내에서 헤더와 바디를 나누는 지점(\r\n\r\n) 찾기
            int headerEnd = indexOf(body, DOUBLE_CRLF_BYTES, partStart);
            if (headerEnd != -1 && headerEnd < nextBoundary) {

                // 4. 파트 헤더 추출 및 파싱
                byte[] headerBytes = Arrays.copyOfRange(body, partStart, headerEnd);
                String headerString = new String(headerBytes, StandardCharsets.UTF_8);
                Map<String, String> partHeaders = parsePartHeaders(headerString);

                // 5. 파트 바디(데이터) 추출
                int bodyStart = headerEnd + DOUBLE_CRLF_BYTES.length;
                int bodyEnd = nextBoundary - CRLF_BYTES.length; // 마지막 CRLF 제외
                byte[] partBody = Arrays.copyOfRange(body, bodyStart, bodyEnd);

                // 6. 결과 객체에 담기
                savePart(partHeaders, partBody, result);
            }

            currentPos = nextBoundary;

            // 종료 경계선인지 확인 (--boundary-- 형태)
            if (body[currentPos + boundaryBytes.length] == '-' &&
                    body[currentPos + boundaryBytes.length + 1] == '-') {
                break;
            }
        }
    }

    private int indexOf(byte[] target, byte[] pattern, int start) {
        for (int i = start; i <= target.length - pattern.length; i++) {
            boolean match = true;
            for (int j = 0; j < pattern.length; j++) {
                if (target[i + j] != pattern[j]) {
                    match = false;
                    break;
                }
            }
            if (match) return i;
        }
        return -1;
    }

    private Map<String, String> parsePartHeaders(String headerString) {
        Map<String, String> headers = new HashMap<>();
        String[] lines = headerString.split(CRLF);
        for (String line : lines) {
            String[] parts = line.split(KEY_DELIMITER, 2);
            if (parts.length == 2) headers.put(parts[0].trim(), parts[1].trim());
        }
        return headers;
    }

    private void savePart(Map<String, String> headers, byte[] content, MultipartData result) {
        String disposition = headers.get("Content-Disposition");
        if (disposition == null) return;

        String name = extractAttribute(disposition, "name");
        String filename = extractAttribute(disposition, "filename");

        if (filename == null) {
            result.addText(name, new String(content, StandardCharsets.UTF_8));
        } else {
            ContentTypes contentType = ContentTypes.fromMimeType(headers.get("Content-Type"));
            ImageForm imageForm = new ImageForm(content, filename, contentType);
            result.addFile(name, imageForm);
        }
    }

    private String extractAttribute(String headerValue, String attributeName) {
        if (headerValue == null || !headerValue.contains(attributeName + KEY_VALUE_SEPARATOR)) {
            return null;
        }

        // 1. 속성 이름 위치 찾기
        int startIdx = headerValue.indexOf(attributeName + KEY_VALUE_SEPARATOR);
        if (startIdx == -1) return null;

        // 2. 값의 시작 위치로 이동
        startIdx += (attributeName.length() + 1);

        // 3. 따옴표 여부 확인
        boolean isQuoted = headerValue.charAt(startIdx) == QUOTES;
        if (isQuoted) startIdx++;

        // 4. 끝 위치 찾기 (따옴표면 다음 따옴표까지, 아니면 세미콜론이나 끝까지)
        int endIdx;
        if (isQuoted) {
            endIdx = headerValue.indexOf(QUOTES, startIdx);
        } else {
            endIdx = headerValue.indexOf(HEADER_VALUE_SEPARATOR, startIdx);
            if (endIdx == -1) endIdx = headerValue.length();
        }

        return (endIdx != -1) ? headerValue.substring(startIdx, endIdx).trim() : null;
    }

    @Override
    public boolean canWrite(Class<?> clazz, ContentTypes contentType) {
        return false;
    }

    @Override
    public void write(MultipartData data, HttpResponse response) {
        // 필요 시 구현
    }
}
