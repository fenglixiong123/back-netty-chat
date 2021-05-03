package com.flx.netty.chat.common.utils.office.pdf;

import com.flx.netty.chat.common.utils.file.FileUtils;
import com.flx.netty.chat.common.utils.office.pdf.base.PdfUnit;
import com.flx.netty.chat.common.utils.office.pdf.event.HeaderFooter;
import com.flx.netty.chat.common.utils.office.pdf.event.WaterMark;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import static com.flx.netty.chat.common.utils.office.pdf.base.PdfUnit.*;

/**
 * PDF代码生成
 * 问题：linux上面字体会找不到
 * 解决办法：
 * 下载simsun.ttc字体文件，把这文件拷贝到Linux系统的 /usr/share/fonts/ 下就可以了。
 */
@Slf4j
public class PdfUtils {

    static {
        if(baseFont==null){
            PdfUnit.freshFont();
        }
    }

    /**
     * 浏览器下载pdf文档
     * @param response
     * @param documentInfo
     */
    public static void downloadPdf(HttpServletResponse response,String fileName,DocumentInfo documentInfo){
        fileName = FileUtils.getRandomName(fileName,true);
        try {
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/PDF;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8")+".pdf");

            //1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象

            //2.找到输出源，和document建立联系
            ServletOutputStream out = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document,out);

            //添加事件
            if(documentInfo.enableMark) {
                writer.setPageEvent(new WaterMark(documentInfo.getMarkText()));//添加水印
            }
            if(documentInfo.isEnableHf()){
                writer.setPageEvent(new HeaderFooter());//页眉页脚
            }

            //3.打开文档
            document.open();

            // 4.向文档中添加内容
            generatePDF(document,documentInfo);

            // 5.关闭文档
            document.close();
            out.flush();
            out.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存pdf到磁盘上面
     */
    public static void savePdfToDesk(String filePath,String fileName,DocumentInfo documentInfo)throws Exception{

        // 1.新建document对象
        Document document = new Document(PageSize.A4);// 建立一个Document对象

        // 2.建立一个书写器(Writer)与document对象关联
        fileName = FileUtils.getRandomName(fileName,true);
        log.info("pathName = "+(filePath+fileName));
        File file = new File(filePath+fileName);
        boolean success = file.createNewFile();
        if(!success){ return;}
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        if(documentInfo.enableMark) {
            writer.setPageEvent(new WaterMark(documentInfo.getMarkText()));//添加水印
        }
        if(documentInfo.isEnableHf()){
            writer.setPageEvent(new HeaderFooter());//页眉页脚
        }

        //3.打开文档
        document.open();

        // 4.向文档中添加内容
        generatePDF(document,documentInfo);

        // 5.关闭文档
        document.close();

    }

    /**
     * 填充Pdf文件
     * @param document
     * @param documentInfo
     * @throws DocumentException
     */
    private static void generatePDF(Document document,DocumentInfo documentInfo) throws DocumentException {
        if(StringUtils.isNoneBlank(documentInfo.getTitleInfo())){
            Paragraph titleParagraph = new Paragraph(documentInfo.getTitleInfo(), new Font(baseFont, 24, Font.BOLD));
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            titleParagraph.setSpacingAfter(50f);
            document.add(titleParagraph);
        }
        if(documentInfo.getBaseInfo()!=null){
            addDocumentBase(document,documentInfo.getBaseInfo());
        }
        if(documentInfo.getTableInfo()!=null) {
            for (TableInfo tableInfo : documentInfo.getTableInfo()) {
                addTable(document, tableInfo);
            }
        }
        if(documentInfo.getSignInfo()!=null){
            addDocumentSign(document,documentInfo.getSignInfo());
        }
    }



    /**
     * 创建基本信息
     * @param document
     * @param baseInfo
     */
    public static void addDocumentBase(Document document,BaseInfo baseInfo){
        document.addTitle(baseInfo.getTitle());// 标题
        document.addAuthor(baseInfo.getAuthor());// 作者
        document.addSubject(baseInfo.getSubject());// 主题
        document.addKeywords(baseInfo.getKeywords());// 关键字
        document.addCreator(baseInfo.getCreator());// 创建者
    }

    /**
     * 添加落款信息
     * @param document
     * @param signInfo
     */
    private static void addDocumentSign(Document document, SignInfo signInfo) throws DocumentException {
        Paragraph userParagraph = new Paragraph(signInfo.getUserLabel()+": "+signInfo.getCreateUser(), textFont);
        userParagraph.setSpacingBefore(50f);
        userParagraph.setIndentationLeft(300f);
        document.add(userParagraph);

        Paragraph timeParagraph = new Paragraph(signInfo.getTimeLabel()+": "+signInfo.getCreateTime(), textFont);
        timeParagraph.setIndentationLeft(300f);

        document.add(timeParagraph);
    }

    /**
     * 向pdf中添加表格
     * @param document
     * @param tableInfo
     * @throws DocumentException
     */
    public static void addTable(Document document,TableInfo tableInfo) throws DocumentException {
        List<List<String>> tableValue = tableInfo.getTableValue();
        int column = tableValue.get(0).size();
        int row = tableValue.size();
        if(column!=tableInfo.getWidths().length){
            throw new RuntimeException("please check column size!");
        }
        PdfPTable table = createTable(tableInfo.getWidths(),tableInfo.getWidthPercent());
        //添加表头
        if(StringUtils.isNoneBlank(tableInfo.getTitle())){
            table.setSpacingBefore(20f);
            table.setSpacingAfter(30f);
            Paragraph titleParagraph = new Paragraph(tableInfo.getTitle(), titleFont);
            document.add(titleParagraph);
        }
        //添加表内容
        for (int i = 0; i < row; i++) {
            List<String> rowValue = tableValue.get(i);
            for (int j = 0; j < column; j++) {
                if(i==0) {
                    table.addCell(createCell(rowValue.get(j), keyFont));
                }else {
                    table.addCell(createCell(rowValue.get(j), textFont));
                }
            }
        }
        document.add(table);
    }





    /**
     * 生成文档所需数据
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DocumentInfo{
        private boolean enableMark;//是否允许水印
        private String markText;//水印内容
        private boolean enableHf;//是否允许页眉页脚
        private String titleInfo;//文章头信息
        private BaseInfo baseInfo;//基础信息
        private SignInfo signInfo;//落款信息
        private List<TableInfo> tableInfo;//表格信息

        public static DocumentInfo builder(){
            return new DocumentInfo();
        }

        public DocumentInfo setEnableMark(boolean enableMark) {
            this.enableMark = enableMark;
            return this;
        }

        public DocumentInfo setMarkText(String markText) {
            this.markText = markText;
            return this;
        }

        public DocumentInfo setEnableHf(boolean enableHf) {
            this.enableHf = enableHf;
            return this;
        }

        public DocumentInfo setTitleInfo(String titleInfo) {
            this.titleInfo = titleInfo;
            return this;
        }

        public DocumentInfo setBaseInfo(BaseInfo baseInfo) {
            this.baseInfo = baseInfo;
            return this;
        }

        public DocumentInfo setSignInfo(SignInfo signInfo) {
            this.signInfo = signInfo;
            return this;
        }

        public DocumentInfo setTableInfo(List<TableInfo> tableInfo) {
            this.tableInfo = tableInfo;
            return this;
        }
    }

    /**
     * 文档基本信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseInfo{
        private String title;
        private String author;
        private String subject;
        private String keywords;
        private String creator;
        public static BaseInfo of(String title,String author,String subject,String keywords,String creator){
            return new BaseInfo(title,author,subject,keywords,creator);
        }
    }

    /**
     * 文档表格信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TableInfo{
        private String title;
        private float widthPercent;
        private int[] widths;
        private List<List<String>> tableValue;

        public TableInfo setTitle(String title) {
            this.title = title;
            return this;
        }

        public TableInfo setWidthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
            return this;
        }

        public TableInfo setWidths(int[] widths) {
            this.widths = widths;
            return this;
        }

        public TableInfo setTableValue(List<List<String>> tableValue) {
            this.tableValue = tableValue;
            return this;
        }
    }

    /**
     * 落款信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInfo{
        private String userLabel;
        private String createUser;
        private String timeLabel;
        private String createTime;
        public static SignInfo of(String userLabel,String createUser,String timeLabel,String createTime){
            return new SignInfo(userLabel,createUser,timeLabel,createTime);
        }
    }

}
