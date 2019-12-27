package com.tools.receipt;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;

import java.io.FileOutputStream;

public class CreateEmptyReceipt {

    private static final String PATH = System.getProperty("user.dir") + "/files/";
    private static final String DEST = PATH + "receipt/empty-receipt.pdf";

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        // Create pdf
        Document document = new Document();
        Rectangle pageSize = new Rectangle(0, 0, 2019.375f, 1350.0f);
        document.setPageSize(pageSize);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        //使用中文字体
        BaseFont bf = BaseFont.createFont("src/main/resources/huawenkaiti.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        drawTitle(cb, bf);
        drawHeadInfo(cb, bf);
        drawTableTitle(cb, bf);
        drawFoot(cb, bf);
        drawQrCodeFrame(cb);
        drawTable(cb);
        addTextField(writer);

        document.close();
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 绘制标题
     */
    private static void drawTitle(PdfContentByte cb, BaseFont bf) {
        // /C2_0 56.25 Tf
        // 700.312 1203.75 Td
        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(bf, 56.25f);
        cb.setRGBColorFill(153, 101, 0);
        cb.moveText(700.312f, 1203.75f);
        cb.showText("云南省罚没收入专用收据");
        cb.endText();
        cb.restoreState();
    }

    /**
     * 绘制头部信息标题
     */
    private static void drawHeadInfo(PdfContentByte cb, BaseFont bf) {
        //左边数据标题
        //135 1067.907 Td
        //0 -27 TD
        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(bf, 22.5f);
        cb.setRGBColorFill(153, 101, 0);
        cb.moveText(135, 1067.907f);
        cb.showText("票据代码：");
        cb.moveText(0, -27);
        cb.showText("交款人统一社会信用代码：");
        cb.moveText(0, -27);
        cb.showText("交款人：");

        //右边数据标题
        //1293.75 1067.907 Td
        cb.moveText(1293.75f - 135, 27 * 2);
        cb.showText("票据号码：");
        cb.moveText(0, -27);
        cb.showText("校验码：");
        cb.moveText(0, -27);
        cb.showText("开票日期：");
        cb.moveText(22.5f * 7, 0);
        cb.showText("年");
        cb.moveText(22.5f * 5 / 2, 0);
        cb.showText("月");
        cb.moveText(22.5f * 5 / 2, 0);
        cb.showText("日");
        cb.endText();
        cb.restoreState();
    }

    /**
     * 绘制表格标题
     */
    private static void drawTableTitle(PdfContentByte cb, BaseFont bf) {
        ///CS0 cs 0.6 0.4 0  scn
        ///C2_0 22.5 Tf
        //168.75 946.688 Td <005300540055000F>Tj项目编码
        //337.5 0 Td <0053005400560057>Tj项目名称
        //360 0 Td <00430044>Tj单位
        //160.312 0 Td <00580059>Tj数量
        //160.313 0 Td <005A005B>Tj标准
        //195.468 0 Td <003100320035003B0038>Tj金额（元）
        //331.875 0 Td <005C005D>Tj备注
        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(bf, 22.5f);
        cb.setRGBColorFill(153, 101, 0);
        cb.moveText(168.75f, 946.688f);
        cb.showText("项目编码");
        cb.moveText(337.5f, 0);
        cb.showText("项目名称");
        cb.moveText(360f, 0);
        cb.showText("单位");
        cb.moveText(160.312f, 0);
        cb.showText("数量");
        cb.moveText(160.313f, 0);
        cb.showText("标准");
        cb.moveText(195.468f, 0);
        cb.showText("金额（元）");
        cb.moveText(331.875f, 0);
        cb.showText("备注");
        cb.endText();

        //137.812 461.532 Td <003100320033003400350036003700380001>Tj
        //1206.562 461.532 Td <0035003D003700380001>Tj
        //137.812 304.452 Td <0040>Tj
        //0 -25.311 TD <0041>Tj
        //T* <0020>Tj
        //T* <0042>Tj
        cb.beginText();
        cb.setFontAndSize(bf, 22.5f);
        cb.setRGBColorFill(153, 101, 0);
        cb.moveText(137.812f, 461.532f);
        cb.showText("金额合计（大写）");
        cb.moveText(1206.562f - 137.812f, 0);
        cb.showText("（小写）");
        cb.moveText(137.812f - 1206.562f, 304.452f - 461.532f);
        cb.showText("其");
        cb.moveText(0, -25.311f);
        cb.showText("他");
        cb.moveText(0, -25.311f);
        cb.showText("信");
        cb.moveText(0, -25.311f);
        cb.showText("息");
        cb.endText();
        cb.restoreState();
    }

    /**
     * 绘制底部信息标题
     */
    private static void drawFoot(PdfContentByte cb, BaseFont bf) {
        //137.812 65.392 Td <0008001A004300440035002A00380010>Tj
        //1133.437 65.392 Td <00510052001B0010>Tj
        //368.437 0 Td <0008001A001B0010>Tj
        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(bf, 22.5f);
        cb.setRGBColorFill(153, 101, 0);
        cb.moveText(137.812f, 65.392f);
        cb.showText("收款单位（章）：");
        cb.moveText(1133.437f - 137.812f, 0);
        cb.showText("复核人：");
        cb.moveText(368.437f, 0);
        cb.showText("收款人：");
        cb.endText();
        cb.restoreState();
    }

    /**
     * 绘制二维码框
     */
    private static void drawQrCodeFrame(PdfContentByte cb) {
        cb.saveState();
        cb.setLineWidth(2.8f);    // Make a bit thicker than 1.0 default
        cb.setRGBColorStroke(153, 101, 0);
        cb.rectangle(1681.876f, 1203.75f - 208.124f, 208.124f, 208.124f);
        cb.stroke();
        cb.restoreState();
    }

    /**
     * 绘制表格线框
     */
    private static void drawTable(PdfContentByte cb) {
        cb.saveState();
        cb.setLineWidth(5f);
        cb.setRGBColorStroke(153, 101, 0);
        cb.rectangle(129.375f, 104.063f, 1755.625f, 877.5f);
        cb.stroke();

        cb.setLineWidth(2.8f);
        cb.moveTo(129.375f, 928.125f);
        cb.lineTo(129.375f + 1755, 928.125f);
        cb.stroke();
        cb.moveTo(129.375f, 503.437f);
        cb.lineTo(129.375f + 1755, 503.437f);
        cb.stroke();
        cb.moveTo(129.375f, 441.562f);
        cb.lineTo(129.375f + 1755, 441.562f);
        cb.stroke();

        cb.moveTo(298.124f, 981.563f);
        cb.lineTo(298.124f, 981.563f - 480.937);
        cb.stroke();
        cb.moveTo(812.811f, 981.563f);
        cb.lineTo(812.811f, 981.563f - 480.937);
        cb.stroke();
        cb.moveTo(973.124f, 981.563f);
        cb.lineTo(973.124f, 981.563f - 480.937);
        cb.stroke();
        cb.moveTo(1133.436f, 981.563f);
        cb.lineTo(1133.436f, 981.563f - 480.937);
        cb.stroke();
        cb.moveTo(1293.749f, 981.563f);
        cb.lineTo(1293.749f, 981.563f - 480.937);
        cb.stroke();
        cb.moveTo(1591.874f, 981.563f);
        cb.lineTo(1591.874f, 981.563f - 480.937);
        cb.stroke();

        cb.restoreState();
    }

    /**
     * 添加文字域
     */
    private static void addTextField(PdfWriter writer) throws Exception {
        addTextFiled(writer, new Rectangle(129.375f, 503.437f, 298.124f, 928.125f), "fill_1");
        addTextFiled(writer, new Rectangle(298.124f, 503.437f, 812.811f, 928.125f), "fill_2");
        addTextFiled(writer, new Rectangle(812.811f, 503.437f, 973.124f, 928.125f), "fill_3");
        addTextFiled(writer, new Rectangle(973.124f, 503.437f, 1133.436f, 928.125f), "fill_4");
        addTextFiled(writer, new Rectangle(1133.436f, 503.437f, 1293.749f, 928.125f), "fill_5");
        addTextFiled(writer, new Rectangle(1293.749f, 503.437f, 1591.874f, 928.125f), "fill_6");
        addTextFiled(writer, new Rectangle(1591.874f, 503.437f, 129.375f + 1755, 928.125f), "fill_7");
    }

    private static void addTextFiled(PdfWriter writer, Rectangle rectangle, String fileName) throws Exception {
        TextField field = new TextField(writer, rectangle, fileName);
        field.setAlignment(Element.ALIGN_TOP);
        field.setOptions(TextField.MULTILINE);
        writer.getAcroForm().addFormField(field.getTextField());
    }

}
