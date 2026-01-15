package db.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapCache<K, V> implements Cache<K, V> {

    private final ConcurrentMap<K, V> map = new ConcurrentHashMap<>();

    public ConcurrentMapCache() {
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void evict(K key) {
        map.remove(key);
    }
}
