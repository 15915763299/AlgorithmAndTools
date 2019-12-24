package com.demo.sm2;


import com.demo.GMBaseUtil;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.misc.NetscapeCertType;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.X509KeyUsage;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * SM2 X.509签名证书生成器
 * <p>
 * X.509证书里含有公钥、身份信息(比如网络主机名，组织的名称或个体名称等)和签名信息（可以是证书签发机构CA的签名，也可以是自签名）。
 * 证书颁发机构（CA, Certificate Authority）即颁发数字证书的机构。
 */
public class Sm2CertGenerator extends GMBaseUtil {


    public static void main(String[] args) throws Exception {
        // 生成密钥生成器
        KeyPairGenerator keyPairGenerator = Sm2KeyGenerator.generate();
        // 产生密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成证书文件
        X509Certificate certificate = generateCert(keyPair);
        // 保存为证书文件
        makeCertFile(certificate, Paths.get("F://test-cert.cer"));
    }

    /**
     * 获取DN(Distinct Name)构造者，来唯一标识一个实体，其功能类似我们平常使用的ID
     * {@see <a href="https://www.ibm.com/support/knowledgecenter/en/SSFKSJ_7.5.0/com.ibm.mq.sec.doc/q009860_.htm"></a>}
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
                .build();
    }

    /**
     * 生成证书
     *
     * @return 证书对象
     */
    public static X509Certificate generateCert(KeyPair keyPair) throws OperatorCreationException, IOException, CertificateException {
        // BouncyCastle算法提供者
        //Provider provider = new BouncyCastleProvider();

        // 证书签名实现类
        ContentSigner sigGen = new JcaContentSignerBuilder("SM3withSM2")
                //.setProvider(provider)
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
                // 证书失效日期(5分钟)
                Date.from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant()),
                // 使用者信息（PS：由于是自签证书，所以颁发者和使用者DN都相同）
                dn(),
                // 证书公钥
                keyPair.getPublic())

                //TODO 设置证书扩展；证书扩展属性，请根据需求设定，参数请参考 《RFC 5280》
                // 设置密钥用法
                .addExtension(Extension.keyUsage, false, new X509KeyUsage(X509KeyUsage.digitalSignature | X509KeyUsage.nonRepudiation))
                // 设置扩展密钥用法：客户端身份认证、安全电子邮件
                .addExtension(Extension.extendedKeyUsage, false, extendedKeyUsage())
                // 基础约束,标识是否是CA证书，这里false标识为实体证书
                .addExtension(Extension.basicConstraints, false, new BasicConstraints(false))
                // Netscape Cert Type SSL客户端身份认证
                .addExtension(MiscObjectIdentifiers.netscapeCertType, false, new NetscapeCertType(NetscapeCertType.sslClient));

        // 将证书构造参数装换为X.509证书对象
        return new JcaX509CertificateConverter()
                .setProvider("BC")
                .getCertificate(certGen.build(sigGen));
    }

    /**
     * 获取扩展密钥用途
     *
     * @return 增强密钥用法ASN.1对象
     */
    public static DERSequence extendedKeyUsage() {
//        // 客户端身份认证
//        ASN1ObjectIdentifier clientAuth = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.3.2");
//        // 安全电子邮件
//        ASN1ObjectIdentifier emailProtection = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.3.4");
        // 构造容器对象
        ASN1EncodableVector vector = new ASN1EncodableVector();
        // 客户端身份认证
        vector.add(KeyPurposeId.id_kp_clientAuth);
        // 安全电子邮件
        vector.add(KeyPurposeId.id_kp_emailProtection);
        return new DERSequence(vector);
    }

    /**
     * 生成证书文件
     *
     * @param x509Certificate X.509格式证书
     * @param savePath        证书保存路径
     */
    public static void makeCertFile(X509Certificate x509Certificate, Path savePath) throws CertificateEncodingException, IOException {
        if (Files.exists(savePath)) {
            Files.deleteIfExists(savePath);
        }
        Files.createFile(savePath);

        // 获取ASN.1编码的证书字节码
        byte[] asn1BinCert = x509Certificate.getEncoded();
        // 编码为BASE64 便于传输
        byte[] base64EncodedCert = Base64.encode(asn1BinCert);
        // 写入文件
        Files.write(savePath, base64EncodedCert);
    }
}
