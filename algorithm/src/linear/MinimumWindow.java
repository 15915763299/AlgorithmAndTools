package linear;

/**
 * Given a string S and a string T, find a substring in S with the minimum
 * length which contains all the characters in T. Time Complexity: O(n)
 * <p>
 * Problem Description:
 * Signature                  Description
 * Input        String s, String t         Find substring in S of pattern t
 * Output       String                     Target substring
 * Assumption   The substring doesn't need to maintain the same order of characters in T
 * Corner Case  Empty input or no match in s-> Return empty string
 */
public class MinimumWindow {

    public static void main(String[] args) {
        String S = "bxcdebbdc";
        String T = "dcb";

        System.out.println(minWindow(S, T));
    }

    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0
                || t.length() > s.length()) {
            return "";
        }

        int[] map = new int[128];
        int count = t.length(), start = 0, end = 0, minLen = Integer.MAX_VALUE, startIndex = 0;
        for (char c : t.toCharArray()) {
            map[c]++;
        }

        char[] chS = s.toCharArray();
        while (end < chS.length) {
            if (map[chS[end++]]-- > 0) {
                count--;
            }
            while (count == 0) {
                if (end - start < minLen) {
                    startIndex = start;
                    minLen = end - start;
                }
                if (map[chS[start++]]++ == 0) {
                    count++;
                }
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : new String(chS, startIndex, minLen);
    }


}
