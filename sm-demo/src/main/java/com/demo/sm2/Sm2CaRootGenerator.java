package com.demo.sm2;

import com.demo.GMBaseUtil;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.misc.NetscapeCertType;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.X509KeyUsage;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 自签名密钥存储，使用KeyStore储存证书文件
 * .p12证书可能既包含公钥也包含私钥，带密码验证。
 */
public class Sm2CaRootGenerator extends GMBaseUtil {

    public static final String ROOT_P12_CERT_PATH = "F://root-cert.p12";
    public static final String ROOT_CERT_PATH = "F://root-cert.cer";
    public static final String ROOT_CERT_PWD = "123456";

    /**
     * 生成KeyStore
     */
    public static void main(String[] args) throws Exception {
        // 生成密钥生成器
        KeyPairGenerator keyPairGenerator = Sm2KeyGenerator.generate();
        // 产生密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成证书
        X509Certificate certificate = genRootCert(keyPair);
        // 使用KeyStore储存证书文件
        makeCertFileByKeyStore(certificate, keyPair, ROOT_P12_CERT_PATH, ROOT_CERT_PATH, ROOT_CERT_PWD);
    }

    /**
     * @return 根证书DN
     */
    private static X500Name dn() {
        return new X500NameBuilder()
                // 国家代码
                .addRDN(BCStyle.C, "CN")
                // 组织
                .addRDN(BCStyle.O, "HZNU")
                // 省份
                .addRDN(BCStyle.ST, "Zhejiang")
                // 地区
                .addRDN(BCStyle.L, "Hangzhou")
                // 通用名称
                .addRDN(BCStyle.CN, "Cluster CA ROOT Certificate")
                .build();
    }

    /**
     * 生成根证书
     *
     * @return 证书对象
     */
    public static X509Certificate genRootCert(KeyPair keyPair) throws Exception {
        // 证书签名实现类
        ContentSigner sigGen = new JcaContentSignerBuilder("SM3withSM2")
                .setProvider("BC")
                .build(keyPair.getPrivate());

        // 构造X.509 第3版的证书构建者
        X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                // 颁发者信息
                dn(),
                // 证书序列号
                BigInteger.valueOf(Instant.now().toEpochMilli()),
                // 证书生效日期
                Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                // 证书失效日期(3小时后)
                Date.from(LocalDateTime.now().plusMonths(3).atZone(ZoneId.systemDefault()).toInstant()),
                // 使用者信息（PS：由于是自签证书，所以颁发者和使用者DN都相同）
                dn(),
                // 证书公钥
                keyPair.getPublic())

                //TODO 设置证书扩展；证书扩展属性，请根据需求设定，参数请参考 《RFC 5280》
                // 设置密钥用法
                .addExtension(Extension.keyUsage, false, new X509KeyUsage(X509KeyUsage.digitalSignature | X509KeyUsage.nonRepudiation))
                // 设置扩展密钥用法：客户端身份认证
                .addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth))
                // 基础约束,标识是否是CA证书，这里true 表明是CA证书
                .addExtension(Extension.basicConstraints, false, new BasicConstraints(true))
                // Netscape Cert Type SSL客户端身份认证
                .addExtension(MiscObjectIdentifiers.netscapeCertType, false, new NetscapeCertType(NetscapeCertType.sslClient));

        // 将证书构造参数装换为X.509证书对象
        return new JcaX509CertificateConverter()
                .setProvider("BC")
                .getCertificate(certGen.build(sigGen));
    }

    /**
     * 使用KeyStore储存证书文件
     */
    public static void makeCertFileByKeyStore(X509Certificate rootCert, KeyPair keyPair, String p12SavePath, String cerSavePath, String password) throws Exception {
        File file = new File(p12SavePath);
        if (file.exists()) {
            file.delete();
        }
        // 以KeyStore保存
        KeyStore store = KeyStore.getInstance("PKCS12", "BC");
        // 初始化
        store.load(null, null);
        char[] pwd = password.toCharArray();
        // 写入证书以及公钥
        store.setKeyEntry("private", keyPair.getPrivate(), pwd, new Certificate[]{rootCert});

        // 带资源的try语句（try-with-resource）的最简形式
        // try块退出时，会自动调用res.close()方法，关闭资源。
        try (
                FileOutputStream out = new FileOutputStream(file);
                FileWriter fw = new FileWriter(cerSavePath)
        ) {
            // 加密写入文件
            store.store(out, pwd);
            fw.write(Base64.toBase64String(rootCert.getEncoded()));
        }
    }

}
