package com.flx.netty.chat.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.flx.netty.chat.common.constants.FileConstant;
import com.flx.netty.chat.generator.utils.property.simple.PropertyUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/weixin_43671737/article/details/106551387
 * https://www.cnblogs.com/miaoying/p/12625920.html
 *
 * @Author: Fenglixiong
 * @Date: 2021/4/25 0:03
 * @Description:
 */
@Slf4j
public class SimpleFileOutputConfig {

    /** controller输出模板 */
    private static final String CONTROLLER_TEMPLATE = "templates-simple/controller.java.ftl";
    /** service输出模板 */
    private static final String SERVICE_TEMPLATE = "templates-simple/service.java.ftl";
    /** serviceImpl输出模板 */
    private static final String SERVICE_IMPL_TEMPLATE = "templates-simple/serviceImpl.java.ftl";
    /** dao.java输出模板 */
    private static final String DAO_TEMPLATE = "templates-simple/dao.java.ftl";
    /** mapper.xml输出模板 */
    private static final String XML_TEMPLATE = "templates-simple/mapper.xml.ftl";
    /** entity输出模板 */
    private static final String ENTITY_TEMPLATE = "templates-simple/entity.java.ftl";
    /** vo输出模板 */
    private static final String VO_TEMPLATE = "templates-simple/vo.java.ftl";

    private static final String baseOutputPath;
    private static final String moduleName;
    private static final String parentPackage;

    //各层文件输出路径
    private static final String ControllerOutputPath;
    private static final String ServiceOutputPath;
    private static final String ServiceImplOutputPath;
    private static final String DaoOutputPath;
    private static final String EntityOutputPath;
    private static final String MapperOutputPath;
    private static final String VoOutputPath;


    static {
        baseOutputPath = System.getProperty("user.dir") + FileConstant.pathSeparator;
        moduleName = PropertyUtils.get("flx.generator.module.name");
        parentPackage = PropertyUtils.get("flx.generator.package.parent.name");

        ControllerOutputPath = getPath("controller");
        ServiceOutputPath = getPath("service");
        ServiceImplOutputPath = getPath("service.impl");
        DaoOutputPath = getPath("dao");
        EntityOutputPath = getPath("entity");
        MapperOutputPath = getPath("mapper");
        VoOutputPath = getPath("vo");

        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("ControllerOutputPath = {}",ControllerOutputPath);
        log.info("ServiceOutputPath = {}",ServiceOutputPath);
        log.info("ServiceImplOutputPath = {}",ServiceImplOutputPath);
        log.info("DaoOutputPath = {}",DaoOutputPath);
        log.info("EntityOutputPath = {}",EntityOutputPath);
        log.info("MapperOutputPath = {}",MapperOutputPath);
        log.info("VoOutputPath = {}",VoOutputPath);
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
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
                return ControllerOutputPath + tableInfo.getControllerName() + StringPool.DOT_JAVA;
            }
        });
        // service文件输出
        outConfigList.add(new FileOutConfig(SERVICE_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return ServiceOutputPath + tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });
        // serviceImpl文件输出
        outConfigList.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return ServiceImplOutputPath + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });
        // dao文件输出
        outConfigList.add(new FileOutConfig(DAO_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return DaoOutputPath + tableInfo.getMapperName() + StringPool.DOT_JAVA;
            }
        });
        // entity文件输出
        outConfigList.add(new FileOutConfig(ENTITY_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return EntityOutputPath + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        // vo文件输出
        outConfigList.add(new FileOutConfig(VO_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return VoOutputPath + tableInfo.getEntityName()+"VO" + StringPool.DOT_JAVA;
            }
        });

        // mapper xml文件输出
        outConfigList.add(new FileOutConfig(XML_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return MapperOutputPath + tableInfo.getEntityName()+ "Mapper" + StringPool.DOT_XML;
            }
        });

        return outConfigList;
    }

    private static String getPath(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseOutputPath);
        if(moduleName!=null) {
            sb.append(moduleName);
        }
        if(key.equals("mapper")){
            sb.append("/src/main/resources/mapper/");
        }else {
            sb.append("/src/main/java/");
            sb.append(parentPackage.replace(".", "/"));
            sb.append("/");
            sb.append(key.replace(".","/"));
            sb.append("/");
        }
        return sb.toString();
    }

}
