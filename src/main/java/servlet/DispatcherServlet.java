package servlet;

import java.util.HashMap;
import java.util.Map;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class DispatcherServlet implements Servlet {

    private static final DispatcherServlet INSTANCE = new DispatcherServlet();
    private static final Map<String, Servlet> SERVLETS = new HashMap<>();

    private DispatcherServlet() {
        initMapping();
    }

    private void initMapping() {
        SERVLETS.put("/", new StaticResourceServlet());
    }

    public static DispatcherServlet getInstance(){
        return INSTANCE;
    }

    public Servlet getHandler(String path){
        while(SERVLETS.get(path) == null){
            int lastSlashIndex = path.lastIndexOf('/');
            if (lastSlashIndex == 0) {
                break;
            } else {
                path = path.substring(0, lastSlashIndex);
            }
        }

        return SERVLETS.get(path);
    }

    @Override
    public HttpResponse doService(HttpRequest request) {
        Servlet servlet = getHandler(request.getRequestURI());
        return servlet.doService(request);
    }
}
