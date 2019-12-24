package linear;

/**
 * 一个数组中有没有三个数字的合为 target 所代表的数字，并输一个 boolean（找到一组即可）
 */
public class ThreeSum1 {

    public static void main(String args[]) {
        int[] temp = {8, -2, 1, 7, 5, 4, 6};
        System.out.println(threeSun(temp, 5));
    }

    /**
     * 在SumTwo的基础上加一次遍历，时间复杂度 O(n²)
     */
    private static boolean threeSun(int nums[], int target) {

        if (nums == null || nums.length < 3) {
            return false;
        }

        for (int num : nums) {
            int two[] = TwoSum1.twoSum(nums, target - num);
            if (nums[two[0]] + nums[two[1]] + num == target) {
                System.out.println(
                        nums[two[0]] + " + " + nums[two[1]] + " + " + num
                                + " = " + (nums[two[0]] + nums[two[1]] + num)
                );
                return true;
            }
        }
        return false;
    }

}
