package com.tools;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

/**
 * https://itextpdf.com/en/resources/examples/itext-5-legacy/digital-signatures-chapter-2
 */
public class SignPdfItext {

    private static final String PATH = System.getProperty("user.dir") + "/files/";

    private static final String KEYSTORE = PATH + "key.p12";
    private static final char[] PASSWORD = "123456".toCharArray();
    private static final String SRC_PDF = PATH + "source.pdf";
    private static final String DEST_PDF = PATH + "result/result-itext.pdf";
    private static final String IMAGE_PATH = PATH + "sign.png";

    public static void main(String[] args) throws Exception {
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(new FileInputStream(KEYSTORE), PASSWORD);

        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
        Certificate[] chain = ks.getCertificateChain(alias);

        // Creating the reader and the stamper
        PdfReader reader = new PdfReader(SRC_PDF);
        FileOutputStream os = new FileOutputStream(DEST_PDF);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', new File(PATH));

        // Creating the appearance
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        // 获取页面宽高，pageIndex从1开始
        Rectangle pageSize = reader.getPageSize(1);
        Rectangle rec = getRec(pageSize.getWidth() / 2, pageSize.getHeight() * 9 / 10, 120, 120);
        appearance.setVisibleSignature(rec, 1, "sig");
        appearance.setSignatureGraphic(Image.getInstance(IMAGE_PATH));
        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);

        // Creating the signature
        ExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, provider.getName());
        ExternalDigest digest = new BouncyCastleDigest();
        MakeSignature.signDetached(appearance, digest, pks, chain,
                null, null, null, 0, MakeSignature.CryptoStandard.CMS);
    }

    private static Rectangle getRec(float centerX, float centerY, float height, float width) {
        float halfH = height / 2;
        float halfW = width / 2;
        // ll: lower left; ur: upper right
        return new Rectangle(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH);
    }

}
