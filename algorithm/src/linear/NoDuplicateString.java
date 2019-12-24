package linear;

public class NoDuplicateString {

    public static void main(String args[]) {
        String test = "abc1de1fghijklmnopqrstuvwxyz";
        System.out.println(lengthOfLongestSubstring(test));
    }

//    public int lengthOfLongestSubstring(String s) {
//        int n = s.length(), ans = 0;
//        int[] index = new int[128]; // current index of character
//        // try to extend the range [i, j]
//        for (int j = 0, i = 0; j < n; j++) {
//            i = Math.max(index[s.charAt(j)], i);
//            ans = Math.max(ans, j - i + 1);
//            index[s.charAt(j)] = j + 1;
//        }
//        return ans;
//    }

    public static int lengthOfLongestSubstring(String s) {
        int[] count = new int[128];
        int length = 0;
        int result = 0;
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            int index = chars[i];
            if (count[index] == 0) {
                // 直接记录第一次出现的位置，0作为临界值要避免，这里+1
                count[index] = i + 1;
                length++;
            } else {
                if (length > result) {
                    result = length;
                }
                length = 0;
                //回到重复字母第一次出现的位置
                i = count[index] - 1;
                count = new int[128];
            }
        }

        if (length > result) {
            result = length;
        }
        return result;
    }

//    public static int lengthOfLongestSubstring(String s) {
//        int[] count = new int[128];
//        int length = 0;
//        int result = 0;
//        char[] chars = s.toCharArray();
//
//        for (int i = 0; i < chars.length; i++) {
//            int index = chars[i];
//            if (count[index] == 0) {
//                count[index]++;
//                length++;
//            } else {
//                if (length > result) {
//                    result = length;
//                }
//                length = 0;
//                //回到重复字母第一次出现的位置
//                do {
//                    i--;
//                } while (chars[i] != index);
//                count = new int[128];
//            }
//        }
//
//        if(length > result){
//            result = length;
//        }
//        return result;
//    }

}
