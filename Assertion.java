public class Assertion {
    static Objecto assertThat(Object o) {
        return new Objecto(o);
    }
    static Objects assertThat(String s) {
        return new Objects(s);
    }
    static Objectb assertThat(boolean b) {
        return new Objectb(b);
    }
    static Objecti assertThat(int i) {
	    return new Objecti(i);
    }
}