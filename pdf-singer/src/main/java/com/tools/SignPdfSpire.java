package com.tools;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.security.GraphicMode;
import com.spire.pdf.security.PdfCertificate;
import com.spire.pdf.security.PdfCertificationFlags;
import com.spire.pdf.security.PdfSignature;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SignPdfSpire {

    //private static final String FILE_PATH = "C:/Users/YuChiTao/Desktop/signPdf/";
    private static final String PATH = "files/";
    private static final String KEYSTORE = PATH + "key.p12";
    private static final String PASSWORD = "123456";
    private static final String SRC_PDF = PATH + "source.pdf";
    private static final String DEST_PDF = PATH + "result/result-spire.pdf";
    private static final String IMAGE_PATH = PATH + "sign.png";

    public static void main(String[] args) {
        //加载PDF文档
        PdfDocument doc = new PdfDocument();
        doc.loadFromFile(SRC_PDF);

        //加载pfx证书，及证书秘钥
        PdfCertificate cert = new PdfCertificate(KEYSTORE, PASSWORD);

        //添加数字签名到指定页面，并设置其位置和大小
        PdfSignature signature = new PdfSignature(doc, doc.getPages().get(0), cert, "MySignature");
        Rectangle2D rect = new Rectangle2D.Float();
        rect.setFrame(new Point2D.Float(0, 0), new Dimension(350, 350));
        signature.setBounds(rect);

        //设置签名为图片加文本模式
        signature.setGraphicMode(GraphicMode.Sign_Image_Only);

        //设置签名的内容
        signature.setNameLabel("Singer: ");
        signature.setName(cert.getIssuer());
        signature.setDistinguishedNameLabel("DN: ");
        signature.setDistinguishedName(signature.getCertificate().get_IssuerName().getName());

        // 设置签名图片
        PdfImage image = PdfImage.fromFile(IMAGE_PATH);
        signature.setSignImageSource(image);

//        signature.setContactInfoLabel("Phone: ");
//        signature.setContactInfo("123456789");
//        signature.setDateLabel("Date: ");
//        signature.setDate(new java.util.Date());
//        signature.setLocationInfoLabel("Location: ");
//        signature.setLocationInfo("Guangzhou");
//        signature.setReasonLabel("Reason: ");
//        signature.setReason("owner");
        //设置签名的字体
//        signature.setSignDetailsFont(new PdfTrueTypeFont(new Font("Arial Unicode MS", Font.PLAIN, 9)));

        //设置文档权限为禁止更改
        signature.setDocumentPermissions(PdfCertificationFlags.Forbid_Changes);
        signature.setCertificated(true);

        //保存文档
        doc.saveToFile(DEST_PDF);
        doc.close();
    }

}
