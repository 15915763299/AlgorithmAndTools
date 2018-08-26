package sort;

import java.util.Arrays;

/**
 * 参考：https://blog.csdn.net/Holmofy/article/details/71168530
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] array = {8, 11, -4, 9, 3, 1, 6, 10, 0};
        deScanSwapSort(array);
        //fillSort(array);
        //forwardScanSort(array);
        Sort.printArray(array);
    }

    /**
     * 双端扫描交换 Double-End Scan and Swap
     * 注意 ：i 与 j 必须交错，如果两者相遇之后就停止比较，那相遇点所在的元素就没有和中心点进行比较。
     */
    private static void deScanSwapSort(int[] items) {
        deScanSwapSort(items, 0, items.length - 1);
    }

    private static void deScanSwapSort(int[] items, int start, int end) {
        if (start < end) {
            int pivot = items[start];

            int i = start + 1, j = end;
            while (i <= j) {
                while (i <= j && items[i] < pivot)
                    i++;
                while (i <= j && items[j] >= pivot)
                    j--;
                if (i <= j) {
                    Sort.swap(items, i, j);
                }
            }
            Sort.swap(items, start, j);// 将中心点交换到中间。
            deScanSwapSort(items, start, j - 1);// 中心点左半部分递归
            deScanSwapSort(items, j + 1, end);// 中心点右半部分递归
        }
    }

    /**
     * 赋值填充方式
     * 一端挖坑一端填充
     */
    private static void fillSort(int[] items) {
        fillSort(items, 0, items.length - 1);
    }

    private static void fillSort(int[] items, int start, int end) {
        if (start < end) {
            int pivot = items[start];
            int i = start, j = end;
            while (i < j) {
                while (i < j && items[j] > pivot)
                    j--;
                items[i] = items[j];
                while (i < j && items[i] <= pivot)
                    i++;
                items[j] = items[i];
            }
            // 相遇后i == j，此处是个坑
            items[i] = pivot;
            fillSort(items, start, i - 1);
            fillSort(items, i + 1, end);
        }
    }

    /**
     * 单向扫描划分方式
     */
    private static void forwardScanSort(int[] items) {
        forwardScanSort(items, 0, items.length - 1);
    }

    private static void forwardScanSort(int[] items, int start, int end) {
        if (start < end) {
            int pivot = items[start];
            int i = start, j = start + 1;
            while (j <= end) {
                if (items[j] < pivot) {
                    i++;
                    Sort.swap(items, i, j);
                }
                j++;
            }
            Sort.swap(items, start, i);
            forwardScanSort(items, start, i - 1);
            forwardScanSort(items, i + 1, end);
        }
    }
}
