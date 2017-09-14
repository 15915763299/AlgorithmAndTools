package structure.linear;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Evaluate Reverse Polish Rotation
 */
public class ReversePolish {


    public static int evalRPN(String[] tokens){
        if(tokens == null || tokens.length == 0){
            return 0;
        }

        Deque<Integer> stack = new LinkedList<>();//Store all numbers to be calculate

        for(int i = 0; i < tokens.length; i++){
            switch (tokens[i]){
                case "+":
                    stack.offerLast(stack.pollLast() + stack.pollLast());
                    break;
                case "-":
                    int a1 = stack.pollLast();
            }
        }
    }

}
