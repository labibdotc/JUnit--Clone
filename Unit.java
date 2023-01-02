import java.util.*;
//import java.lang.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
import java.lang.Exception.*;

public class Unit {
    /*
    test flags: @test, @Before, @After, @BeforeClass, @AfterClass
    implements beforeclasstests->i*(before->test[i]->after)->afterclasstests
    throws exception if a test has more than one flag
    Should enforce beforeclass and afterclass to be on static public functions
    To get exceptions of associated tests for map value, unwrap InvocationTargetException
    */
    public static Map<String, Throwable> testClass(String name) {
        
        Map<String, Throwable>res = new HashMap<>();
        try {
            Class<?>c = Class.forName(name);
            Method[] methods= c.getDeclaredMethods();
            List<Method>Test = new LinkedList<>();
            List<Method>Before = new LinkedList<>();
            List<Method>After = new LinkedList<>();
            List<Method>BeforeClass = new LinkedList<>();
            List<Method>AfterClass = new LinkedList<>();
            getTests(methods, Test, Before, After, BeforeClass, AfterClass);
            sortTests(Test, Before, After, BeforeClass, AfterClass);
            executeClassTests(BeforeClass);
            executeTests(c, res, Before, Test, After);
            executeClassTests(AfterClass);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        
        
        return res;
	//throw new UnsupportedOperationException();
    }

    public static Map<String, Object[]> quickCheckClass(String name) {
        Map<String, Object[]>res = new HashMap<>();
        try{ 
            Class<?>c = Class.forName(name);
            Method[] methods= c.getDeclaredMethods();
            List<Method>property = new LinkedList<>();
            getPropertyTests(methods, property);
            sort(property);
            Constructor<?>cons = c.getConstructor();
            Object o = cons.newInstance();
            execProperties(property, o, res, c);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
	    
        return res;
    }
    private static void execProperties(List<Method>methods, Object o, Map<String, Object[]>res, Class<?>c) {
        for(Method method: methods) {
            Annotation [][] annotations = method.getParameterAnnotations();
            List<List<Object>>params = new ArrayList<>();
            AnnotatedType[] parTypes = method.getAnnotatedParameterTypes();
            for(int i = 0; i < annotations.length; i++) {
                params.add(new ArrayList<>());
                Annotation anot  = annotations[i][0];
                    //System.out.println("here");
                oneParameterProcessing(params.get(i), anot, parTypes[i], c, o);
            }
            //list of parameter of lists
            // for(int j = 0; j < params.get(1).size(); j++) {
            //     for(int i = 0; i < params.get(0).size(); i++) {
            //         System.out.println(params.get(0).get(i) + " : " + params.get(1).get(j) );
            //     } System.out.println("");
            // }
            List<Object>curr = new ArrayList<>();
            //Object[]args = new Object[params.size()];
            List<Object[]> all = new ArrayList<>();
            comb(params.size(), 0, params, curr, all);
            boolean failed = false;
            for(int i = 0; i < 100 && i < all.size() && !failed; i++) {
                try{
                    if(method.invoke(o, all.get(i)).equals(false)) {
                        res.put(method.getName(), all.get(i));
                        failed = true;
                    }
                } catch(Throwable t) {
                    res.put(method.getName(), all.get(i));
                    failed = true;
                }
                
            }
            if(!failed) {
                res.put(method.getName(), null);
            }
        }
    }
    /*
    Problem: Undetermined amount of for loops
    Use: creates n amount of loops using recursion

    */
    private static void comb(int n, int loop, List<List<Object>>params, List<Object> curr, List<Object[]> all) {
        if (n > 0) {
            for(int i = 0; i < params.get(loop).size(); i++) {
                curr.add(params.get(loop).get(i));
                //curr[loop] = params.get(loop).get(i);
                comb(n-1, loop+1, params, curr, all);
                curr.remove(loop);
                //curr[loop] //as curr is passed around by reference
            }
        } else {
            Object[]args = new Object[loop];
            for(int i = 0; i < loop; i++) {
                args[i] = curr.get(i);
            }
            all.add(args);
        }
    }

    private static void oneParameterProcessing(List<Object>params, Annotation anot, AnnotatedType anotType, Class <?>c, Object o){
                Class <? extends Annotation> t = anot.annotationType(); //@IntRange-@ListLength-@ForAll-@StringSet
                //System.out.println(anotType.getType().getTypeName());
                if(t.equals(IntRange.class)) {
                    IntRange range = (IntRange) anot;
                    for(int j = range.min(); j <= range.max(); j++) {
                        params.add(j);
                    }
                    //System.out.println("An int range: " + range.min() + " -> " + range.max() );
                } else if(t.equals(StringSet.class)) {
                    StringSet set = (StringSet) anot;
                    String[]strings = set.strings();
                    for(String string: strings) {
                        params.add(string);
                    }
                    //System.out.println("A string list of size: " + strings.length);
                } 
                else if(t.equals(ListLength.class)) {
                    
                    ListLength range = (ListLength) anot;
                    int min = range.min(), max = range.max();
                    //anottype 
                    AnnotatedType[] subs= ((AnnotatedParameterizedType)anotType).getAnnotatedActualTypeArguments();
                    // for(int i = 0; i < subs.length; i++) {
                    //     System.out.println(subs[i].getType().getTypeName());
                    //     System.out.println(i);
                    // }
                    
                    

                    List<Object>inner = new ArrayList<>();
                    //params.add(inner);
                    oneParameterProcessing(inner, subs[0].getAnnotations()[0], subs[0], c, o); 
                    //System.out.println(inner.size());
                    //inner will have [1,2,3, 4]
                    //we want [], [1], [2], [3], [1,2] .. in params
                    //n = min->max (incl) call comb
                    List<Object> curr = new ArrayList<>();
                    //System.out.println(params.size());
                    for(int i = min; i <= max; i++) {
                        combListLength(params, curr, inner, 0, i);
                    }
                    

                    //System.out.println(params.size());
                    //
                    //params should have lists of combinations of that

                } else if(t.equals(ForAll.class)) {
                    ForAll forall = (ForAll) anot;
                    String name  = forall.name();
                    int times = forall.times();
                    //System.out.println(c.getName());
                    try{
                        
                        Method method = c.getMethod(name);
                        // Constructor <?> cons = c.getConstructor();
                        // Object o = c.newInstance();
                        for(int i = 0; i < times && i < 100; i++) {
                            params.add(method.invoke(o));
                        }
                    } catch(Throwable e) {
                        System.out.println(e.getMessage());
                    }
                    //System.out.println(params.size());
                }
    }
    /*
    Problem: Undetermined amount of for loops
    Use: creates n amount of loops using recursion

    */
    private static void combListLength(List<Object> params , List<Object> curr, List<Object>tocomb, int start, int max){
        //if(curr.size() > max) return;
        if(curr.size() == max) {
                List<Object> perms = new ArrayList<>();
                //gets all perms of this subset
                perm(perms, new ArrayList<>(), curr);
                for(Object o: perms) {
                    params.add(o);
                }
                if(!params.contains(curr))
                params.add(new ArrayList<>(curr));
                return;

        }
        //insertion happens here

        //gets all subsets
        for(int i = start; i < tocomb.size(); i++){
            curr.add(tocomb.get(i));
            //if(i!=max) 
            //combListLength(params, curr, tocomb, i+1, max);
            combListLength(params, curr, tocomb, i, max);
            curr.remove(curr.size() - 1);
        }
    }


    private static void perm(List<Object> perms, List<Object> curr, List<Object>toperm){
        if(curr.size() == toperm.size()){
            perms.add(new ArrayList<>(curr));
        } else{
            for(int i = 0; i < toperm.size(); i++){ 
                if(curr.contains(toperm.get(i))) continue; // element already exists, skip
                curr.add(toperm.get(i));
                perm(perms, curr, toperm);
                curr.remove(curr.size() - 1);
            }
        }
    } 
    // private static void combListLength(int n, int loop, List<Object>tocomb, List<Object>curr, List<Object> all) {
    //     if (n > 0) {
    //         for(int i = 0; i < tocomb.size(); i++) {
    //             curr.add(tocomb.get(i));
    //             combListLength(n-1, loop+1, tocomb, curr, all);
    //             curr.remove(i);
    //             //curr[loop] //as curr is passed around by reference
    //         }
    //     } else {
    //         all.add(curr);
    //     }
    // }
    private static void processList(List<Object>list, AnnotatedType curr) {
       //parTypes[i].getDeclaredAnnotation(IntRange.class).toString();
       //System.out.println(curr.getType().getTypeName());
        Annotation[]anott = curr.getDeclaredAnnotations();
        Class <? extends Annotation> toBeCurr = anott[0].annotationType();
        if(toBeCurr.equals(AnnotatedType.class)) {
            //AnnotatedType newCurr = (AnnotatedType)toBeCurr;
            //processList(list, (AnnotatedType)toBeCurr);
        } else {
            return;
        }
    }
    private static void getPropertyTests(Method[]methods, List<Method>property) {
        for(Method method: methods) { //get all methods
            Annotation[] annotations = method.getDeclaredAnnotations(); //get flags for that method
            //Type type = anot.getType();
            int flag_count = 0; //if more than one throw
            String flag = ""; //stores flag
            for(Annotation anot: annotations) { //
                Class <? extends Annotation> t = anot.annotationType();
                if(t.equals(Property.class)) {
                    property.add(method);
                }
            }
        }
    }

    private static void getTests(Method[]methods, List<Method>Test, List<Method>Before, List<Method>After, List<Method>BeforeClass, List<Method>AfterClass) {
            //System.out.println(methods.length);
        for(Method method: methods) { //get all methods
            //System.out.println(method.getName());
                //Class<Annotation>d = Annotation.class;
            Annotation[] annotations = method.getDeclaredAnnotations(); //get flags for that method
            //Type type = anot.getType();
            int flag_count = 0; //if more than one throw
            String flag = ""; //stores flag
            for(Annotation anot: annotations) { //
                Class <? extends Annotation> t = anot.annotationType();
                //if(anot instanceof Test) System.out.println(method.getName());
                // Object save = t.cast(anot);
                // for(Method meth: t.getMethods()) {
                //     System.out.println(meth.getName());
                // }
                if (t.equals(Test.class)) {
                    flag_count++;
                    flag = "@Test";
                } else if (t.equals(Before.class)) {
                    flag_count++;
                    flag = "@Before";
                } else if (t.equals(After.class)) {
                    flag_count++;
                    flag = "@After";
                } else if (t.equals(AfterClass.class)) {
                    flag_count++;
                    flag = "@AfterClass";
                } else if (t.equals(BeforeClass.class)) {
                    flag_count++;
                    flag = "@BeforeClass";
                }
            }
            if (flag_count > 1) {
                throw new IllegalStateException("Tests should have no more than one annotation");
            }
            if (flag.equals("@Test")) {
                Test.add(method);
            } else if (flag.equals("@Before")) {
                Before.add(method);
            } else if (flag.equals("@After")) {
                After.add(method);
            } else if (flag.equals("@AfterClass")) {
                AfterClass.add(method);
            } else if (flag.equals("@BeforeClass")) {
                BeforeClass.add(method);
            }
        }
    }
    private static void sortTests(List<Method>Test, List<Method>Before, List<Method>After, List<Method>BeforeClass, List<Method>AfterClass) {
        sort(Test);
        sort(Before);
        sort(After);
        sort(BeforeClass);
        sort(AfterClass);
    }
    
    private static void sort(List<Method>methods) {
        Collections.sort(methods, (m1,m2) -> {
            return m1.getName().compareTo(m2.getName());
        });
    }

    private static void executeClassTests(List<Method> ClassTests) throws Throwable {
        for(Method method: ClassTests){
            
            try {
                method.invoke(null);
                assert(Modifier.isStatic(method.getModifiers()));
            } catch(InvocationTargetException e) {
              throw e.getCause();
            } catch(Exception e) {
                
            }
        }
    }

    private static void executeTests(Class<?> c, Map<String, Throwable> res, List<Method> Before, List<Method> Test, List<Method> After) throws Throwable {
       
        Constructor<?>cons = c.getConstructor();
        Object o = cons.newInstance();
        for(Method method: Test){
            invoke(o, Before);
                try{
                    method.invoke(o);
                    res.put(method.getName(), null); //you put only @test
                } catch(Exception e) { //double check that
                    res.put(method.getName(), e.getCause());
                }
            invoke(o, After);
        }
    }
    private static void invoke(Object o, List<Method>tests) throws Throwable {
        for(Method method: tests) {
            try{
                method.invoke(o);
            } catch(InvocationTargetException e) {
                //System.out.println("here");
                throw e.getCause();
            } catch(Exception e) {
                //System.out.println(e.getMessage());
            }
        }
    }
    
}