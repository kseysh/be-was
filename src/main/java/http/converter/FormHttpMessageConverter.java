package http.converter;

import enums.ContentTypes;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FormHttpMessageConverter implements HttpMessageConverter<Form<String, String>> {

    private static final String PARAM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final List<ContentTypes> supportedContentTypes = List.of(
            ContentTypes.APPLICATION_FORM_URL_ENCODED
    );

    public FormHttpMessageConverter() {}

    private Charset charset = DEFAULT_CHARSET;

    @Override
    public boolean canRead(Class<?> clazz, ContentTypes contentType) {
        if (!Form.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (contentType == null) {
            return true;
        }
        return supportedContentTypes.contains(contentType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, ContentTypes contentType) {
        if (!Form.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (contentType == null) {
            return true;
        }
        return supportedContentTypes.contains(contentType);
    }

    @Override
    public Form<String, String> read(HttpRequest request) {

        String body = new String(request.getBody().getBody());

        String[] pairs = body.split(PARAM_SEPARATOR);
        Form<String, String> form = new Form<>();

        for (String pair : pairs) {
            int idx = pair.indexOf(KEY_VALUE_SEPARATOR);
            if (idx == -1) {
                form.add(URLDecoder.decode(pair, charset), null);
            } else {
                String name = URLDecoder.decode(pair.substring(0, idx), charset);
                String value = URLDecoder.decode(pair.substring(idx + 1), charset);
                form.add(name, value);
            }
        }

        return form;
    }

    @Override
    public void write(Form<String, String> form, HttpResponse response) {

    }
}
