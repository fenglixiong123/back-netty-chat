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
public class SimpleFileOutput {

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

    @Getter
    public static Map<String,String> pathMap = new HashMap<>();

    /**
     * 自定义输出文件配置
     */
    public List<FileOutConfig> fileOutConfigList(ConfigInfo config) {

        String controllerOutputPath = getPath(config, Module.CONTROLLER);
        String serviceOutputPath = getPath(config,Module.SERVICE);
        String serviceImplOutputPath = getPath(config,Module.SERVICE_IMPL);
        String daoOutputPath = getPath(config,Module.DAO);
        String entityOutputPath = getPath(config,Module.ENTITY);
        String mapperOutputPath = getPath(config,Module.MAPPER);
        String voOutputPath = getPath(config,Module.VO);

        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("controllerOutputPath = {}",controllerOutputPath);
        log.info("serviceOutputPath = {}",serviceOutputPath);
        log.info("serviceImplOutputPath = {}",serviceImplOutputPath);
        log.info("daoOutputPath = {}",daoOutputPath);
        log.info("entityOutputPath = {}",entityOutputPath);
        log.info("mapperOutputPath = {}",mapperOutputPath);
        log.info("voOutputPath = {}",voOutputPath);
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        List<FileOutConfig> outConfigList = new ArrayList<>();


        // controller文件输出
        outConfigList.add(new FileOutConfig(CONTROLLER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = controllerOutputPath + tableInfo.getControllerName() + StringPool.DOT_JAVA;
                pathMap.put(CONTROLLER_TEMPLATE,path);
                return path;
            }
        });
        // service文件输出
        outConfigList.add(new FileOutConfig(SERVICE_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = serviceOutputPath + tableInfo.getServiceName() + StringPool.DOT_JAVA;
                pathMap.put(SERVICE_TEMPLATE,path);
                return path;
            }
        });
        // serviceImpl文件输出
        outConfigList.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = serviceImplOutputPath + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
                pathMap.put(SERVICE_IMPL_TEMPLATE,path);
                return path;
            }
        });
        // dao文件输出
        outConfigList.add(new FileOutConfig(DAO_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = daoOutputPath + tableInfo.getMapperName() + StringPool.DOT_JAVA;
                pathMap.put(DAO_TEMPLATE,path);
                return path;
            }
        });
        // entity文件输出
        outConfigList.add(new FileOutConfig(ENTITY_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = entityOutputPath + tableInfo.getEntityName() + StringPool.DOT_JAVA;
                pathMap.put(ENTITY_TEMPLATE,path);
                return path;
            }
        });
        // vo文件输出
        outConfigList.add(new FileOutConfig(VO_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = voOutputPath + tableInfo.getEntityName()+"VO" + StringPool.DOT_JAVA;
                pathMap.put(VO_TEMPLATE,path);
                return path;
            }
        });

        // mapper xml文件输出
        outConfigList.add(new FileOutConfig(XML_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = mapperOutputPath + tableInfo.getEntityName()+ "Mapper" + StringPool.DOT_XML;
                pathMap.put(XML_TEMPLATE,path);
                return path;
            }
        });

        return outConfigList;
    }

    /**
     * 获取输出路径
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
        if(module.getParentModule()!=null) {
            sb.append(module.getParentModule());
        }
        if(key.equals("mapper")){
            sb.append("/src/main/resources/mapper/");
        }else {
            sb.append("/src/main/java/");
            sb.append(pack.getParentPackage().replace(".", "/"));
            sb.append("/");
            sb.append(key.replace(".","/"));
            sb.append("/");
        }
        return sb.toString();
    }

}
