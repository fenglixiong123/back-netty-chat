package com.flx.netty.chat.generator.output;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.flx.netty.chat.generator.constants.Module;
import com.flx.netty.chat.generator.entity.ConfigInfo;
import com.flx.netty.chat.generator.entity.config.GlobalInfo;
import com.flx.netty.chat.generator.entity.config.ModuleInfo;
import com.flx.netty.chat.generator.entity.config.PackageInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://blog.csdn.net/weixin_43671737/article/details/106551387
 * https://www.cnblogs.com/miaoying/p/12625920.html
 *
 * @Author: Fenglixiong
 * @Date: 2021/4/25 0:03
 * @Description:
 */
@Slf4j
@SuppressWarnings("all")
public class CustomFileOutput {

    /** controller输出模板 */
    private static final String CONSOLE_CONTROLLER_TEMPLATE = "templates-custom/console/controller.java.ftl";
    /** service输出模板 */
    private static final String CONSOLE_SERVICE_TEMPLATE = "templates-custom/console/service.java.ftl";
    /** serviceImpl输出模板 */
    private static final String CONSOLE_SERVICE_IMPL_TEMPLATE = "templates-custom/console/serviceImpl.java.ftl";
    /** manager.java输出模板 */
    private static final String CRUD_MANAGER_TEMPLATE = "templates-custom/crud/manager.java.ftl";
    /** dao.java输出模板 */
    private static final String CRUD_DAO_TEMPLATE = "templates-custom/crud/dao.java.ftl";
    /** mapper.xml输出模板 */
    private static final String CRUD_XML_TEMPLATE = "templates-custom/crud/mapper.xml.ftl";
    /** entity输出模板 */
    private static final String CRUD_ENTITY_TEMPLATE = "templates-custom/crud/entity.java.ftl";
    /** vo输出模板 */
    private static final String API_VO_TEMPLATE = "templates-custom/api/vo.java.ftl";
    /** IService输出模板 */
    private static final String API_CLIENT_TEMPLATE = "templates-custom/api/client.java.ftl";


    @Getter
    public static Map<String,String> pathMap = new HashMap<>();

    /**
     * 自定义输出文件配置
     */
    public List<FileOutConfig> fileOutConfigList(ConfigInfo config) {

        String controllerOutputPath = getPath(config,Module.CONTROLLER);
        String serviceOutputPath = getPath(config,Module.SERVICE);
        String serviceImplOutputPath = getPath(config,Module.SERVICE_IMPL);
        String managerOutputPath = getPath(config,Module.MANAGER);
        String daoOutputPath = getPath(config,Module.DAO);
        String entityOutputPath = getPath(config,Module.ENTITY);
        String mapperOutputPath = getPath(config,Module.MAPPER);
        String voOutputPath = getPath(config,Module.VO);
        String clientOutputPath = getPath(config,Module.CLIENT);

        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("controllerOutputPath = {}",controllerOutputPath);
        log.info("serviceOutputPath = {}",serviceOutputPath);
        log.info("serviceImplOutputPath = {}",serviceImplOutputPath);
        log.info("managerOutputPath = {}",managerOutputPath);
        log.info("daoOutputPath = {}",daoOutputPath);
        log.info("entityOutputPath = {}",entityOutputPath);
        log.info("mapperOutputPath = {}",mapperOutputPath);
        log.info("voOutputPath = {}",voOutputPath);
        log.info("ApiClientOutputPath = {}",clientOutputPath);
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        List<FileOutConfig> outConfigList = new ArrayList<>();

        // controller文件输出
        outConfigList.add(new FileOutConfig(CONSOLE_CONTROLLER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = controllerOutputPath + tableInfo.getControllerName() + StringPool.DOT_JAVA;
                pathMap.put(CONSOLE_CONTROLLER_TEMPLATE,path);
                return path;
            }
        });
        // service文件输出
        outConfigList.add(new FileOutConfig(CONSOLE_SERVICE_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path =  serviceOutputPath + tableInfo.getServiceName() + StringPool.DOT_JAVA;
                pathMap.put(CONSOLE_SERVICE_TEMPLATE,path);
                return path;
            }
        });
        // serviceImpl文件输出
        outConfigList.add(new FileOutConfig(CONSOLE_SERVICE_IMPL_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = serviceImplOutputPath + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
                pathMap.put(CONSOLE_SERVICE_IMPL_TEMPLATE,path);
                return path;
            }
        });
        // manager文件输出
        outConfigList.add(new FileOutConfig(CRUD_MANAGER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = managerOutputPath + tableInfo.getEntityName()+"Manager" + StringPool.DOT_JAVA;
                pathMap.put(CRUD_MANAGER_TEMPLATE,path);
                return path;
            }
        });
        // dao文件输出
        outConfigList.add(new FileOutConfig(CRUD_DAO_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = daoOutputPath + tableInfo.getMapperName() + StringPool.DOT_JAVA;
                pathMap.put(CRUD_DAO_TEMPLATE,path);
                return path;
            }
        });
        // entity文件输出
        outConfigList.add(new FileOutConfig(CRUD_ENTITY_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = entityOutputPath + tableInfo.getEntityName() + StringPool.DOT_JAVA;
                pathMap.put(CRUD_ENTITY_TEMPLATE,path);
                return path;
            }
        });

        /*
        // mapper xml文件输出
        outConfigList.add(new FileOutConfig(CRUD_XML_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = mapperOutputPath + tableInfo.getEntityName()+ "Mapper" + StringPool.DOT_XML;
                pathMap.put(CRUD_XML_TEMPLATE,path);
                return path;
            }
        });
        */

        // vo文件输出
        outConfigList.add(new FileOutConfig(API_VO_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = voOutputPath + tableInfo.getEntityName()+"VO" + StringPool.DOT_JAVA;
                pathMap.put(API_VO_TEMPLATE,path);
                return path;
            }
        });
        // client文件输出
        outConfigList.add(new FileOutConfig(API_CLIENT_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = clientOutputPath + tableInfo.getEntityName()+"Client" + StringPool.DOT_JAVA;
                pathMap.put(API_CLIENT_TEMPLATE,path);
                return path;
            }
        });

        return outConfigList;
    }

    /**
     * 获取文件输出路径
     * @param config
     * @param key
     * @return
     */
    private String getPath(ConfigInfo config,String key) {
        GlobalInfo global = config.getGlobal();
        ModuleInfo module = config.getModule();
        PackageInfo pack = config.getPack();
        StringBuilder sb = new StringBuilder();
        sb.append(global.getBasePath());
        if(module.getParentModule()!=null){
            sb.append(module.getParentModule()).append("/");
        }
        String subModule = getSubModule(module,key);
        if(subModule!=null) {
            sb.append(subModule);
        }
        if(key.equals("mapper")){
            sb.append("/src/main/resources/mapper/");
        }else {
            sb.append("/src/main/java/");
            sb.append(pack.getParentPackage().replace(".", "/"));
            sb.append("/");
            String subPackage = getSubPackage(pack,key);
            if(subPackage!=null) {
                sb.append(subPackage.replace(".", "/"));
            }
            sb.append("/");
            sb.append(key.replace(".","/"));
            sb.append("/");
        }
        return sb.toString();
    }

    /**
     * 获取子模块名称
     * @param module
     * @param key
     * @return
     */
    private String getSubModule(ModuleInfo module,String key){
        switch (key){
            case Module.CONTROLLER :
                return module.getConsoleModule();
            case Module.SERVICE:
                return module.getServiceModule();
            case Module.SERVICE_IMPL:
                return module.getServiceImplModule();
            case Module.MANAGER:
                return module.getManagerModule();
            case Module.DAO:
                return module.getDaoModule();
            case Module.ENTITY:
                return module.getEntityModule();
            case Module.MAPPER:
                return module.getMapperModule();
            case Module.VO:
                return module.getVoModule();
            case Module.CLIENT:
                return module.getClientModule();
        }
        return null;
    }

    /**
     * 获取子包名称
     * @param pack
     * @param key
     * @return
     */
    private String getSubPackage(PackageInfo pack,String key){
        switch (key){
            case Module.CONTROLLER :
                return pack.getControllerPackage();
            case Module.SERVICE:
                return pack.getServicePackage();
            case Module.SERVICE_IMPL:
                return pack.getServiceImplPackage();
            case Module.MANAGER:
                return pack.getManagerPackage();
            case Module.DAO:
                return pack.getDaoPackage();
            case Module.ENTITY:
                return pack.getEntityPackage();
            case Module.VO:
                return pack.getVoPackage();
            case Module.CLIENT:
                return pack.getClientPackage();
        }
        return null;
    }

}
