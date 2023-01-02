public class Objectb {
    private boolean b;
    public Objectb(boolean b){
        this.b = b;
    }
    public Objectb isEqualTo(boolean b2){
        if(b!=b2) {
            throw new NullPointerException("booleans are not equal");
        }
        return this;
    }
    public Objectb isTrue(){
        if(!b) {
            throw new NullPointerException("booleans are not equal");
        }
        return this;
    }
    public Objectb isFalse(){
        if(b) {
            throw new NullPointerException("booleans are not equal");
        }
        return this;
    }
}