package com.tools.itext;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;

public class ReadPdf {

    private static final String PATH = System.getProperty("user.dir") + "/files/";
    private static final String SRC_PDF = PATH + "receipt/template.pdf";

    public static void main(String[] args) throws Exception {
        PdfReader reader = new PdfReader(SRC_PDF);

        // 获取页面宽高，pageIndex从1开始
        Rectangle pageSize = reader.getPageSize(1);
        System.out.println("height: " + pageSize.getHeight() + ", width: " + pageSize.getWidth());

        System.out.println();
        System.out.println("Content: ");

        //读取页面内容
        byte[] streamBytes = reader.getPageContent(1);
        String contentStream = new String(streamBytes);
        System.out.println(contentStream);
    }

}
