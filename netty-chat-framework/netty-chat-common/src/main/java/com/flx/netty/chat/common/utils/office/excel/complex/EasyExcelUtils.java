package com.flx.netty.chat.common.utils.office.excel.complex;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Fenglixiong
 * @Date: 2020/6/5 14:17
 * @Description:
 */
public class EasyExcelUtils {

    private static Map<String, String> updateHeadMap() {
        Map<String, String> headMap = new LinkedHashMap<>();
        headMap.put("slotCode", "货位编码");
        headMap.put("roadwayPointCode", "巷道内作业点");
        headMap.put("extensionDistance", "距货架伸叉距离");
        headMap.put("groundHeight","库位层距地高度");
        return headMap;
    }

    public static Map<String, String> test(Map<String, String> headMap,String language){
        if ("zh-cn".equals(language)) {
            return headMap;
        } else if("en-us".equals(language)) {
            for (String key : headMap.keySet()) {
                headMap.replace(key, key);
            }
            return headMap;
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(updateHeadMap());

    }

}


