package linear;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Evaluate Reverse Polish Rotation
 * <p>
 * Deque: A linear collection that supports element insertion and removal at both ends
 * offerLast()    Insert in last
 * pollLast()     Retrieve and remove last
 * peekLast()     Retrieve, but do not remove last
 */
public class ReversePolish {

    public static void main(String args[]) {
        // ( 2 + 1 ) * 3
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        System.out.println(evalRPN(tokens1));

        // ( 6 + 9 / 3 ) * 9
        String[] tokens2 = {"6", "3", "9", "/", "+", "9", "*"};
        System.out.println(evalRPN(tokens2));

        // ( 2 + 1 ) * 3
        char[] tokens3 = {'(', '2', '+', '1', ')', '*', '3'};
        System.out.println(evalInFix(tokens3));

        // (( 3 + 4 ) * 5  - 4 * 2 ) + 1
        char[] tokens4 = {'(', '(', '3', '+', '4', ')', '*', '5', '-', '4', '*', '2', ')', '+', '1'};
        System.out.println(evalInFix(tokens4));
    }

    /**
     * Assumption    1.Only numbers and operators in the String array (No parentheses)
     * 2.The expression is valid in terms of arithmetic rules
     * Corner Case   Null or empty input -> 0
     */
    public static int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }

        Deque<Integer> stack = new LinkedList<>();//Store all numbers to be calculate

        for (String str : tokens) {
            switch (str) {
                case "+":
                    stack.offerLast(stack.pollLast() + stack.pollLast());
                    break;
                case "-":
                    int a1 = stack.pollLast();
                    int b1 = stack.pollLast();
                    stack.offerLast(b1 - a1);
                    break;
                case "*":
                    stack.offerLast(stack.pollLast() * stack.pollLast());
                    break;
                case "/":
                    int a2 = stack.pollLast();
                    int b2 = stack.pollLast();
                    stack.offerLast(b2 / a2);
                    break;
                default:
                    stack.offerLast(Integer.parseInt(str));
                    break;
            }
        }
        return stack.pollLast();
    }

    /**
     * Assumption    1.Numbers, operators and parentheses(圆括号) may be in the char array
     * 2.The expression is valid in terms of arithmetic rules
     * Corner Case   Null or empty input -> 0
     */
    public static int evalInFix(char[] tokens) {
        //Corner Case Checked
        Deque<Integer> valStack = new LinkedList<>();//Stack to store numbers
        Deque<Character> opStack = new LinkedList<>();//Stack to store operators

        for (char c : tokens) {
            if (c == '(') {
                opStack.offerLast(c);
            } else if (c == ')') {
                while (opStack.peekLast() != '(') {
                    valStack.offerLast(cal(valStack.pollLast(), opStack.pollLast(), valStack.pollLast()));
                }
                opStack.pollLast();// Poll'('
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                // Case 3: Operators other than parenthese
                while (!opStack.isEmpty() && isLowerThan(c, opStack.peekLast())) {
                    valStack.offerLast(cal(valStack.pollLast(), opStack.pollLast(), valStack.pollLast()));
                }
                opStack.offerLast(c);//Offer current operator
            } else {//Case 4: number
                valStack.offerLast(Integer.parseInt(c + ""));
            }
        }

        //Calculate all numbers rest in stack​
        while (!opStack.isEmpty()) {
            valStack.offerLast(cal(valStack.pollLast(), opStack.pollLast(), valStack.pollLast()));
        }

        return valStack.pollLast();
    }

    private static int cal(int num1, char operator, int num2) {
        int result = 0;
        switch (operator) {
            case '+':
                result = num2 + num1;
                break;
            case '-':
                result = num2 - num1;
                break;
            case '*':
                result = num2 * num1;
                break;
            case '/':
                result = num2 / num1;
        }
        return result;
    }

    private static boolean isLowerThan(char cur, char toPeek) {
        return (toPeek == '*' || toPeek == '/') &&
                (cur == '+' || cur == '-');
    }
}