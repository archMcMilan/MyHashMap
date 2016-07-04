package hashmap;

import java.util.Map;

/**
 * Created by Artem on 30.06.16.
 */
public class MyHashMap<K, V> {
    private Entry[] buckets;
    private float loadFactor;
    private int size = 0;

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    class Entry<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.hash = key.hashCode();
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

        public void setNext(Entry<K, V> next) {
            this.next = next;
        }
    }

    /**
     * Default constructor initialized buckets array's capacity and loadFactor with default values
     */
    public MyHashMap() {
        buckets = new Entry[DEFAULT_INITIAL_CAPACITY];
        loadFactor = DEFAULT_LOAD_FACTOR;
    }

    /**
     * Constructor with one parameter initialized loadFactor with the given value and ensure buckets'
     * capacity with default value
     *
     * @param loadFactor
     */
    public MyHashMap(float loadFactor) {
        buckets = new Entry[DEFAULT_INITIAL_CAPACITY];
        this.loadFactor = loadFactor;
    }

    /**
     * Constructor with two values initialized buckets array's capacity and loadFactor with the given values
     *
     * @param capacity
     * @param loadFactor
     */
    public MyHashMap(int capacity, float loadFactor) {
        buckets = new Entry[capacity];
        this.loadFactor = loadFactor;
    }

    /**
     * Method put element in the map by key.hashCode(). If size+1 is greater then loadFactor allow, causes growBuckets()
     * method that doubles buckets' capacity. If map contains element with the same key, method override V value to new.
     *
     * @param key   null - throw NullPointerException().
     * @param value
     * @return V value of the put element
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (size + 1 > loadFactor * buckets.length) {//check if amount of elements is more than loadFactor allow
            growBuckets();
        }
        Entry<K, V> entry = new Entry<>(key, value, null);
        size++;//increment size
        int hash = hash(entry.hash);
        if (buckets[hash] == null) {//bucket cell hasn't element in it
            buckets[hash] = entry;
            return entry.getValue();
        } else {//bucket cell has element
            Entry<K, V> current = buckets[hash];
            if (current.getKey().equals(entry.getKey())) {//if buckets contains element with same key
                current.setValue(entry.getValue());//change value to new
                return entry.getValue();
            }
            while (current.next != null) {//find last element in chain by next filed in Enrty
                current = current.next;
                if (current.getKey().equals(entry.getKey())) {//if buckets contains element with same key
                    return entry.getValue();
                }
            }
            current.setNext(entry);
            return entry.getValue();
        }

    }

    /**
     * Method finds V value of the element with the given key in the map.
     *
     * @param key null - return null, otherwise
     * @return V value
     */
    public V get(K key) {
        int hash = hash(key.hashCode());
        if (buckets[hash] == null) {//map doesn't contain elements with such key
            return null;
        }
        Entry<K, V> currentWithSameKeyHashCode = buckets[hash];
        if (currentWithSameKeyHashCode.getKey().equals(key)) {//check element with same key.hashCode() on key equals
            return currentWithSameKeyHashCode.getValue();//find with same key
        } else {
            while (currentWithSameKeyHashCode.next != null) {//go through all elements with same key.hashCode()
                currentWithSameKeyHashCode = currentWithSameKeyHashCode.next;
                if (currentWithSameKeyHashCode.getKey().equals(key)) {//if buckets contains element with same key
                    return currentWithSameKeyHashCode.getValue();
                }
            }
            return null;//map contains elements with same key.hashCode(), but not with same key
        }

    }

    /**
     * Method remove elements from the map with the given key. Method save chain of elements with the same
     * key.hashCode() values when remove the element with suck key
     *
     * @param key null - return null, otherwise
     * @return V value
     */
    public V remove(K key) {
        int hash = hash(key.hashCode());
        if (buckets[hash] == null) {//map doesn't contain elements with such key
            return null;
        }

        size--;//decrement size due to the fact that we remove element

        Entry<K, V> previous = null;
        Entry<K, V> currentWithSameKeyHashCode = buckets[hash];

        while (currentWithSameKeyHashCode != null) { //we have reached last entry node of bucket.
            if (currentWithSameKeyHashCode.key.equals(key)) {
                if (previous == null) {  //delete first entry node.
                    buckets[hash] = buckets[hash].next;
                    return (V) buckets[hash].getValue();
                } else {
                    previous.next = currentWithSameKeyHashCode.next;
                    return currentWithSameKeyHashCode.getValue();
                }
            }
            previous = currentWithSameKeyHashCode;
            currentWithSameKeyHashCode = currentWithSameKeyHashCode.next;
        }
        return null;//map contains elements with same key.hashCode(), but not with same key
    }

    /**
     * Method count in which bucket need to put element with such key.hashCode();
     *
     * @param hashCode
     * @return position of the bucket in the buckets array
     */
    private int hash(int hashCode) {
        return Math.abs(hashCode) % buckets.length;
    }

    /**
     * Method doubles buckets array's capacity
     */
    private void growBuckets() {
        size = 0;//make size 0 due to the fact that elements will be put in new array with method put()
        Entry<K, V>[] temp = buckets;
        buckets = new Entry[buckets.length * 2];//make bucket capacity twice bigger
        for (Entry<K, V> t : temp) {//go through all elements and put them in new array;
            if (t == null) {
                continue;
            }
            Entry<K, V> current = t;
            put(current.getKey(), current.getValue());
            while (current.next != null) {
                current = current.next;
                put(current.getKey(), current.getValue());
            }
        }
    }

    public int getSize() {
        return size;
    }
}
