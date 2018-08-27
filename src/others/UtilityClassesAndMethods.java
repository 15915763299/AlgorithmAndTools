package others;

import java.util.*;

/**
 * Java 自带工具类
 */
public class UtilityClassesAndMethods {

    public static void main(String[] args) {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Object[] objects = {new Object(), new Object(), new Object()};

        //自动填充相同元素
        Arrays.fill(integers, 3, 7, 10);
        //数组直接转字符串
        System.out.println(Arrays.deepToString(integers));
        System.out.println(Arrays.toString(objects));
        //数组转换List<T>
        List<Integer> integerList = Arrays.asList(integers);
        //二分查找
        //int index = Arrays.binarySearch(test, 3);
        //复制
        //Integer[] copy = Arrays.copyOfRange(test, 1,3);
        //深层比较
        //Arrays.deepEquals();
        //深层哈希码（这个不太懂有什么用）
        //Arrays.deepHashCode();
        //排序
        //Arrays.sort();

        //两个Collection是否无交集
        //System.out.println(Collections.disjoint(integerList, integerList));
        //某个Object在Collection中出现的次数
        //System.out.println(Collections.frequency(integerList, 10));
        //...

        //拼接
        String[] strings = {"1", "2", "3"};
        System.out.println(String.join("-", strings));

        //Character.isDigit();
        //Character.isUpperCase();
        //Character.getNumericValue();
        //...
    }


}
