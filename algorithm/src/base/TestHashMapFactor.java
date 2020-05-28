package base;

/**
 * @author 尉涛
 * @date 2020-02-26 14:56
 **/
public class TestHashMapFactor {

    //平衡占用空间与查找时间
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public static void main(String[] args) {
        for (int i = 4; i < 15; i++) {
            float result = DEFAULT_LOAD_FACTOR * power2(i);
            System.out.println("乘积(都是整数)：" + result);
            System.out.println("二进制：" + Integer.toBinaryString((int) result));
        }

    }

    /**
     * 2的n次方
     */
    private static int power2(int n) {
        return (int) Math.pow(2, n);
    }

}
