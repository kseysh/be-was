package http.request;

import java.util.Map;

public class HttpCookies {

    public static final Map<String, String> EMPTY_COOKIES = Map.of();
    private final Map<String, String> cookies;

    public HttpCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookies emptyCookies() {
        return new HttpCookies(EMPTY_COOKIES);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }
}
