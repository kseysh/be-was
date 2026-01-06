package http.converter;

import enums.ContentTypes;
import http.request.HttpRequest;
import http.response.HttpResponse;

public interface HttpMessageConverter<T> {

    boolean canRead(Class<?> clazz, ContentTypes contentType);
    boolean canWrite(Class<?> clazz, ContentTypes contentType);

    T read(HttpRequest request);
    void write(T t, HttpResponse response);
}
