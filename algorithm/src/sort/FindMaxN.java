package sort;

/**
 * @author 尉涛
 * @date 2020-02-06 18:20
 * 基于快速排序，寻找第n大的数字
 **/
public class FindMaxN {

    public static void main(String[] args) {
        int[] array = {2, 4, 9, 3, 5, 0, 1};
        System.out.println(findMaxN(array, 0, array.length - 1, 2));
        System.out.println(findMinN(array, 0, array.length - 1, 2));
    }

    private static int findMaxN(int array[], int left, int right, int n) {
        int index = partitionMax(array, left, right);
        if (index == n - 1) {
            return array[index];
        } else if (index < n - 1) {
            return findMaxN(array, index + 1, right, n);
        } else {
            return findMaxN(array, left, index - 1, n);
        }
    }

    private static int partitionMax(int array[], int left, int right) {
        int Pivot = array[left];
        int l = left, r = right;
        while (l < r) {
            while (array[r] < Pivot && l < r) r--;//大->小
            while (array[l] >= Pivot && l < r) l++;
            Sort.swap(array, l, r);
        }
        Sort.swap(array, left, r);
        return l;
    }

    //*************************************************************************

    private static int findMinN(int array[], int left, int right, int n) {
        int index = partitionMin(array, left, right);
        if (index == n - 1) {
            return array[index];
        } else if (index < n - 1) {
            return findMinN(array, index + 1, right, n);
        } else {
            return findMinN(array, left, index - 1, n);
        }
    }

    private static int partitionMin(int array[], int left, int right) {
        int Pivot = array[left];
        int l = left, r = right;
        while (l < r) {
            while (array[r] > Pivot && l < r) r--;//小->大
            while (array[l] <= Pivot && l < r) l++;
            Sort.swap(array, l, r);
        }
        Sort.swap(array, left, r);
        return l;
    }
}
