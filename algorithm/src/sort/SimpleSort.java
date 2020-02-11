package sort;

/**
 * 简易排序
 * Insertion sort faster than Selection sort  (Number of comparison, best case)
 * Insertion sort faster than Bubble sort  (Number of swaps)
 */
public class SimpleSort {

    public static void main(String[] args) {
        int[] array = {8, 1, 5, 9, 3, 1, 6, 0};
        print(array);
        System.out.println();

        //bubbleSort(array);
        //selectionSort(array);
        insertionSort(array);
        Sort.printArray(array);
    }

    /**
     * 冒泡排序
     * Best Case O(n);  Average Case O(n2);  Worst Case O(n2);  Memory O(1);
     * <p>
     * Compares each pair of adjacent items and swaps them if they are in the wrong order.
     * The pass through the list is repeated until no swaps are needed.
     * Each pass through the list will sort one element.
     */
    private static void bubbleSort(int[] array) {
        for (int unSorted = array.length; unSorted > 0; unSorted--) {
            for (int i = 0; i < unSorted - 1; i++) {
                if (array[i] > array[i + 1]) {
                    Sort.swap(array, i, i + 1);
                }
            }
        }
    }

    /**
     * 选择排序
     * Best Case O(n2);  Average Case O(n2);  Worst Case O(n2);  Memory O(1);
     * <p>
     * Select the minimum/maximum value of rest of elements and swap with it.
     * The selection is repeated until no element is rest.
     * Each selection will sort one element.
     */
    private static void selectionSort(int[] array) {
        for (int unSorted = array.length; unSorted > 0; unSorted--) {
            final int lastElement = unSorted - 1;
            int assumeMax = lastElement;
            for (int i = 0; i < unSorted; i++) {
                if (array[assumeMax] < array[i]) {
                    assumeMax = i;
                }
            }

            if (assumeMax != lastElement) {
                Sort.swap(array, assumeMax, lastElement);
            }
        }
    }


    /**
     * 插入排序
     * Best Case O(n);  Average Case O(n2);  Worst Case O(n2);  Memory O(1);
     * <p>
     * Find the inserting position in sorted part and insert the element to sort there.
     */
    private static void insertionSort(int[] array) {
        for (int sorted = 0, j = sorted; sorted < array.length - 1; j = ++sorted) {
            int elementToSort = array[sorted + 1];
            while (elementToSort < array[j]) {
                array[j + 1] = array[j];
                print(array, sorted, elementToSort, j);
                if (j-- == 0) {
                    break;
                }
            }
            array[j + 1] = elementToSort;
            print(array);
            System.out.println("insertIndex: " + (j + 1));
            System.out.println("--------------------------------");
        }
    }


    // print------------------------------------------
    private static void print(int[] array) {
        for (int item : array) {
            System.out.print(item + " ");
        }
    }


    private static void print(int[] array, int sorted, int elementToSort, int j) {
        for (int item : array) {
            System.out.print(item + " ");
        }
        System.out.println("sorted: " + sorted + ", elementToSort: " + elementToSort);

        printArrow(j);
    }

    private static void printArrow(int startPosition) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startPosition * 2; i > 0; i--) {
            stringBuilder.append(" ");
        }
        stringBuilder.append("j");
        System.out.println(stringBuilder.toString());
    }
}
