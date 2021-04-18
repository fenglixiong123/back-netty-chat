package com.flx.netty.chat.common.utils.office.pdf;

import com.flx.springboot.scaffold.common.office.pdf.base.PdfUnit;
import com.flx.springboot.scaffold.common.utils.CollectionUtils;
import com.flx.springboot.scaffold.common.utils.file.FileUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 利用模板生成pdf
 * 1.word编辑好模板，另存为aaa.pdf
 * 2.打开Adobe Acrobat pro软件选择准备表单制作模板
 * 3.使用util工具类生成pdf
 */
public class PdfTemplateUtils {

    public static void main(String[] args) {
        /*
        @RequestMapping(value = "downloadPdf", method = RequestMethod.POST)
        public void downloadPdf(HttpServletResponse response){
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("user.name","jack");
            dataMap.put("user.age",25);
            URL resource = this.getClass().getClassLoader().getResource("heihei.pdf");
            if(resource!=null)
                log.info("resource = "+resource.getPath());
            else
                log.info("resource is null");
            Optional.ofNullable(resource).ifPresent(e->{
                String templatePath = e.getPath();
                PdfTemplateUtils.downloadPdf(dataMap,"A001",templatePath,response);
            });
        }
        */
    }

    /**
     * 下载pdf文件
     * @param pdfContent
     * @param fileName
     * @param response
     */
    public static void downloadPdf(PdfContent pdfContent,String fileName,HttpServletResponse response){
        fileName = FileUtils.getRandomName(fileName,true);
        try {
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/PDF;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8")+".pdf");
            generatePdf(pdfContent,response.getOutputStream());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将生成的PDF保存到磁盘中去
     * @param pdfContent
     * @param filePath
     * @param fileName
     * @throws IOException
     * @throws DocumentException
     */
    public static void savePdfToDesk(PdfContent pdfContent,String filePath,String fileName) throws IOException, DocumentException {
        fileName = FileUtils.getRandomName(fileName,true);
        File file = new File(filePath+fileName);
        boolean success = file.createNewFile();
        if(!success) throw new RuntimeException("create file error!");
        FileOutputStream fos = new FileOutputStream(file);
        generatePdf(pdfContent,fos);
    }

    /**
     * 将填充好的pdf生成pdf文件
     * @param out 输出源
     * @param pdfContent 填充模板信息
     * @throws DocumentException
     * @throws IOException
     */
    public static void generatePdf(PdfContent pdfContent, OutputStream out) throws DocumentException, IOException {
        if(StringUtils.isBlank(pdfContent.getTemplatePath())){
            throw new RuntimeException("Template path is not exists !");
        }
        PdfReader pdfReader = new PdfReader(pdfContent.getTemplatePath());//输入源
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//输出，array
        PdfStamper pdfStamper = new PdfStamper(pdfReader,bos);

        Document doc = new Document();
        doc.open();
        PdfCopy pdfCopy = new PdfCopy(doc,out);
        //填充好的pdf字节
        fillPdfModule(pdfStamper,pdfContent);
        int pageNo = pdfReader.getNumberOfPages();
        for (int i = 1; i <= pageNo; i++) {
            PdfImportedPage importedPage = pdfCopy.getImportedPage(new PdfReader(bos.toByteArray()),i);
            pdfCopy.addPage(importedPage);
        }
        doc.close();
        out.flush();
        out.close();
    }

    /**
     * 填充pdf模板
     * 文字
     * 表格
     * 图片
     * @param pdfStamper
     * @param pdfContent 各种数据文件
     * @throws DocumentException
     * @throws IOException
     */
    private static void fillPdfModule(PdfStamper pdfStamper,PdfContent pdfContent) throws DocumentException, IOException {
        pdfStamper.getAcroFields().addSubstitutionFont(PdfUnit.baseFont);
        dealPdfText(pdfStamper,pdfContent.getTextMap());
        dealPdfImage(pdfStamper,pdfContent.getImageMap());
        dealPdfTable(pdfStamper,pdfContent.getTableMap(),PdfUnit.baseFont);
        //设置pdf文件不可编辑
        pdfStamper.setFormFlattening(true);
        pdfStamper.close();
    }

    /**
     * 根据模板填充pdf内容
     * @param pdfStamper pdf模板
     * @param textMap 填充模板的文字数据
     */
    private static void dealPdfText(PdfStamper pdfStamper,Map<String,Object> textMap) throws IOException, DocumentException {
        if(CollectionUtils.isEmpty(textMap)){
            return;
        }
        AcroFields acroFields = pdfStamper.getAcroFields();
        for (String name : acroFields.getFields().keySet()) {
            String value = textMap.get(name) == null ? null : textMap.get(name).toString();
            acroFields.setField(name, value);
        }
    }

    /**
     * 处理表格类数据
     * 多个表格名称对应相应的表格的数据
     * K>>>>>data
     * userTable>>>List<List<String>>
     */
    private static void dealPdfTable(PdfStamper pdfStamper,Map<String, List<List<String>>> tableMap,BaseFont font) throws DocumentException {
        if(CollectionUtils.isEmpty(tableMap)){
            return;
        }
        for (String tableName : tableMap.keySet()){
            //获取表格数据
            List<List<String>> tableValue = tableMap.get(tableName);
            int pageNo = pdfStamper.getAcroFields().getFieldPositions(tableName).get(0).page;
            PdfContentByte pcb = pdfStamper.getOverContent(pageNo);
            //表格位置
            Rectangle rectangle = pdfStamper.getAcroFields().getFieldPositions(tableName).get(0).position;

            int column = tableValue.get(0).size();
            int row = tableValue.size();
            float totalWidth = rectangle.getRight() - rectangle.getLeft() - 1;
            float[] widths = new float[column];
            for (int i = 0; i < column; i++) {
                if(i==0){
                    widths[i] = 60f;
                }else {
                    widths[i] = (totalWidth-60)/(column-1);
                }
            }
            PdfPTable table = PdfUnit.createTable(widths);
            //表格数据填写
            for (int i = 0; i < row; i++) {
                List<String> rowValue = tableValue.get(i);
                for (int j = 0; j < column; j++) {
                    table.addCell(PdfUnit.createCell(String.valueOf(rowValue.get(j)),PdfUnit.textFont));
                }
            }
            table.writeSelectedRows(0,-1,rectangle.getLeft(),rectangle.getTop(),pcb);
        }
    }

    /**
     * 处理pdf图片信息，可以添加多张图片
     * @param pdfStamper
     * @param imageMap
     */
    private static void dealPdfImage(PdfStamper pdfStamper,Map<String,String> imageMap) throws IOException, DocumentException {
        if(CollectionUtils.isEmpty(imageMap)){
            return;
        }
        for (String imageKey:imageMap.keySet()){
            //图片内容
            String imagePath = imageMap.get(imageKey);
            //获取图片页码
            int pageNo = pdfStamper.getAcroFields().getFieldPositions(imageKey).get(0).page;
            //获取图片位置
            Rectangle rectangle = pdfStamper.getAcroFields().getFieldPositions(imageKey).get(0).position;
            //获取x，y坐标
            float x = rectangle.getLeft();
            float y = rectangle.getBottom();
            //根据图片路径构建image对象
            Image image = Image.getInstance(imagePath);
            //图片大小自适应
            image.scaleToFit(rectangle.getWidth(),rectangle.getHeight());
            image.setAbsolutePosition(x,y);
            //获取所在图片页面
            PdfContentByte pcb = pdfStamper.getOverContent(pageNo);
            //添加图片
            pcb.addImage(image);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PdfContent {

        /**
         * 模板路径
         */
        private String templatePath;

        /**
         * 文字数据
         */
        private Map<String,Object> textMap;

        /**
         * 图片数据
         */
        private Map<String,String> imageMap;

        /**
         * 表格数据
         */
        private Map<String, List<List<String>>> tableMap;

        public static PdfContent of(String templatePath,Map<String,Object> textMap,Map<String,String> imageMap,Map<String, List<List<String>>> tableMap){
            return new PdfContent(templatePath,textMap,imageMap,tableMap);
        }

    }

}
