package structure.linear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Design and implement a TwoSum class which supports the
 * the following operations: Add & Find
 * Add(x): Add x into the internal data structure of the class.
 * Find(target): Find if there are any pairs sum up to target.
 * <p>
 * Problem Description
 * Signature          Description
 * interface:add      public void add(int toAdd)
 * interface:find     public boolean find(int toFind)
 * Assumption         You have enough memory to store the stream of data
 * Corner Case        No data received -> return false in find()
 * <p>
 * Key to Data Stream Problem: What Data Structure?
 * This question: List + Map
 */
public class TwoSum2 {

    public static void main(String args[]) {
        TwoSum2 twoSum2 = new TwoSum2();
        twoSum2.add(3);
        twoSum2.add(2);
        twoSum2.add(7);
        System.out.println(twoSum2.find(5));
        System.out.println(twoSum2.find(4));
        twoSum2.add(-1);
        twoSum2.add(3);
        System.out.println(twoSum2.find(6));
    }

    // <number, frequency>
    private final Map<Integer, Integer> map = new HashMap<>();
    // Maintain a list to for iteration in find,
    // to avoid iterate the map, which can be really slow
    private final List<Integer> list = new ArrayList<>();

    public void add(int number) {
        list.add(number);
//        Integer frequency = map.get(number);
//        if (frequency == null) {
//            map.put(number, 1);
//        } else {
//            map.put(number, frequency + 1);
//        }
        //Java8, replace the code above
        map.merge(number, 1, (oldValue, value) -> oldValue + value);
    }

    public boolean find(int value) {
        for (int curKey : list) {
            int target = value - curKey;
            Integer count = map.get(target);
            if(count != null){
                if(curKey != target || (curKey == target && count >= 2)){
                    return true;
                }
            }
        }
        return false;
    }
}