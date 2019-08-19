package others;

/**
 * 浮点数减法
 * 与2进制有关，这是2进制的无限小数
 */
public class FloatSubtraction {

    public static void main(String args[]) {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        System.out.println(String.valueOf(a));
        System.out.println(String.valueOf(b));
        System.out.println(a == b);
    }

}
