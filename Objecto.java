import java.lang.*;
import java.lang.reflect.*;
public class Objecto {
    private Object o;
    public Objecto (Object object) {
        this.o = object;
    }
    public Objecto isNotNull(){
        if(this.o == null) throw new NullPointerException("object is null");
        return this;
    }
    public Objecto isNull(){
        if(this.o != null) throw new NullPointerException("object isn't null");
        return this;
    }
    public Objecto isEqualTo(Object o2){
        if(!this.o.equals(o2)) throw new NullPointerException("o1 isn't equal to o2");
        return this;
    }
    public Objecto isNotEqualTo(Object o2){
        if(this.o.equals(o2)) throw new NullPointerException("o1 is equal to o2");
        return this;
    }
    public Objecto isInstanceOf(Class c){
        try{
            if(! c.isInstance(this.o)) throw new NullPointerException("o1 isn't an instance of c");
        } catch (NullPointerException e) {
            throw new NullPointerException("o1 isn't an instance of c");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        return this;
    }
}