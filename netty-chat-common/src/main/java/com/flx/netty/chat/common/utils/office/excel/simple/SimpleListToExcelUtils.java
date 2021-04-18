package com.flx.netty.chat.common.utils.office.excel.simple;


import com.flx.springboot.scaffold.common.utils.CollectionUtils;
import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fenglixiong
 */
@Slf4j
public class SimpleListToExcelUtils extends BaseExcel{

    private static int MAX_SHEET_SIZE = 65535;

    /**
     * @param list     数据源
     * @param fieldMap 类的英文属性和Excel中的中文列名的对应关系
     * @param response 使用response可以导出到浏览器
     * @Description : 导出默认的Excel（导出到浏览器，工作表的大小是2003支持的最大值）
     */
    public static <T> void listToExcel(
            String fileName,
            List<T> list,
            Map<String, String> fieldMap,
            String sheetName,
            HttpServletResponse response
    ) throws Exception {
        listToExcel(fileName, list, fieldMap,new ArrayList<>(),new ArrayList<>(), sheetName, MAX_SHEET_SIZE, response);
    }


    /**
     * @param prefix   文件名前缀
     * @param list     数据源
     * @param fieldMap 类的英文属性和Excel中的中文列名的对应关系
     * @param titleFormat 头部样式
     * @param contentFormat 内容样式
     * @param response 使用response可以导出到浏览器
     * @Description : 导出带指定样式的Excel（导出到浏览器，工作表的大小是2003支持的最大值）
     */
    public static <T> void listToExcel(
            String prefix,
            List<T> list,
            Map<String, String> fieldMap,
            List<WritableCellFormat> titleFormat,
            List<WritableCellFormat> contentFormat,
            String sheetName,
            HttpServletResponse response
    ) throws Exception {
        listToExcel(prefix, list, fieldMap,titleFormat,contentFormat, sheetName, MAX_SHEET_SIZE, response);
    }

    /**
     * @param list     数据源
     * @param fieldMap 类的英文属性和Excel中的中文列名的对应关系
     * @param out      导出流
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，工作表大小为2003支持的最大值）
     */
    public static <T> void listToExcel(
            List<T> list,
            Map<String, String> fieldMap,
            List<WritableCellFormat> titleFormat,
            List<WritableCellFormat> contentFormat,
            String sheetName,
            OutputStream out
    ) throws Exception {
        listToExcel(list, fieldMap,titleFormat,contentFormat, sheetName, MAX_SHEET_SIZE, out);
    }

    /**
     * @param prefix 文件名前缀
     * @param list           数据源
     * @param fieldMap       类的英文属性和Excel中的中文列名的对应关系
     * @param sheetSize      每个工作表中记录的最大个数
     * @param response       使用response可以导出到浏览器
     * @throws Exception
     * @MethodName : listToExcel
     * @Description : 导出Excel（导出到浏览器，可以自定义工作表的大小）
     */
    private static <T> void listToExcel(
            String prefix,
            List<T> list,
            Map<String, String> fieldMap,
            List<WritableCellFormat> titleFormat,
            List<WritableCellFormat> contentFormat,
            String sheetName,
            int sheetSize,
            HttpServletResponse response
    ) throws Exception {
        String fileName = prefix + "-" + new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
            OutputStream out = response.getOutputStream();
            listToExcel(list, fieldMap,titleFormat,contentFormat, sheetName, sheetSize, out);
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to export Excel!");
        }
    }

    /**
     * @param dataList  数据源
     * @param fieldMap  类的英文属性和Excel中的中文列名的对应关系，如果需要的是引用对象的属性，则英文属性使用类似于EL表达式的格式
     *                  如：list中存放的都是student，student中又有college属性，而我们需要学院名称，则可以这样写fieldMap.put("college.collegeName","学院名称")
     * @param sheetName 工作表的名称
     * @param sheetSize 每个工作表中记录的最大个数
     * @param out       导出流
     * @throws Exception
     * @MethodName : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，可自定义工作表大小）
     * 因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
     * 所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
     */
    private static <T> void listToExcel(
            List<T> dataList,
            Map<String, String> fieldMap,
            List<WritableCellFormat> titleFormat,
            List<WritableCellFormat> contentFormat,
            String sheetName,
            int sheetSize,
            OutputStream out
    ) throws Exception {

        if (CollectionUtils.isEmpty(dataList)) {
            throw new Exception("There is no data in Excel file!");
        }

        if (sheetSize > MAX_SHEET_SIZE || sheetSize < 1) {
            sheetSize = MAX_SHEET_SIZE;
        }

        fillDefaultStyle(titleFormat,fieldMap.size());
        fillDefaultStyle(contentFormat,fieldMap.size());

        WritableWorkbook wwb = Workbook.createWorkbook(out);

        double sheetNum = Math.ceil(dataList.size() / (double) sheetSize);

        for (int i = 0; i < sheetNum; i++) {
            if (1 == sheetNum) {
                WritableSheet sheet = wwb.createSheet(sheetName, i);
                fillSheet(sheet, dataList, fieldMap,titleFormat,contentFormat, 0, dataList.size() - 1);
            } else {
                WritableSheet sheet = wwb.createSheet(sheetName + (i + 1), i);
                int firstIndex = i * sheetSize;
                int lastIndex = (i + 1) * sheetSize - 1 > dataList.size() - 1 ? dataList.size() - 1 : (i + 1) * sheetSize - 1;
                fillSheet(sheet, dataList, fieldMap,titleFormat,contentFormat, firstIndex, lastIndex);
            }
        }

        wwb.write();
        wwb.close();

    }


}