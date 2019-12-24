package com.demo.sm2;

import com.demo.GMBaseUtil;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

/**
 * 使用KeyStore签发证书
 */
public class DemoUseKeyStore extends GMBaseUtil {

    /**
     * 日期格式化
     */
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *
     */
    public static void main(String[] args) throws Exception {
        File file = new File(Sm2CaRootGenerator.ROOT_P12_CERT_PATH);
        if (!file.exists()) {
            Sm2CaRootGenerator.main(null);
        }

        //将要签发的文件储存路径
        File out = new File(Sm2SubCertGenerator.SIGN_CERT_PATH);

        /*
         * 注意此处可能会发生 JCE cannot authenticate the provider BC 的错误
         *
         * 为解决这个问题需要在 在jre的 /lib/security/java.security 文件中加入
         *
         * security.provider.11=org.bouncycastle.jce.provider.BouncyCastleProvider
         *
         * （例如： C:\Program Files\Java\jre1.8.0_231\lib\security）
         *
         * 然后将对应版本的BC jar包移动到jre的 /lib/ext 中 （bcprov-jdk15on-1.64.jar）
         *
         * （例如： C:\Program Files\Java\jre1.8.0_231\lib\ext）
         *
         * 再次运行就可以解决问题，如果有多个jre请确认当前使用的是哪一个。
         */
        // 1. 载入P12得到证书和私钥
        KeyStore store = KeyStore.getInstance("PKCS12", "BC");
        try (
                FileInputStream fIn = new FileInputStream(Sm2CaRootGenerator.ROOT_P12_CERT_PATH);
                FileWriter fw = new FileWriter(out)
        ) {
            char[] pwd = Sm2CaRootGenerator.ROOT_CERT_PWD.toCharArray();
            store.load(fIn, pwd);

            // 2. 取得CA根证书
            Certificate root = store.getCertificateChain("private")[0];
            // 3. 取得CA根证书的私钥
            PrivateKey privateKey = (PrivateKey) store.getKey("private", pwd);

            X509CertificateHolder holder = new X509CertificateHolder(root.getEncoded());
            System.out.println("CA证书有效期为 "
                    + SDF.format(holder.getNotBefore())
                    + " 至 "
                    + SDF.format(holder.getNotAfter()));
            System.out.println();
            System.out.println("注：签发证书默认有效时长为 5天");
            System.out.println();

            // 4. 签发证书
            X509Certificate userCert = Sm2SubCertGenerator.issue(Sm2Pkcs10Generator.P10_EXAMPLE, root, privateKey);
            // 5. 保存到本地
            fw.write(Base64.toBase64String(userCert.getEncoded()));
        }

        System.out.println(">>> 证书签发成功，证书存储路径: \n\n\t" + out.getAbsolutePath());
    }
}
