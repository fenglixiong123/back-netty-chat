package com.flx.netty.chat.common.utils.office.excel.simple;

import com.flx.netty.chat.common.utils.ObjectUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.*;

/**
 * @Author: Fenglixiong
 * @Date: 2020/7/8 19:34
 * @Description: excel转换成list数据
 */
@Slf4j
public class SimpleExcelToListUtils {

    /**
     * 读取excel指定sheet页的数据并转化成list
     * @param in
     * @param sheetName
     * @param entityClass
     * @param fieldMap
     * @param uniqueFields 指定业务主键组合（即复合主键），这些列的组合不能重复
     * @return
     * @throws Exception
     */
    public static <T> List<T> excelToList(
            InputStream in,
            String sheetName,
            Class<T> entityClass,
            Map<String, String> fieldMap,
            String[] uniqueFields
    ) throws Exception {
        Workbook wb = Workbook.getWorkbook(in);
        return excelToList(wb, sheetName, entityClass, fieldMap, uniqueFields);
    }

    /**
     * 读取excel所有的sheet页的数据并转化成list
     * @param in
     * @param entityClass
     * @param fieldMap
     * @param uniqueFields
     * @return
     * @throws Exception
     */
    public static <T> List<T> excelToList(
            InputStream in,
            Class<T> entityClass,
            Map<String, String> fieldMap,
            String[] uniqueFields
    ) throws Exception {
        Workbook workbook = Workbook.getWorkbook(in);
        List<T> list = new ArrayList<>();
        for (String sheetName : workbook.getSheetNames()) {
            list.addAll(excelToList(workbook, sheetName, entityClass, fieldMap, uniqueFields));
        }
        return list;
    }

    /**
     * @param entityClass  ：List中对象的类型（Excel中的每一行都要转化为该类型的对象）
     * @param fieldMap     ：Excel中的中文列头和类的英文属性的对应关系Map
     * @param uniqueFields ：指定业务主键组合（即复合主键），这些列的组合不能重复
     * @return ：List
     * @throws Exception
     * @MethodName : excelToList
     * @Description : 将Excel转化为List
     */
    public static <T> List<T> excelToList(
            Workbook wb,
            String sheetName,
            Class<T> entityClass,
            Map<String, String> fieldMap,
            String[] uniqueFields
    ) throws Exception {

        List<T> resultList = new ArrayList<>();

        Sheet sheet = wb.getSheet(sheetName);

        int realRows = 0;
        for (int i = 0; i < sheet.getRows(); i++) {
            int nullCols = 0;
            for (int j = 0; j < sheet.getColumns(); j++) {
                Cell currentCell = sheet.getCell(j, i);
                if (currentCell == null || StringUtils.isBlank(currentCell.getContents())) {
                    nullCols++;
                }
            }

            if (nullCols == sheet.getColumns()) {
                break;
            } else {
                realRows++;
            }
        }

        if (realRows <= 1) {
            throw new Exception("There is no data in Excel file!");
        }


        Cell[] firstRow = sheet.getRow(0);
        String[] titleFieldNames = new String[firstRow.length];

        //获取Excel中的列名
        for (int i = 0; i < firstRow.length; i++) {
            titleFieldNames[i] = firstRow[i].getContents().trim();
        }

        //判断需要的字段在Excel中是否都存在
        List<String> excelFieldList = Arrays.asList(titleFieldNames);
        for (Map.Entry e : fieldMap.entrySet()) {
            if (!excelFieldList.contains(e.getValue().toString())) {
                throw new Exception("Lack of necessary fields in Excel or incorrect field names {0}!||"+e.getValue());
            }
        }

        Map<String, Integer> colMap = new HashMap<>(10);
        for (int i = 0; i < titleFieldNames.length; i++) {
            colMap.put(titleFieldNames[i], firstRow[i].getColumn());
        }

        if (null != uniqueFields) {
            //判断是否有重复行
            //1.获取uniqueFields指定的列
            Cell[][] uniqueCells = new Cell[uniqueFields.length][];
            for (int i = 0; i < uniqueFields.length; i++) {
                int col = colMap.get(uniqueFields[i]);
                uniqueCells[i] = sheet.getColumn(col);
            }

            //2.从指定列中寻找重复行
            for (int i = 1; i < realRows; i++) {
                int nullCols = 0;
                for (int j = 0; j < uniqueFields.length; j++) {
                    String currentContent = uniqueCells[j][i].getContents();
                    Cell sameCell = sheet.findCell(currentContent,
                            uniqueCells[j][i].getColumn(),
                            uniqueCells[j][i].getRow() + 1,
                            uniqueCells[j][i].getColumn(),
                            uniqueCells[j][realRows - 1].getRow(),
                            true);
                    if (sameCell != null) {
                        nullCols++;
                    }
                }

                if (nullCols == uniqueFields.length) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < uniqueFields.length; j++) {
                        String currentContent = uniqueCells[j][i].getContents();
                        stringBuilder.append("|").append(currentContent);
                    }
                    throw new Exception("There are duplicate rows in Excel,please check it,content={0}!||" + stringBuilder.toString());
                }
            }
        }

        //将sheet转换为list
        for (int i = 1; i < realRows; i++) {
            T entity = entityClass.newInstance();
            for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
                String cnNormalName = entry.getValue();
                String enNormalName = entry.getKey();
                int col = colMap.get(cnNormalName);
                String content = sheet.getCell(col, i).getContents().trim();
                ObjectUtils.setFieldValueByName(entity,enNormalName, content);
            }
            resultList.add(entity);
        }
        return resultList;
    }



}
