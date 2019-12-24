package com.demo.sm3;

import com.demo.GMBaseUtil;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.util.Arrays;

/**
 * SM3算法是一种哈希算法、散列算法，可以作为签名算法
 * （加密算法可逆，签名算法不可逆）
 * 散列算法：将任意长度的消息映射成为定长的散列值的算法，以该散列值消息摘要作为认证符。
 * <p>
 * 此算法对输入长度小于2的64次方的比特消息；
 * 经过填充和迭代压缩，生成长度为256比特的杂凑值，其中使用了异或，模，模加，移位，与，或，非运算，由填充，迭代过程，消息扩展和压缩函数所构成。
 * 具体算法及运算示例见SM3标准。
 */
public class DemoSm3 extends GMBaseUtil {

    public static void main(String[] args) {
        //原始数据
        byte[] src = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("src: " + ByteUtils.toHexString(src));

        //生成签名
        byte[] sign = generateSign(src);
        System.out.println("sign: " + ByteUtils.toHexString(sign));

        //验证签名
        boolean verify = verify(src, sign);
        System.out.println(verify);

        //使用key生成签名
        byte[] key = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        sign = generateHmacSign(key, src);
        System.out.println("hmac: " + ByteUtils.toHexString(sign));
    }

    /**
     * 利用SM3生成签名
     */
    public static byte[] generateSign(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 验证签名
     */
    public static boolean verify(byte[] srcData, byte[] sm3Hash) {
        byte[] newHash = generateSign(srcData);
        return Arrays.equals(newHash, sm3Hash);
    }

    /**
     * HMAC - Hash-based Message Authentication Code
     * 密钥相关的哈希运算消息认证码
     * 消息认证码：它是一个需要密钥的算法，可以对可变长度的消息进行认证，把输出的结果作为认证符。
     * <p>
     * HMAC可以配合各种散列算法进行签名的生成，常见的有：
     * 算法种类                摘要长度
     * HmacMD5                 128
     * HmacSHA1                160
     * HmacSHA256              256
     * HmacSHA384              384
     * HmacSHA512              512
     */
    public static byte[] generateHmacSign(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }
}
