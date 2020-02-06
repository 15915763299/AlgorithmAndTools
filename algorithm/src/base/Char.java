package base;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * @author 尉涛
 * @date 2020-02-06 11:27
 * java char 2字节 2^16(65536)
 **/
public class Char {

    public static void main(String[] args) throws Exception {
        System.out.println((char) (-100000000) + " - " +
                (char) (-100000) + " - " +
                (char) (-100) + " - " +
                (char) -10);
        System.out.println((char) 20 + " - " +
                (char) 50 + " - " +
                (char) 100 + " - " +
                (char) (100000));
        System.out.println((int) 'ﾜ' + " - " +
                (char) 65436 + " - " +
                (char) 65535 + " - " +
                (char) 65534);

        System.out.println("啊啊啊".toCharArray().length);//3,一个中文一个char
        System.out.println("啊".getBytes().length);//汉字在utf-8中占三个字节（还有四字节的）
        System.out.println("1".getBytes().length);

        System.out.println("-------------------------------------------------");

        char c = 'a';
        char cc = '中';
        String str = "a";
        String strr = "中";
        String s = "ａ";
        System.out.println("编码为UTF8:");
        System.out.println("char-英文字符-字节长度:" + getBytesUTF8(c).length);
        System.out.println("char-中文字符-字节长度:" + getBytesUTF8(cc).length);
        System.out.println("编码为GBK(默认编码):");
        System.out.println("char-英文字符-字节长度:" + getBytesGBK(c).length);
        System.out.println("char-中文字符-字节长度:" + getBytesGBK(cc).length);
        System.out.println("-------------------------------");
        System.out.println("编码为UTF8");
        System.out.println("String-英文字母-字节长度:" + str.getBytes("utf-8").length);
        System.out.println("String-中文字符-字节长度:" + strr.getBytes("utf-8").length);
        System.out.println("编码为GBK:");
        System.out.println("String-英文字母-字节长度:" + str.getBytes("GBK").length);
        System.out.println("String-中文字符-字节长度:" + strr.getBytes("GBK").length);
        System.out.println("String-英文字母（全角）所占字节长度:" + s.getBytes("GBK").length);


        System.out.println("-------------------------------------------------");

        long a = 65536;
        System.out.println(a * a);
    }


    public static byte[] getBytesUTF8(char c) {
        Charset cs = Charset.forName("utf-8");
        CharBuffer cb = CharBuffer.allocate(1);
        cb.put(c);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    public static byte[] getBytesGBK(char c) {
        Charset cs = Charset.forName("GBK");
        CharBuffer cb = CharBuffer.allocate(1);
        cb.put(c);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }
}
