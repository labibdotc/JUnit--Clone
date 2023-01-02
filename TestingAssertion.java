public class TestingAssertion{
    public static void main(String argv[]) {
        Objects b = Assertion.assertThat("b");
        Assertion.assertThat("").isNotEqualTo(b).startsWith("").isNotNull();
        Objecto c = new Objecto(String.class);
        Assertion.assertThat(String.class).isNotEqualTo(null).isNotNull();
       //Assertion.
        //o = o.isNull();
        // o = o.contains("");
        // o = o.isEmpty();
    }
}