package com.flx.netty.chat.common.utils.office.pdf.base;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;

/**
 * PDF基础操作
 */
@Slf4j
public class PdfUnit {

    /**
     * 获取基础字体
     */
    public static BaseFont baseFont;
    public static Font titleFont;
    public static Font headFont;
    public static Font keyFont;
    public static Font textFont;

    static {
        freshFont();
    }

    /**
     * 刷新全局字体,根据操作系统选择使用的字体样式
     */
    public static void freshFont(){
        String osName = System.getProperties().getProperty("os.name");
        log.info("os.name = {}",osName);
        if(osName.toLowerCase().contains("window")) {
            useWindowsFont();
        }else {
            useLinuxFont();
        }
    }

    /**
     * windows下面使用的字体
     * 首先simsun.ttc字体需要自己添加到项目中
     */
    public static void useWindowsFont(){
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource("simsun.ttc");
            String fontPath = resource==null?null:resource.getPath();
            System.out.println("fontPath = "+fontPath);
            baseFont = BaseFont.createFont(fontPath+",1",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            titleFont = new Font(baseFont, 16, Font.BOLD);
            headFont = new Font(baseFont, 14, Font.BOLD);
            keyFont = new Font(baseFont, 10, Font.BOLD);
            textFont = new Font(baseFont, 10, Font.NORMAL);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * linux上面文字
     */
    public static void useLinuxFont(){
        try {
            baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            titleFont = new Font(baseFont, 16, Font.BOLD);
            headFont = new Font(baseFont, 14, Font.BOLD);
            keyFont = new Font(baseFont, 10, Font.BOLD);
            textFont = new Font(baseFont, 10, Font.NORMAL);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建单元格
     */
    public static PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidth(1);
        cell.setPaddingBottom(8f);
        return cell;
    }

    /**
     * 创建单元格
     */
    public static PdfPCell createPerfectCell(String value, float fixedHeight,
                                             Font font, float[] borderWidth, float[] paddingSize,
                                             int vAlign, int hAlign, int rowspan, int colspan,
                                             BaseColor borderColor, BaseColor backColor) {
        PdfPCell cell = new PdfPCell();
        cell.setFixedHeight(fixedHeight);
        cell.setVerticalAlignment(vAlign);//垂直对齐 Element.ALIGN_MIDDLE
        cell.setHorizontalAlignment(hAlign);//水平对齐 Element.ALIGN_CENTER
        cell.setPhrase(new Phrase(value, font));//文字
        cell.setBorderWidthLeft(borderWidth[0]);//左边框宽度
        cell.setBorderWidthRight(borderWidth[1]);//右边框宽度
        cell.setBorderWidthTop(borderWidth[2]);//上边框宽度
        cell.setBorderWidthBottom(borderWidth[3]);//下边框宽度
        cell.setPaddingTop(paddingSize[0]);//上边间距
        cell.setPaddingBottom(paddingSize[1]);//下边间距 8实现垂直居中
        cell.setRowspan(rowspan);//行合并
        cell.setColspan(colspan);//合并个数
        cell.setBorderColor(borderColor);//边框颜色
        cell.setBackgroundColor(backColor);//背景颜色
        cell.setLeading(0, (float) 1.4);//设置行间距
        return cell;
    }

    /**
     * 创建表格
     */
    public static PdfPTable createTable(float[] columnWith) throws DocumentException {
        PdfPTable table = new PdfPTable(columnWith.length);
        float totalWidth = 0.0f;
        for (int i = 0; i < columnWith.length; i++) {
            totalWidth += columnWith[i];
        }
        table.setTotalWidth(totalWidth);
        table.setTotalWidth(columnWith);
        table.setLockedWidth(true);
        table.setKeepTogether(true);
        table.setSplitLate(false);
        table.setSplitRows(true);
        return table;
    }

    /**
     * 创建表格，按照比例设置宽度
     */
    public static PdfPTable createTable(int[] widths,float withPercent) throws DocumentException {
        PdfPTable table = new PdfPTable(widths.length);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(withPercent);//表格宽度比例
        table.setWidths(widths);//设置宽度比例
        table.setLockedWidth(false);//使用百分比不能锁行
        table.setKeepTogether(false);//解决表格数据过多空白行问题
        table.setSplitLate(false);
        table.setSplitRows(true);
        return table;
    }

    /**
     * 创建表格，按照实际长度
     */
    public static PdfPTable createTable(int column,float totalWidth) throws DocumentException {
        PdfPTable table = new PdfPTable(column);
        table.setTotalWidth(totalWidth);
        float[] columnWith = new float[column];
        for (int i=0;i<column;i++){
            columnWith[i] = totalWidth/column;
        }
        table.setTotalWidth(columnWith);
        table.setLockedWidth(true);
        table.setKeepTogether(true);
        table.setSplitLate(false);
        table.setSplitRows(true);
        return table;
    }

    /**
     * 创建表格
     */
    public static PdfPTable createPerfectTable(int column,
                                               float withPercent,int[] widths,
                                               float totalWidth,float[] columnWith,
                                               float spaceBefore,float spaceAfter,
                                               boolean lockWidth,boolean keepTogether,
                                               int hAlign,int border) throws DocumentException {
        PdfPTable table = new PdfPTable(column);
        table.setWidthPercentage(withPercent);//表格宽度比例
        table.setWidths(widths);//设置宽度比例
        table.setTotalWidth(totalWidth);//设置表格宽度
        table.setTotalWidth(columnWith);//设置表格各列宽度
        table.setLockedWidth(lockWidth);//锁定行
        table.setKeepTogether(keepTogether);//标题跟随表格
        table.setHorizontalAlignment(hAlign);//设置水平对齐 Element.ALIGN_CENTER
        table.getDefaultCell().setBorder(border);//边框
        table.setSpacingBefore(spaceBefore);//表格前面距离
        table.setSpacingAfter(spaceAfter);//表格后面距离
        table.setSplitLate(false);//当前页能放多少放多少,解决行优先问题
        table.setSplitRows(true);//和上面一起解决cell跨页问题
        return table;
    }

}
