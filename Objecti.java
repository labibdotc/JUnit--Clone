public class Objecti {
    private int i;
    public Objecti(int i) {
        this.i = i;
    }
    public Objecti isEqualTo(int i2){
        if(i != i2) throw new NullPointerException("i isn't equal to i2");
        return this;
    }
    public Objecti isLessThan(int i2){
        if(i >= i2) throw new NullPointerException("i isn't less than with i2");
        return this;
    }
    public Objecti isGreaterThan(int i2){
        if(i <= i2) throw new NullPointerException("i isn't greater than with i2");
        return this;
    }
}