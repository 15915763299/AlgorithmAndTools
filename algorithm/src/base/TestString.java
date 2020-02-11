package base;

/**
 * @author 尉涛
 * @date 2020-02-07 11:07
 **/
public class TestString {

    public static void main(String[] args) {
        String a = "123";
        String b = "123";
        System.out.println(a == b);
        System.out.println(a.equals(b));

        System.out.println("------------------");
        String c = new String("123");
        System.out.println(a == c);
        System.out.println(a.equals(c));
    }


}
