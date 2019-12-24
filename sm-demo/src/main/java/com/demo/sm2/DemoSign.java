package com.demo.sm2;

import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * SM2签名生成过程
 */
public class DemoSign {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException {
        //获取SM2密钥对生成器
        KeyPairGenerator kpg = Sm2KeyGenerator.generate();

        // 获取密钥对
        KeyPair keyPair = kpg.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // ------------------------ SM2未压缩公钥 ----------------------------
        // 椭圆曲线公钥的点坐标
        ECPoint pubKeyPointQ = ((BCECPublicKey) publicKey).getQ();
        System.out.println("X: \n" + pubKeyPointQ.getXCoord());
        System.out.println("Y: \n" + pubKeyPointQ.getYCoord());
        // 将其表示为SM2未压缩的公钥为
        System.out.println("SM2 public key: \n"
                + "04"
                + pubKeyPointQ.getXCoord().toString()
                + pubKeyPointQ.getYCoord().toString() + "\n"
        );
        // ------------------------ SM2未压缩公钥 -------------------------------

        System.out.println("Public key: \n" + Hex.toHexString(publicKey.getEncoded()));
        System.out.println("Private key: \n" + Hex.toHexString(privateKey.getEncoded()));

        // 生成SM2sign with sm3 签名验签算法实例
        Signature signature = Signature.getInstance("SM3withSm2", new BouncyCastleProvider());

        /*
         * 签名
         */
        // 签名需要使用私钥，使用私钥 初始化签名实例
        signature.initSign(privateKey);
        // 签名原文
        byte[] plainText = "你好".getBytes(StandardCharsets.UTF_8);
        // 写入签名原文到算法中
        signature.update(plainText);
        // 计算签名值
        byte[] signatureValue = signature.sign();
        System.out.println("signature: \n" + Hex.toHexString(signatureValue));

        /*
         * 验签
         */
        // 签名需要使用公钥，使用公钥 初始化签名实例
        signature.initVerify(publicKey);
        // 写入待验签的签名原文到算法中
        signature.update(plainText);
        // 验签
        System.out.println("Signature verify result: " + signature.verify(signatureValue));
    }

}
