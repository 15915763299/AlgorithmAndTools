package sort;

import java.util.Scanner;

/**
 * @author 尉涛
 * @date 2020-02-06 12:22
 **/
public class BinarySearch {

    public static void main(String[] args) {
        int[] list = {11, 12, 13, 15, 19, 20};
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number :");
        int number = input.nextInt();
        System.out.println("The position of" + "  " + binarySearch(list, 0, list.length - 1, number));
        input.close();
    }

    private static int binarySearch(int[] list, int low, int high, int number) {
        if (high >= low) {
            int mid = (low + high) / 2;
            if (list[mid] > number) {
                return binarySearch(list, low, mid - 1, number);
            } else if (list[mid] < number) {
                return binarySearch(list, mid + 1, high, number);
            } else {//list[mid] == number
                return mid;
            }
        }
        return -1;
    }

}
