/**
 * @author 尉涛
 * @date 2020-02-06 00:31
 * 斐波那契数列，黄金分割线
 * F(1)=1，F(2)=1, F(n)=F(n-1)+F(n-2)（n>=3，n∈N*）
 **/
public class FibonacciSequence {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(f(40));
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        System.out.println(f2(40));
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        System.out.println(f3(40));
        System.out.println(System.currentTimeMillis() - start);
    }


    /**
     * 最耗资源的实现方式
     */
    private static int f(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        return f(n - 1) + f(n - 2);
    }

    /**
     * 对于数据量大的，能用循环就不要用递归
     */
    private static int f2(int n) {
        int[] temp = new int[n + 1];
        temp[0] = 0;
        temp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            temp[i] = temp[i - 1] + temp[i - 2];
        }
        return temp[n];
    }

    /**
     * 降低空间复杂度
     */
    private static int f3(int n) {
        int f0 = 0;
        int f1 = 1;

        if (n <= 0) {
            return f0;
        } else if (n == 1) {
            return f1;
        }
        int fn_2 = f0, fn_1 = f1;
        int fn = 0;
        //n = 2开始
        for (int i = 2; i <= n; i++) {
            fn = fn_1 + fn_2;
            fn_2 = fn_1;
            fn_1 = fn;
        }
        return fn;
    }

}
