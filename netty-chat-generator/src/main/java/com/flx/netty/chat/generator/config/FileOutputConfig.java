package com.flx.netty.chat.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.flx.netty.chat.common.constants.FileConstant;
import com.flx.netty.chat.generator.utils.property.custom.PropertyUtils;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class FileOutputConfig {

    /** controller输出模板 */
    private static String CONTROLLER_TEMPLATE = "templates-ftl/controller.java.ftl";
    /** service输出模板 */
    private static String SERVICE_TEMPLATE = "templates-ftl/service.java.ftl";
    /** serviceImpl输出模板 */
    private static String SERVICE_IMPL_TEMPLATE = "templates-ftl/serviceImpl.java.ftl";
    /** mapper.java输出模板 */
    private static String MAPPER_TEMPLATE = "templates-ftl/mapper.java.ftl";
    /** mapper.xml输出模板 */
    private static String XML_TEMPLATE = "templates-ftl/mapper.xml.ftl";
    /** entity输出模板 */
    private static String ENTITY_TEMPLATE = "templates-ftl/entity.java.ftl";

    private static String baseOutputPath;
    private static String parentPackage;

    //文件输出路径
    private static String controllerOutputPath;
    private static String serviceOutputPath;
    private static String serviceImplOutputPath;
    private static String daoOutputPath;
    private static String entityOutputPath;
    private static String mapperOutputPath;


    static {
        baseOutputPath = System.getProperty("user.dir") + FileConstant.pathSeparator;
        parentPackage = PropertyUtils.get("flx.generator.package.parent.name");

        controllerOutputPath = getPath("controller");
        serviceOutputPath = getPath("service");
        serviceImplOutputPath = getPath("service.impl");
        daoOutputPath = getPath("dao");
        entityOutputPath = getPath("entity");
        mapperOutputPath = getPath("mapper");

        log.info("ControllerOutputPath = {}",controllerOutputPath);
        log.info("ServiceOutputPath = {}",serviceOutputPath);
        log.info("ServiceImplOutputPath = {}",serviceImplOutputPath);
        log.info("DaoOutputPath = {}",daoOutputPath);
        log.info("EntityOutputPath = {}",entityOutputPath);
        log.info("MapperOutputPath = {}",mapperOutputPath);
    }

    public static void main(String[] args) {

    }

    /**
     * 自定义输出文件配置
     */
    public static List<FileOutConfig> fileOutConfigList() {

        List<FileOutConfig> outConfigList = new ArrayList<>();


        // controller文件输出
        outConfigList.add(new FileOutConfig(CONTROLLER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return controllerOutputPath + tableInfo.getControllerName() + StringPool.DOT_JAVA;
            }
        });
        // service文件输出
        outConfigList.add(new FileOutConfig(SERVICE_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return serviceOutputPath + tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });
        // service文件输出
        outConfigList.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return serviceImplOutputPath + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });
        // dao文件输出
        outConfigList.add(new FileOutConfig(MAPPER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return daoOutputPath + tableInfo.getMapperName() + StringPool.DOT_JAVA;
            }
        });
        // entity文件输出
        outConfigList.add(new FileOutConfig(ENTITY_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return entityOutputPath + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        // mapper xml文件输出
        outConfigList.add(new FileOutConfig(XML_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return mapperOutputPath + tableInfo.getEntityName()+ "Mapper" + StringPool.DOT_XML;
            }
        });

        return outConfigList;
    }

    private static String getPath(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseOutputPath);
        sb.append(PropertyUtils.get("flx.generator.module." + key + ".name"));
        if(key.equals("mapper")){
            sb.append("/src/main/resources/mapper/");
        }else {
            sb.append("/src/main/java/");
            sb.append(parentPackage.replace(".", "/"));
            sb.append("/");
            sb.append(PropertyUtils.get("flx.generator.package.module." + key + ".name").replace(".","/"));
            sb.append("/");
            sb.append(key.replace(".","/"));
            sb.append("/");
        }
        return sb.toString();
    }

}
