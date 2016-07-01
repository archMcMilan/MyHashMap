package hashmap;

/**
 * Created by Artem on 30.06.16.
 */
public class MyHashMap<K, V> {
    private Entry[] buckets;
    private float loadFactor;

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public MyHashMap() {
        buckets = new Entry[DEFAULT_INITIAL_CAPACITY];
        loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public MyHashMap(float loadFactor) {
        buckets = new Entry[DEFAULT_INITIAL_CAPACITY];
        this.loadFactor = loadFactor;
    }

    public MyHashMap(int capacity, float loadFactor) {
        buckets = new Entry[capacity];
        this.loadFactor = loadFactor;
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        Entry<K, V> entry = new Entry<>(hash(key), key, value, null);

        if (size() +1> loadFactor * buckets.length) {//check if amount of elements is more than loadFactor allow
            growBuckets();
        }

        if (buckets[entry.getHash()] == null) {//bucket cell hasn't element in it
            buckets[entry.getHash()] = entry;
            return entry.getValue();
        } else {//bucket cell has element
            Entry<K, V> current = buckets[entry.getHash()];
            if(current.getKey().equals(entry.getKey())){//if buckets contains element with same key
                current.setValue(entry.getValue());//change value to new
                return entry.getValue();
            }
            while (current.getNext() != null) {//find last element in chain by next filed in Enrty
                current = current.getNext();
                if(current.getKey().equals(entry.getKey())){//if buckets contains element with same key
                    return entry.getValue();
                }
            }
            current.setNext(entry);
            return entry.getValue();
        }

    }

    public V get(K key) {
        int hash=hash(key);
        if(buckets[hash]==null){//map doesn't contain elements with such key
            return null;
        }
        Entry<K,V>currentWithSameKey= buckets[hash];
        if(currentWithSameKey.getKey().equals(key)){//check element with same key.hashCode() on key equals
            return currentWithSameKey.getValue();//find with same key
        }else{
            while(currentWithSameKey.getNext()!=null){//go through all elements with same key.hashCode()
                currentWithSameKey = currentWithSameKey.getNext();
                if(currentWithSameKey.getKey().equals(key)){//if buckets contains element with same key
                    return currentWithSameKey.getValue();
                }
            }
            return null;//map contains elements with same key.hashCode(), but not with same key
        }

    }

    public V remove(K key) {
        int hash=hash(key);
        if(buckets[hash]==null){//map doesn't contain elements with such key
            return null;
        }
        Entry<K,V>currentWithSameKey= buckets[hash];
        if(currentWithSameKey.getKey().equals(key)){//check element with same key.hashCode() on key equals
            buckets[hash]=currentWithSameKey.getNext();//remove with same key
            return currentWithSameKey.getValue();
        }else{
            while(currentWithSameKey.getNext()!=null){//go through all elements with same key.hashCode()
                currentWithSameKey = currentWithSameKey.getNext();
                if(currentWithSameKey.getKey().equals(key)){//if buckets contains element with same key
                    buckets[hash]=currentWithSameKey.getNext();//remove with same key
                    return currentWithSameKey.getValue();
                }
            }
            return null;//map contains elements with same key.hashCode(), but not with same key
        }
    }


    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private void growBuckets() {
        Entry<K, V>[] temp = buckets;
        buckets = new Entry[buckets.length * 2];
        for (Entry<K, V> t : temp) {
            if(t==null){
                continue;
            }
            Entry<K, V> current = t;
            put(current.getKey(), current.getValue());
            while (current.getNext() != null) {
                current=current.getNext();
                put(current.getKey(),current.getValue());
            }
        }
    }

    public int size(){
        int size=0;
        for (Entry<K, V> t : buckets) {
            if (t == null) {
                continue;
            }
            size++;
            Entry<K, V> current = t;
            while (current.getNext() != null) {
                current = current.getNext();
                size++;
            }
        }
        return size;
    }
}
