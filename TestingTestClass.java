
import java.util.Map;
public class TestingTestClass {
    public static void main(String argv[]) {
        Map<String,Throwable> m = Unit.testClass("ttt");
        for(Map.Entry<String,Throwable> e: m.entrySet()) {
            System.out.println(e.getKey() + ":"+ e.getValue());
        }
    }
}