package com.flx.netty.chat.common.utils.office.pdf.event;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;

/**
 * pdf加水印的实现方式
 */
public class WaterMark extends PdfPageEventHelper {

    Font FONT = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, new GrayColor(0.95f));

    /**
     * 水印内容
     */
    private String waterContent;

    public WaterMark(){

    }

    public WaterMark(String waterContent){
        this.waterContent = waterContent;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        if(StringUtils.isNoneBlank(this.waterContent)) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    ColumnText.showTextAligned(writer.getDirectContent(),
                            Element.ALIGN_CENTER,
                            new Paragraph(this.waterContent == null ? "SMILE" : this.waterContent, FONT),
                            (50.5f + i * 350),
                            (40.0f + j * 150),
                            writer.getPageNumber() % 2 == 1 ? 45 : -45);
                }
            }
        }
    }
}
