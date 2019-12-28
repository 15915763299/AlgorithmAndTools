package com.tools.itext;

import com.itextpdf.text.exceptions.UnsupportedPdfException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 提取PDF内容
 */
public class ExtractPdfStream {
    private static final String PATH = System.getProperty("user.dir") + "/files/";
    private static final String SRC_PDF = PATH + "receipt/template.pdf";
    private static final String DEST = PATH + "stream/stream%s";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ExtractPdfStream().parse(SRC_PDF, DEST);
    }

    private void parse(String src, String dest) throws IOException {
        PdfReader reader = new PdfReader(src);
        PdfObject obj;
        for (int i = 1; i <= reader.getXrefSize(); i++) {
            obj = reader.getPdfObject(i);
            if (obj != null && obj.isStream()) {
                PRStream stream = (PRStream) obj;
                byte[] b;
                try {
                    b = PdfReader.getStreamBytes(stream);
                } catch (UnsupportedPdfException e) {
                    b = PdfReader.getStreamBytesRaw(stream);
                }
                FileOutputStream fos = new FileOutputStream(String.format(dest, i));
                fos.write(b);
                fos.flush();
                fos.close();
            }
        }
    }
}
