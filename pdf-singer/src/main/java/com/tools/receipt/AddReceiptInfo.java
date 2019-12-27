package com.tools.receipt;

import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class AddReceiptInfo {

    private static final String PATH = System.getProperty("user.dir") + "/files/";
    private static final String SRC_PDF = PATH + "receipt/empty-receipt.pdf";
    private static final String DEST = PATH + "receipt/receipt.pdf";

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        // Load pdf
        PdfReader reader = new PdfReader(SRC_PDF);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(DEST));
        PdfContentByte cb = stamper.getOverContent(1);

        //查看pdf宽高
        //Rectangle pageSize = reader.getPageSize(1);
        //System.out.println(pageSize.getHeight() + ":" + pageSize.getWidth());

        //使用中文字体
        BaseFont bf = BaseFont.createFont("src/main/resources/huawenkaiti.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        drawHeadInfo(cb, bf);
        fillFrom(stamper, bf);

        stamper.close();
        reader.close();
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 绘制头部信息
     */
    private static void drawHeadInfo(PdfContentByte cb, BaseFont bf) {
        //135 1067.907 Td
        //0 -27 TD
        //左边数据内容
        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(bf, 22.5f);
        cb.setRGBColorFill(0, 0, 0);
        cb.moveText(135 + 22.5f * 5, 1067.907f);
        cb.showText("53020919");
        cb.moveText(22.5f * 7, -27);
        cb.showText("123456789012345678");
        cb.moveText(-22.5f * 8, -27);
        cb.showText("交通违章人某某某");

        //右边数据内容
        cb.moveText(1293.75f - 135 + 22.5f * (5 - 4), 27 * 2);
        cb.showText("0000030004");
        cb.moveText(-22.5f, -27);
        cb.showText("2da078");
        cb.moveText(22.5f, -27);
        cb.showText("2019");
        cb.moveText(22.5f * 7 / 2, 0);
        cb.showText("12");
        cb.moveText(22.5f * 5 / 2, 0);
        cb.showText("05");
        cb.endText();
        cb.restoreState();
    }

    /**
     * 填写表单
     */
    private static void fillFrom(PdfStamper stamper, BaseFont bf) throws Exception {
        //填入表单信息
        AcroFields form = stamper.getAcroFields();
        //form.addSubstitutionFont(bf);
        form.setExtraMargin(0, 5);
        setText(form, bf, "fill_1", "103050101001");
        setText(form, bf, "fill_2", "交通违法罚没收入");
        setText(form, bf, "fill_3", "元");
        setText(form, bf, "fill_4", "1");
        setText(form, bf, "fill_5", "");
        setText(form, bf, "fill_6", "10.00");
        setText(form, bf, "fill_7", "备注备注备注备注备注备注备注，备注备注备注备注备注备注，备注备注备注备注备注备注，备注备注备注备注");
    }

    /**
     * 给表单设置数据
     */
    private static void setText(AcroFields form, BaseFont bf, String fieldName, String text) throws Exception {
        AcroFields.Item item = form.getFieldItem(fieldName);
        if (item != null) {
            item.getMerged(0).put(PdfName.Q, new PdfNumber(PdfFormField.Q_CENTER));
        }
        form.setFieldProperty(fieldName, "textsize", 22.5f, null);
        form.setFieldProperty(fieldName, "textfont", bf, null);
        form.setField(fieldName, text);
    }
}
