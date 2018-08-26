import java.util.Arrays;
import java.util.List;

public class UtilityClassesAndMethods {

    public static void main(String[] args) {
        Integer[] test = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

        //自动填充相同元素
        Arrays.fill(test, 3,7,10);
        //数组直接转字符串
        System.out.println(Arrays.deepToString(test));
        //数组转换List<T>
        //List<Integer> integers = Arrays.asList(test);
        //二分查找
        //int index = Arrays.binarySearch(test, 3);
        //复制
        //Integer[] copy = Arrays.copyOfRange(test, 1,3);
        //深层比较
        //Arrays.deepEquals();
        //深层哈希码
        //Arrays.deepHashCode();


    }


}
