package db;

import java.util.Optional;
import java.util.UUID;
import model.User;

public class SessionManager {

    private static final ConcurrentMapCache<String, User> cache = new ConcurrentMapCache<>();
    private static final SessionManager INSTANCE = new SessionManager();

    private SessionManager() {}

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public String setAttribute(User user){
        String sessionId = createSessionId();
        cache.put(sessionId, user);
        return sessionId;
    }

    public Optional<User> getAttribute(String sessionId){
        return Optional.ofNullable(cache.get(sessionId));
    }

    public void removeAttribute(String sessionId){
        cache.evict(sessionId);
    }

    private String createSessionId(){
        return UUID.randomUUID().toString();
    }

}
