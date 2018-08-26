package sort;

class Sort {

    static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
    }

    static void swap(int[] items, int i, int j) {
        int tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

}
