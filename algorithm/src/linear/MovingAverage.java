package linear;

import java.util.ArrayList;
import java.util.List;

/**
 * Moving Average from Data Stream
 * <p>
 * Give an integer stream and a window size, calculate the
 * moving average of all integers in the sliding window.
 * MovingAverage(size): Initialize the data structure with size
 * next(val): Calculate the next average value after adding val
 */
public class MovingAverage {

    public static void main(String args[]) {
        MovingAverage movingAverage = new MovingAverage(3);
        movingAverage.next(3);
        movingAverage.next(1);
        movingAverage.next(1);
        movingAverage.next(2);
        movingAverage.next(5);
    }

    private int windowSize;
    private List<Integer> list;

    public MovingAverage(int windowSize) {
        this.windowSize = windowSize;
        this.list = new ArrayList<>();
    }

    public void next(int num) {
        list.add(num);
        int sum = 0;
        int count = 0;
        for (int i = list.size() - 1; i >= 0 && i >= list.size() - windowSize; i--) {
            sum += list.get(i).doubleValue();
            count++;
        }
        double result = (double)sum/count;
        System.out.println("count: " + count + " result: " + result);
    }
}
