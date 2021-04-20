package com.flx.netty.chat.common.utils.office.excel.simple;

import com.flx.netty.chat.common.utils.CollectionUtils;
import com.flx.netty.chat.common.utils.ObjectUtils;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/8 18:32
 * @Description:
 */
public class BaseExcel {

    /**
     * 自动列宽
     */
    private static int autoColumnSize = 5;

    /**
     * @param sheet      工作表
     * @param dataList   数据源
     * @param fieldMap   中英文字段对应关系的Map
     * @param firstIndex 开始索引
     * @param lastIndex  结束索引
     * @MethodName : fillSheet
     * @Description : 向工作表中填充数据
     *
     */
    protected static <T> void fillSheet(
            WritableSheet sheet,
            List<T> dataList,
            Map<String, String> fieldMap,
            List<WritableCellFormat> titleFormat,
            List<WritableCellFormat> contentFormat,
            int firstIndex,
            int lastIndex
    ) throws Exception {

        String[] enFields = new String[fieldMap.size()];
        String[] cnFields = new String[fieldMap.size()];

        int count = 0;
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }

        for (int i = 0; i < cnFields.length; i++) {
            sheet.addCell(new Label(i, 0, cnFields[i],titleFormat.get(i)));
        }

        int rowNo = 1;

        for (int index = firstIndex; index <= lastIndex; index++) {
            T item = dataList.get(index);
            for (int i = 0; i < enFields.length; i++) {
                Object objValue = ObjectUtils.getFieldValueByCascadeName(item,enFields[i]);
                String fieldValue = objValue == null ? "" : objValue.toString();
                if (objValue != null) {
                    if (objValue.getClass().isAssignableFrom(Date.class)) {
                        if (StringUtils.isNotBlank(fieldValue)) {
                            fieldValue = DateFormatUtils.format((Date) objValue, "yyyy/MM/dd HH:mm:ss");
                        }
                    } else if (objValue.getClass().isAssignableFrom(java.sql.Date.class)) {
                        if (StringUtils.isNotBlank(fieldValue)) {
                            fieldValue = DateFormatUtils.format((java.sql.Date) objValue, "yyyy/MM/dd HH:mm:ss");
                        }
                    }
                }
                sheet.addCell(new Label(i, rowNo, fieldValue,contentFormat.get(i)));
            }

            rowNo++;
        }

        setColumnAutoSize(sheet, autoColumnSize);
    }

    /**
     * @param ws
     * @MethodName : setColumnAutoSize
     * @Description : 设置工作表自动列宽和首行加粗
     */
    private static void setColumnAutoSize(WritableSheet ws, int extraWith) {

        for (int i = 0; i < ws.getColumns(); i++) {
            int colWith = 0;
            for (int j = 0; j < ws.getRows(); j++) {
                String content = ws.getCell(i, j).getContents();
                int cellWith = content.length();
                if (colWith < cellWith) {
                    colWith = cellWith;
                }
            }

            ws.setColumnView(i, colWith + extraWith);
        }

    }

    /**
     * 填充默认样式
     * @param formatList
     * @param size
     */
    protected static void fillDefaultStyle(List<WritableCellFormat> formatList,int size){
        if(CollectionUtils.isEmpty(formatList)){
            for (int i = 0; i < size; i++) {
                formatList.add(WritableWorkbook.NORMAL_STYLE);
            }
        }else {
            for (int i = 0; i < size; i++) {
                if(formatList.get(i)==null) {
                    formatList.set(i,WritableWorkbook.NORMAL_STYLE);
                }
            }
        }
    }


}




/**
 * WritableFont font1 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
 */
