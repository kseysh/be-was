package http.converter;

import enums.ContentTypes;
import exception.UnsupportedMediaTypeException;
import java.util.List;

public class HttpMessageConverterMapper {

    private static final List<HttpMessageConverter<?>> converters;

    static {
        converters = List.of(
                new FormHttpMessageConverter()
        );
    }

    private HttpMessageConverterMapper() {
    }


    @SuppressWarnings("unchecked") // 타입 체크 후 캐스팅 진행
    public static <T> HttpMessageConverter<T> findHttpMessageConverter(Class<T> clazz, ContentTypes contentType) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter.canRead(clazz, contentType)) {
                return (HttpMessageConverter<T>) converter;
            }
        }

        throw new UnsupportedMediaTypeException("Unsupported ContentType");
    }
}
