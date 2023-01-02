public class Objects {
    private String s;
    public Objects (String s) {
        //System.out.println(s);
        this.s = s;
    }
    public Objects isNotNull(){
        if(this.s == null) throw new NullPointerException("object is null");
        return this;
    }
    public Objects isNull(){
        if(this.s != null) throw new NullPointerException("object isn't null");
        return this;
    }
    public Objects isEqualTo(Object o2){
        
        if(!this.s.equals(o2)) throw new NullPointerException("s1 isn't equal to s2");
        return this;
    }

    public Objects isNotEqualTo(Object o2){
        if(this.s.equals(o2)) throw new NullPointerException("s1 is equal to s2");
        return this;
    }
    public Objects startsWith(String s2){
        if(!s.substring(0, s2.length()).equals(s2)) throw new NullPointerException("o1 doesn't start with s2");
        return this;
    }
    public Objects isEmpty(){
        if(!s.equals("")) throw new NullPointerException("o1 isn't an empty string");
        return this;
    }
    public Objects contains(String s2){
        if(!s.contains(s2)) throw new NullPointerException("s doesn't contain s2");
        return this;
    }


}