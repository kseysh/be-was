package http.converter;

import java.util.HashMap;
import java.util.Map;

public class Form<K, V> {
    private final Map<K, V> map;

    public Form() {
        this.map = new HashMap<>();
    }

    public void add(K key, V value) {
        this.map.put(key, value);
    }

    public V get(K key) {
        return this.map.get(key);
    }
}
