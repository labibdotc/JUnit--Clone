import java.util.*;
public class AssertionTest{
    public static void main(String args[]) {
        
        Assertion.assertThat(true).isEqualTo(true);
        Assertion.assertThat("string").isNotEqualTo("s");
        Assertion.assertThat("string").isEqualTo("string");
        Assertion.assertThat("string").startsWith("str");
        Assertion.assertThat("string").contains("str");
        //Assertion.assertThat("string").isEqualTo(Assertion.assertThat("string"));
        String s = "CSG";
        Assertion.assertThat(s).isNotNull().startsWith("CS");
        ArrayList<Integer>b = new ArrayList<>();
        Assertion.assertThat(b).isEqualTo(b);
        Assertion.assertThat(b).isNotNull();
        try {
            Assertion.assertThat(b).isInstanceOf(ArrayList.class);
        } catch(Throwable e) {
            System.out.println(e.getMessage());
        }
        b = null;
        Object o = null;
        Assertion.assertThat(o).isNull();
        Assertion.assertThat(1).isLessThan(2);
        Assertion.assertThat(false).isEqualTo(!true);
        String m = "CS";
        Assertion.assertThat(m).isNotNull().isEqualTo("CS");
        Object c = null;
        Object a = new LinkedList<Integer>();
        Assertion.assertThat(c).isNull();
        Assertion.assertThat(a).isNotNull().isEqualTo(a);

        String string = "heyy";
        Assertion.assertThat(string).isNotNull().isEqualTo("heyy").contains("e");
        Assertion.assertThat("").isNotNull().isEmpty().startsWith("");


        
    }
}