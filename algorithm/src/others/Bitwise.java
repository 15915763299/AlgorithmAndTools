package others;

/**
 * 位运算
 */
public class Bitwise {

    private static final int CLOUMN_WIDTH = 35;

    public static void main(String[] args) {
        System.out.println("------------------------------十进制-------------------------------二进制-------------------------------操作");

        int num = -10;
        printNum(num, "无");
        printNum(num << 1, "num << 1");
        printNum(num >> 1, "num >> 1");
        printNum(num >>> 1, "num >>> 1");

        num = 10;
        printNum(num, "无");
        printNum(num << 1, "num << 1");
        printNum(num >> 1, "num >> 1");
        printNum(num >>> 1, "num >>> 1");

        // 负数转化过程
        // 以负数-5为例：
        //    1.先将-5的绝对值转换成二进制，即为0000 0101；
        //    2.然后求该二进制的反码，即为 1111 1010；
        //    3.最后将反码加1，即为：1111 1011
        int testNum = -10;
        System.out.println(Integer.toBinaryString(~Math.abs(testNum) + 1));
        System.out.println(Integer.toBinaryString(testNum));

        //>>>操作：包括符号位一起右移
        //!!!! >>>操作有些时候看似会溢出，但是不会 !!!!
        //Java源码有一行是这样的： int mid = (high + low) >>> 1; 注意这里不会溢出
        int result = (Integer.MAX_VALUE * 2) >>> 1;
        System.out.println(Integer.toBinaryString(result) + "   " + result);
    }

    private static void printNum(int num, String operation) {
        printRow(num + "", Integer.toBinaryString(num), operation);
    }

    private static void printRow(String... columns) {
        for (String column : columns) {
            System.out.print(formatCloumnString(column));
        }
        System.out.println();
    }

    private static String formatCloumnString(String str) {
        if (str.length() > 35) {
            return str.substring(0, 35);
        } else {
            int differencing = CLOUMN_WIDTH - str.length();
            StringBuilder stringBuilder = new StringBuilder();
            while (differencing-- > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(str);
            return stringBuilder.toString();
        }
    }

}

//输出
//------------------------------十进制-------------------------------二进制-------------------------------操作
//                                -10   11111111111111111111111111110110                                  无
//                                -20   11111111111111111111111111101100                           num << 1
//                                 -5   11111111111111111111111111111011                           num >> 1
//                         2147483643    1111111111111111111111111111011                          num >>> 1
//                                 10                               1010                                  无
//                                 20                              10100                           num << 1
//                                  5                                101                           num >> 1
//                                  5                                101                          num >>> 1
// 11111111111111111111111111110110
// 11111111111111111111111111110110
// 1111111111111111111111111111111   2147483647
