package com.tools.itext;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReplacePdfStream {
    private static final String PATH = System.getProperty("user.dir") + "/files/";
    private static final String SRC_PDF = PATH + "template/text.pdf";
    public static final String DEST = PATH + "result/result-replace.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReplacePdfStream().manipulatePdf(SRC_PDF, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfDictionary dict = reader.getPageN(1);
        PdfObject object = dict.getDirectObject(PdfName.CONTENTS);
        if (object instanceof PRStream) {
            PRStream stream = (PRStream) object;
            byte[] data = PdfReader.getStreamBytes(stream);
            String streamStr = new String(data);
            System.out.println(streamStr);
            //“项”字改成了“云”
            stream.setData(streamStr.replace("0053", "0003").getBytes());
            //位置移动到了最左端
            stream.setData(streamStr.replace("168.75", "0").getBytes());
        }
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }
}
