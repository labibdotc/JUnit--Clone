import java.util.*;
public class ClassToTest2 {
    //, @ListLength(min = 1, max = 4) List<@ListLength(min = 1, max = 4) List<@IntRange(min =3, max = 7) Integer>>lst
    @Property
    public boolean ab(@ForAll(name = "foo", times=10)Object o) {
        //System.out.println("line 6 in class test 2");
        return true;
    }
    @Property public boolean testFoo(@ForAll(name="genIntSet", times=10) Object o) {
        Integer[] s = (Integer[]) o;
        //s.add("foo");
        //return true;
        System.out.println(s.length);
        return true;
        //return !s.contains(1);
    }

    int count = 0;
    public Object genIntSet() {
        // Set s = new HashSet<Object>();
        // for (int i=0; i<count; i++) { s.add(i); }
        // count++;
        // return s;
        count++;
        return new Integer[count];
    }
    // @Property 
    // public boolean absss(@IntRange(min=1, max=10) Integer i, @StringSet(strings={"1", "4"}) String s1, @StringSet(strings={"abb", "bba"}) String s2) {
    //    System.out.println("calling absss(" + i + "," + s1+ "," + s2 +");");
    //     return false;
    // }
    // //@ListLength(min = 1, max = 4) List<@ListLength(min = 1, max = 4) List<@IntRange(min =3, max = 7) Integer>>lst
    // @Property 
    // public boolean lll(@IntRange(min=1, max=10) Integer j, @StringSet(strings={"1", "4"}) String s1, @StringSet(strings={"abb", "bba"}) String s2, @ListLength(min = 0, max = 2) List<@IntRange(min =5, max = 7) Integer>lst) {
    //     System.out.println("calling lll(" + j + "," + s1+ "," + s2 + ",");
    //     System.out.print("<");
    //     for(int i = 0; i < lst.size(); i++) {
    //         if(i == lst.size() - 1) System.out.print(lst.get(i)+"");
    //         else
    //         System.out.print(lst.get(i)+", ");
    //     }
    //     System.out.print(">");
    //     System.out.println(")");
    //     return true;
    // }
    // @Property 
    // public boolean lalala(@IntRange(min=1, max=10) Integer j, @StringSet(strings={"1", "4"}) String s1, @StringSet(strings={"abb", "bba"}) String s2, @ListLength(min = 0, max = 5) List<@StringSet(strings={"abb", "bba", "ccc"}) String>lst) {
    //     System.out.println("calling lalala(" + j + "," + s1+ "," + s2 + ",");
    //     System.out.print("<");
    //     for(int i = 0; i < lst.size(); i++) {
    //         if(i == lst.size() - 1) System.out.print(lst.get(i)+"");
    //         else
    //         System.out.print(lst.get(i)+", ");
    //     }
    //     System.out.print(">");
    //     System.out.println(")");
    //     return true;
    // }
    // @Property 
    // public boolean ll(@ListLength(min = 0, max = 2) List<@IntRange(min =5, max = 7) Integer>lst) {
    //    //System.out.println("calling absss(" + i + "," + s1+ "," + s2 +");");
    //     System.out.print("ll(");
    //     for(int i = 0; i < lst.size(); i++) {
    //         if(i == lst.size() - 1) System.out.print(lst.get(i)+"");
    //         else
    //         System.out.print(lst.get(i)+", ");
    //     }
    //     System.out.println(")");
    //     return true;
    // }
}