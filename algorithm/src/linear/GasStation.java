package linear;

/**
 * Suppose there is a circle with n petrol pumps on that circle.
 * You are given two sets of data:
 * 1. gas[i]:  the amount of petrol that (i)th petrol pump has.
 * 2. cost[i]: distance from tih petrol pump to (i+1)th petrol pump.
 * Calculate and return the starting gas station's index if you can
 * travel around the circuit once, otherwise return -1;
 */
public class GasStation {

    public static void main(String[] args) {
        int[] gas = {3, 4, 3, 6, 7, 1, 2};
        int[] cost = {2, 4, 5, 1, 5, 1, 3};
        //position:  0---1---2---3---4---5---6
        //clean:      -1   0  -2   5   2   0  -1

        System.out.println(canCompleteCircuit(gas, cost));
    }


    private static int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null) {
            return -1;
        }
        if (gas.length != cost.length) {
            return -1;
        }

        int start = gas.length - 1;
        int end = 0;
        int sum = gas[start] - cost[start];
        System.out.println("start: " + start + "; end: " + end + "; sum: " + sum);

        while (end < start) {
            //Case 1: sum < 0 --> a new start needed
            if (sum < 0) {
                sum += gas[--start] - cost[start];
            } else {//Case 2: sum >= 0 --> try to move more
                sum += gas[end] - cost[end++];
            }
            System.out.println("start: " + start + "; end: " + end + "; sum: " + sum);
        }
        return sum >= 0 ? start : -1;
    }

}
