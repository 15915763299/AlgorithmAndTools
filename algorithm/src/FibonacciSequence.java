/**
 * @author 尉涛
 * @date 2020-02-06 00:31
 * 斐波那契数列，黄金分割线
 * F(1)=1，F(2)=1, F(n)=F(n-1)+F(n-2)（n>=3，n∈N*）
 **/
public class FibonacciSequence {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(f(45));
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        System.out.println(f2(45));
        System.out.println(System.currentTimeMillis() - start);
    }


    /**
     * 最耗资源的实现方式
     */
    private static int f(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else if (n < 1) {
            return 0;
        }
        return f(n - 1) + f(n - 2);
    }

    /**
     * 对于数据量大的，能用循环就不要用递归
     */
    private static int f2(int n) {
        int[] temp = new int[n];
        temp[0] = 1;
        temp[1] = 1;
        for (int i = 2; i < n; i++) {
            temp[i] = temp[i - 1] + temp[i - 2];
        }
        return temp[n - 1];
    }

}
