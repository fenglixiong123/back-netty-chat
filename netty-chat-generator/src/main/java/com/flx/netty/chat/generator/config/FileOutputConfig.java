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
 * https://www.cnblogs.com/miaoying/p/12625920.html
 *
 * @Author: Fenglixiong
 * @Date: 2021/4/25 0:03
 * @Description:
 */
@Slf4j
public class FileOutputConfig {

    /** controller输出模板 */
    private static final String CONSOLE_CONTROLLER_TEMPLATE = "templates-ftl/console/controller.java.ftl";
    /** service输出模板 */
    private static final String CONSOLE_SERVICE_TEMPLATE = "templates-ftl/console/service.java.ftl";
    /** serviceImpl输出模板 */
    private static final String CONSOLE_SERVICE_IMPL_TEMPLATE = "templates-ftl/console/serviceImpl.java.ftl";
    /** manager.java输出模板 */
    private static final String CRUD_MANAGER_TEMPLATE = "templates-ftl/crud/manager.java.ftl";
    /** dao.java输出模板 */
    private static final String CRUD_DAO_TEMPLATE = "templates-ftl/crud/dao.java.ftl";
    /** mapper.xml输出模板 */
    private static final String CRUD_XML_TEMPLATE = "templates-ftl/crud/mapper.xml.ftl";
    /** entity输出模板 */
    private static final String CRUD_ENTITY_TEMPLATE = "templates-ftl/crud/entity.java.ftl";
    /** vo输出模板 */
    private static final String API_VO_TEMPLATE = "templates-ftl/api/vo.java.ftl";
    /** IService输出模板 */
    private static final String API_CLIENT_TEMPLATE = "templates-ftl/api/client.java.ftl";

    private static final String baseOutputPath;
    private static final String parentModule;
    private static final String parentPackage;

    //各层文件输出路径
    private static final String ConsoleControllerOutputPath;
    private static final String ConsoleServiceOutputPath;
    private static final String ConsoleServiceImplOutputPath;
    private static final String CrudManagerOutputPath;
    private static final String CrudDaoOutputPath;
    private static final String CrudEntityOutputPath;
    private static final String CrudMapperOutputPath;
    private static final String ApiVoOutputPath;
    private static final String ApiClientOutputPath;


    static {
        baseOutputPath = System.getProperty("user.dir") + FileConstant.pathSeparator;
        parentModule = PropertyUtils.get("flx.generator.module.parent.name");
        parentPackage = PropertyUtils.get("flx.generator.package.parent.name");

        ConsoleControllerOutputPath = getPath("controller");
        ConsoleServiceOutputPath = getPath("service");
        ConsoleServiceImplOutputPath = getPath("service.impl");
        CrudManagerOutputPath = getPath("manager");
        CrudDaoOutputPath = getPath("dao");
        CrudEntityOutputPath = getPath("entity");
        CrudMapperOutputPath = getPath("mapper");
        ApiVoOutputPath = getPath("vo");
        ApiClientOutputPath = getPath("client");

        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("ConsoleControllerOutputPath = {}",ConsoleControllerOutputPath);
        log.info("ConsoleServiceOutputPath = {}",ConsoleServiceOutputPath);
        log.info("ConsoleServiceImplOutputPath = {}",ConsoleServiceImplOutputPath);
        log.info("CrudManagerOutputPath = {}",CrudManagerOutputPath);
        log.info("CrudDaoOutputPath = {}",CrudDaoOutputPath);
        log.info("CrudEntityOutputPath = {}",CrudEntityOutputPath);
        log.info("CrudMapperOutputPath = {}",CrudMapperOutputPath);
        log.info("ApiVoOutputPath = {}",ApiVoOutputPath);
        log.info("ApiClientOutputPath = {}",ApiClientOutputPath);
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
        outConfigList.add(new FileOutConfig(CONSOLE_CONTROLLER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return ConsoleControllerOutputPath + tableInfo.getControllerName() + StringPool.DOT_JAVA;
            }
        });
        // service文件输出
        outConfigList.add(new FileOutConfig(CONSOLE_SERVICE_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return ConsoleServiceOutputPath + tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });
        // serviceImpl文件输出
        outConfigList.add(new FileOutConfig(CONSOLE_SERVICE_IMPL_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return ConsoleServiceImplOutputPath + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });
        // manager文件输出
        outConfigList.add(new FileOutConfig(CRUD_MANAGER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return CrudManagerOutputPath + tableInfo.getEntityName()+"Manager" + StringPool.DOT_JAVA;
            }
        });
        // dao文件输出
        outConfigList.add(new FileOutConfig(CRUD_DAO_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return CrudDaoOutputPath + tableInfo.getMapperName() + StringPool.DOT_JAVA;
            }
        });
        // entity文件输出
        outConfigList.add(new FileOutConfig(CRUD_ENTITY_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return CrudEntityOutputPath + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        /*
        // mapper xml文件输出
        outConfigList.add(new FileOutConfig(CRUD_XML_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return CrudMapperOutputPath + tableInfo.getEntityName()+ "Mapper" + StringPool.DOT_XML;
            }
        });
        */

        // vo文件输出
        outConfigList.add(new FileOutConfig(API_VO_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return ApiVoOutputPath + tableInfo.getEntityName()+"VO" + StringPool.DOT_JAVA;
            }
        });
        // client文件输出
        outConfigList.add(new FileOutConfig(API_CLIENT_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return ApiClientOutputPath + tableInfo.getEntityName()+"Client" + StringPool.DOT_JAVA;
            }
        });

        return outConfigList;
    }

    private static String getPath(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseOutputPath);
        if(parentModule!=null){
            sb.append(parentModule).append("/");
        }
        String subModule = PropertyUtils.get("flx.generator.module." + key + ".name");
        if(subModule!=null) {
            sb.append(subModule);
        }
        if(key.equals("mapper")){
            sb.append("/src/main/resources/mapper/");
        }else {
            sb.append("/src/main/java/");
            sb.append(parentPackage.replace(".", "/"));
            sb.append("/");
            String subPackage = PropertyUtils.get("flx.generator.package.module." + key + ".name");
            if(subPackage!=null) {
                sb.append(subPackage.replace(".", "/"));
            }
            sb.append("/");
            sb.append(key.replace(".","/"));
            sb.append("/");
        }
        return sb.toString();
    }

}
