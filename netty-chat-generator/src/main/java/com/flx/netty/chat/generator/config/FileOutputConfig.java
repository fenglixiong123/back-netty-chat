package com.flx.netty.chat.generator.config;

import com.baomidou.mybatisplus.generator.config.FileOutConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/weixin_43671737/article/details/106551387
 * https://blog.csdn.net/weixin_43671737/article/details/106551387
 * https://www.cnblogs.com/miaoying/p/12625920.html
 * http://www.zuidaima.com/blog/4477548156537856.htm
 * @Author: Fenglixiong
 * @Date: 2021/4/25 0:03
 * @Description:
 */
public class FileOutputConfig {

    /** entity输出模板 */
    private static String ENTITY_TEMPLATE = "templates/entity.java.ftl";
    /** mapper.xml输出模板 */
    private static String XML_TEMPLATE = "templates/mapper.xml.ftl";
    /** mapper.java输出模板 */
    private static String MAPPER_TEMPLATE = "templates/mapper.java.ftl";
    /** service输出模板 */
    private static String SERVICE_TEMPLATE = "templates/service.java.ftl";
    /** serviceImpl输出模板 */
    private static String SERVICE_IMPL_TEMPLATE = "templates/serviceImpl.java.ftl";
    /** controller输出模板 */
    private static String CONTROLLER_TEMPLATE = "templates/controller.java.ftl";


    /**
     * 自定义输出文件配置
     */
    private static List<FileOutConfig> fileOutConfigList() {

        // 自定义输出模板和位置
        // 文件位置输出模式: file output path = projectPath + XX_OUTPUT_PATH  + File
        // XX_OUTPUT_PATH = modulePath + packagePath
        private static String ENTITY_OUTPUT_PATH = DAO_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + DAO_OUTPUT_MODULE + ENTITY_PATH;

        private static String XML_OUTPUT_PATH = DAO_OUTPUT_MODULE + "/src/main" + XML_OUTPUT_MODULE + XML_PATH;

        private static String MAPPER_OUTPUT_PATH = DAO_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + DAO_OUTPUT_MODULE + MAPPER_PATH;

        private static String SERVICE_OUTPUT_PATH = SERVICE_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + SERVICE_OUTPUT_MODULE + SERVICE_PATH;

        private static String SERVICE_IMPL_OUTPUT_PATH = SERVICE_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + SERVICE_OUTPUT_MODULE + SERVICE_IMPL_PATH;

        private static String CONTROLLER_OUTPUT_PATH = Controller_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + Controller_OUTPUT_MODULE + CONTROLLER_PATH;

        List<FileOutConfig> list = new ArrayList<>();
        // 当前项目路径
        final String projectPath = System.getProperty("user.dir");

        // 实体类文件输出
        list.add(new FileOutConfig(ENTITY_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + ENTITY_OUTPUT_PATH + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        // mapper xml文件输出
        list.add(new FileOutConfig(XML_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + XML_OUTPUT_PATH + tableInfo.getMapperName() + StringPool.DOT_XML;
            }
        });
        // mapper文件输出
        list.add(new FileOutConfig(MAPPER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + MAPPER_OUTPUT_PATH + tableInfo.getMapperName() + StringPool.DOT_JAVA;
            }
        });
        // service文件输出
        list.add(new FileOutConfig(SERVICE_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + SERVICE_OUTPUT_PATH + tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });
        // service impl文件输出
        list.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + SERVICE_IMPL_OUTPUT_PATH + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });
        // controller文件输出
        list.add(new FileOutConfig(CONTROLLER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + CONTROLLER_OUTPUT_PATH + tableInfo.getControllerName() + StringPool.DOT_JAVA;
            }
        });

        return list;
    }

}
