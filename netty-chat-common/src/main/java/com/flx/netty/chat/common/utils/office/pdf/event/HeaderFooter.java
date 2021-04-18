package com.flx.netty.chat.common.utils.office.pdf.event;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * 自定义pdf页脚页眉实现
 */
public class HeaderFooter extends PdfPageEventHelper {

    private PdfTemplate pdfTemplate;

    /**
     * 打开文档时，创建一个总页数的模版
     * @param writer
     * @param document
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        PdfContentByte pcb = writer.getDirectContent();
        pdfTemplate = pcb.createTemplate(30,16);
    }

    /**
     * 一页加载完成触发，写入页眉和页脚
     * @param writer
     * @param document
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        try{
            table.setTotalWidth(PageSize.A4.getWidth() - 100);
            table.setWidths(new int[] { 24, 24, 3});
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(-10);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            table.addCell(new Paragraph("我是页眉/页脚"));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Paragraph("第" + writer.getPageNumber() + "页/"));
            PdfPCell cell = new PdfPCell(Image.getInstance(pdfTemplate));
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            // 将页眉写到document中，位置可以指定，指定到下面就是页脚
            table.writeSelectedRows(0, -1, 50,PageSize.A4.getHeight() - 20, writer.getDirectContent());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 全部完成后，将总页数的pdf模版写到指定位置
     * @param writer
     * @param document
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        String text = "总" + (writer.getPageNumber()) + "页";
        ColumnText.showTextAligned(pdfTemplate, Element.ALIGN_LEFT, new Paragraph(text), 2, 2, 0);
    }
}
