package structure.linear;

import java.util.HashMap;
import java.util.Map;

/**
 * 求一个数组中有没有两个数字的合为 target 所代表的数字，并输出这两个数字的 index（找到一组即可）
 */
public class TwoSum1 {

    public static void main(String args[]) {
        int[] temp = {8, -2, 1, 7, 5, 4};
        int[] result = twoSum(temp, 5);
        System.out.println(result[0] + "," + result[1]);
    }

    /**
     * 将 target - nums[i] 放入HashMap中，依次检查后面的数字是否等于这个值
     * 时间复杂度：O(n)
     */
    static int[] twoSum(int[] nums, int target) {

        int[] res = {0, 0};
        if (nums == null || nums.length < 2) {
            return res;
        }

        Map<Integer, Integer> remainSet = new HashMap<>();//<remain, index> 使用HashMap时注意标出Key与Value的意义

        for (int i = 0; i < nums.length; i++) {
            if (remainSet.containsKey(nums[i])) {
                res[0] = remainSet.get(nums[i]);
                res[1] = i;
                return res;
            } else {
                remainSet.put(target - nums[i], i);
            }
        }

        return res;
    }

    //    public int[] twoSum(int[] nums, int target) {
    //        int[] result = new int[2];
    //        HashMap<Integer, Integer> map = new HashMap<>(nums.length);
    //        for (int i = 0; i < nums.length; i++) {
    //            if (map.get(nums[i]) != null) {
    //                result[0] = map.get(nums[i]);
    //                result[1] = i;
    //                return result;
    //            }
    //            map.put(target - nums[i], i);
    //        }
    //        return result;
    //    }

}
