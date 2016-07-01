package hashmap;

import java.util.Map;

/**
 * Created by Artem on 30.06.16.
 */
public class Entry<K,V> implements Map.Entry<K,V>{
    private final int hash;
    private final K key;
    private V value;
    private Entry<K,V> next;

    public Entry(int hash,K key, V value, Entry<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }


    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        if (value == null)
            throw new NullPointerException();
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public int getHash() {
        return hash;
    }

    public Entry<K, V> getNext() {
        return next;
    }

    public void setNext(Entry<K, V> next) {
        this.next = next;
    }
}
