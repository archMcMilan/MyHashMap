import hashmap.MyHashMap;

/**
 * Created by Artem on 30.06.16.
 */
public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer,String> myHashMap=new MyHashMap<>(4,0.75f);
        myHashMap.put(1,"Artem");
        myHashMap.put(2,"Vyka");
        myHashMap.put(4,"Ivan");
        myHashMap.put(5,"Olia");
        System.out.println(myHashMap.get(2));
        System.out.println(myHashMap.size());
        System.out.println(myHashMap.remove(2));
        System.out.println(myHashMap.size());
    }
}
