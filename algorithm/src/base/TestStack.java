package base;

import java.util.Stack;

/**
 * @author 尉涛
 * @date 2020-02-07 12:29
 **/
public class TestStack {

    public static void main(String[] args){
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        System.out.println(stack.peek());//只读不出
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.size());
    }

}
