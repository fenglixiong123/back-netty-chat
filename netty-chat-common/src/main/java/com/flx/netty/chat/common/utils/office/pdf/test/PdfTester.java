package com.flx.netty.chat.common.utils.office.pdf.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import static com.flx.netty.chat.common.utils.office.pdf.base.PdfUnit.*;


/**
 * 测试Pdf功能
 */
public class PdfTester {

    public static void main(String[] args) throws Exception {

        Document document = new Document();
        testPDF(document);

    }

    // 生成PDF文件
    public static void testPDF(Document document) throws Exception {

        // 段落
        Paragraph paragraph = new Paragraph("美好的一天从早起开始！", titleFont);
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setIndentationLeft(12); //设置左缩进
        paragraph.setIndentationRight(12); //设置右缩进
        paragraph.setFirstLineIndent(24); //设置首行缩进
        paragraph.setLeading(20f); //行间距
        paragraph.setSpacingBefore(5f); //设置段落上空白
        paragraph.setSpacingAfter(10f); //设置段落下空白

        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));

        // 点线
        Paragraph p2 = new Paragraph();
        p2.add(new Chunk(new DottedLineSeparator()));

        // 超链接
        Anchor anchor = new Anchor("baidu");
        anchor.setReference("www.baidu.com");

        // 定位
        Anchor gotoP = new Anchor("goto");
        gotoP.setReference("#top");

        // 添加图片
        Image image = Image.getInstance("https://img-blog.csdn.net/20180801174617455?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzg0ODcxMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70");
        image.setAlignment(Image.ALIGN_CENTER);
        image.scalePercent(40); //依照比例缩放

        // 表格
        PdfPTable table = createTable(new float[] { 40, 120, 120, 120, 80, 80 });
        table.addCell(createCell("美好的一天", headFont));
        table.addCell(createCell("早上9:00", keyFont));
        table.addCell(createCell("中午11:00", keyFont));
        table.addCell(createCell("中午13:00", keyFont));
        table.addCell(createCell("下午15:00", keyFont));
        table.addCell(createCell("下午17:00", keyFont));
        table.addCell(createCell("晚上19:00", keyFont));
        Integer totalQuantity = 0;
        for (int i = 0; i < 5; i++) {
            table.addCell(createCell("起床", textFont));
            table.addCell(createCell("吃午饭", textFont));
            table.addCell(createCell("午休", textFont));
            table.addCell(createCell("下午茶", textFont));
            table.addCell(createCell("回家", textFont));
            table.addCell(createCell("吃晚饭", textFont));
            totalQuantity ++;
        }
        table.addCell(createCell("总计", keyFont));
        table.addCell(createCell("", textFont));
        table.addCell(createCell("", textFont));
        table.addCell(createCell("", textFont));
        table.addCell(createCell(totalQuantity + "件事", textFont));
        table.addCell(createCell("", textFont));

        document.add(paragraph);
        document.add(anchor);
        document.add(p2);
        document.add(gotoP);
        document.add(p1);
        document.add(table);
        document.add(image);
    }

}
