package sort;

/**
 * 参考：https://blog.csdn.net/Holmofy/article/details/71168530
 */
public class QuickSort {

    public static void main(String[] args) {
//        int[] array = {8, 11, -4, 9, 3, 1, 6, 10, 0};
        int[] array = {4, 1, 5, 7, 3, 9, 12};
        print(array);
        System.out.println();

        //方法一
        deScanSwapSort(array);
        //方法二
        //fillSort(array);
        //方法三
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

            // 这里+1排除pivot，接着将数分为两半，左边小于pivot，右边大于pivot
            int i = start + 1, j = end;
            while (i <= j) {
                while (i <= j && items[i] < pivot)
                    i++;
                while (i <= j && items[j] >= pivot)
                    j--;
                if (i <= j) {
                    Sort.swap(items, i, j);
                    printArray(items, start, end, pivot, i, j, "ij交换");
                }
            }
            //最后ij会交错，即：

            // i →    ← j   ij互相靠近
            //     ji       最后停在两个相邻的数字上，并交错
            printArray(items, start, end, pivot, i, j, "ij交错");


            //将pivot与ij交汇点交换，这里是j，就是在左半部分的最后一个数字
            Sort.swap(items, start, j);// 将中心点交换到中间。
            printArray(items, start, end, pivot, i, j, "pj换位");
            System.out.println("-------------------------");

            deScanSwapSort(items, start, j - 1);// 中心点左半部分递归
            deScanSwapSort(items, j + 1, end);// 中心点右半部分递归
            // 中心点不用进入递归！！！
        }
    }

    // 方法二 *********************************************************************

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

    // 方法三 *********************************************************************

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


    // 输出数组 *********************************************************************
    private static void print(int[] array) {
        for (int item : array) {
            System.out.print(item + " ");
        }
    }

    private static void printArray(int[] items, int start, int end, int pivot, int i, int j, String tip) {
        //数组
        for (int item : items) {
            System.out.print(item + " ");
        }
        System.out.print("iStartIndex: " + (start + 1) + ", jStartIndex: " + end + ", pivot: " + pivot);
        System.out.println();

        //箭头
        printArrow(i, j, items.length, tip);
    }

    private static void printArrow(int i, int j, int length, String tip) {
        boolean isIFirst = i <= j;
        int first = isIFirst ? i : j;
        int second = (!isIFirst ? i : j) - first;
        if (second > 0) {
            second--;
        }
        int space = length - first - second - 1;

        StringBuilder stringBuilder = new StringBuilder();
        for (; first > 0; first--) {
            stringBuilder.append("  ");
        }

        if (i == j) {
            stringBuilder.append("ij");
        } else {
            stringBuilder.append(isIFirst ? "i " : "j ");
            for (; second > 0; second--) {
                stringBuilder.append("  ");
            }
            stringBuilder.append(isIFirst ? "j" : "i");
        }

        for (; space > 0; space--) {
            stringBuilder.append("  ");
        }
        stringBuilder.append("(").append(tip).append(")");

        System.out.println(stringBuilder.toString());
    }
}
