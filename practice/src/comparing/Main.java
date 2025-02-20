package comparing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<MyClass> list = new ArrayList<>();
        list.add(new MyClass(2, "Charlie"));
        list.add(new MyClass(2, "Sharlie"));
        list.add(new MyClass(1, "Alice"));
        list.add(new MyClass(1, "Blice"));
        list.add(new MyClass(3, "Bob"));

//        // Sort using Comparable
//        Collections.sort(list);
//        System.out.println("Sorted by id (Comparable): " + list);
//
//        // Sort using Comparator
//        Collections.sort(list, new MyClassNameComparator());
//        System.out.println("Sorted by name (Comparator): " + list);

//        // Sort using lambda
//        Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
//        System.out.println("Sorted by name (Lambda): " + list);

        // Sort using Comparator static method
        list.sort(Comparator.comparingInt(MyClass::getId).thenComparing(MyClass::getName));
        System.out.println("Sorted by name (Comparator.comparing): " + list);
    }
}
