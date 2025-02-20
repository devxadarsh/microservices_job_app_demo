import java.util.HashMap;

public class HashMapExample {
    public static void main(String[] args) {
        HashMap<Integer, String> hs = new HashMap<>();
        hs.put(1,"one");
        hs.put(2,"two");
        hs.put(3,"three");
        hs.put(4,"four");
        hs.put(5,"five");

        hsIterator(hs);
    }

    private static void hsIterator(HashMap<Integer, String> hs) {
        int size = hs.size();
        hs.forEach((integer, string) -> System.out.println("Key: " + integer +" && value: " +string));
        for (int i = 0; i <size; i++) {
            System.out.println(hs.get(i+1));
        }
    }
}
