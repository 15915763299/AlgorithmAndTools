package structure.linear;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Simplify the absolute path for a unix-style file
 *
 * ".."  back
 * "."   stay
 */
public class SimplifyPath {

    public static void main(String args[]) {
        String path1 = "/etc/";
        System.out.println(simplify(path1));

        String path2 = "/tmp/../algo/./../etc/rrr";
        System.out.println(simplify(path2));

    }

    public static String simplify(String path) {
        String[] temp = path.split("/");
        Deque<String> stack = new LinkedList<>();

        for (String str : temp) {
            if (str.equals("..")) {
                stack.pollLast();
            } else if (!str.equals(".")) {
                stack.offerLast(str);
            }
        }

        StringBuilder result = new StringBuilder();
        while (stack.peekFirst() != null) {
            if (!"".equals(stack.peekFirst())) {
                result.append("/").append(stack.pollFirst());
            } else {
                stack.pollFirst();
            }
        }
        return result.toString();
    }

}
