public class ClassToTest {
    @Test 
    public void A() {
        System.out.println("A");
    }
    @Test 
    public void B() {
        System.out.println("B");
        throw new IllegalStateException();
    }
    @BeforeClass 
    public static void run1() {
        System.out.println("BeforeClass");
    }
    @AfterClass 
    public static void run2() {
        System.out.println("AfterClass");
    }
    @Before 
    public void run3a() {
        System.out.println("A before");
    }
    @Before 
    public void run3b() {
        System.out.println("B before");
    }
    @After 
    public void run4() {
        System.out.println("After");
        throw new IllegalStateException();
    }
}