package linear;

/**
 * @author 尉涛
 * @date 2020-02-03 21:25
 * 将一个字符串转换为一个整数
 **/
public class StrToNum {

    public static void main(String[] args) {
//        System.out.println(strToNum("!@#$%^&*("));
//        System.out.println(strToNum(")_+~`="));
//        System.out.println(strToNum("-[]{}|;'"));
//        System.out.println(strToNum(":,.<>? "));
//        System.out.println(strToNum(" "));
//        System.out.println(strToNum("abc"));
//        System.out.println(strToNum(null));
        System.out.println(strToNum("ﾜ"));

    }

    private static long strToNum(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        StringBuilder sb = new StringBuilder();

        for (char c : str.toCharArray()) {
            sb.append((int) c);
        }
        System.out.println(sb.toString());

        long result = 0;
        try {
            result = Long.valueOf(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
