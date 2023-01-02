import java.util.*;

public class TestingQuickCheck {
    public static void printArgs(Object[] args) {
        if(args == null) {System.out.print("null"); return;}
        for(int i = 0; i < args.length; i++) {
            if(i == args.length - 1) System.out.print(args[i]+"");
            else
            System.out.print(args[i]+", ");
        }
    }
    public static void main(String args[]) {
        Map<String, Object[]> res = Unit.quickCheckClass("ClassToTest2");
        // for(Map.Entry<String,Object[]> e: res.entrySet()) {
        //     System.out.print(e.getKey() + ":"+ "(");
        //     printArgs(e.getValue());
        //     System.out.println(")");
        // }
    }
}