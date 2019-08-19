package others;

/**
 * 对象赋值
 */
public class ObjectAssignment {

    private static class A {
    }

    private static void assign(A a) {
        a = new A();
    }

    public static void main(String[] args) {
        A a = null;
        assign(a);
        System.out.println(a == null);
    }

}
