package com.cm.shirotest.config.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器
 * @author 陈萌
 * @date 2021/11/28 00:37
 */
public class CodeGenerator {

    public static void main(String[] args) {

        String tableNames = "sys_user_role,sys_user,sys_role,sys_permission_role,sys_permission";

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir"); // 当前目录
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("陈萌");
        gc.setOpen(false); // 打开文件夹
        gc.setFileOverride(false); // 是否覆盖
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setIdType(IdType.AUTO);
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/shiro_test?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setSchemaName("test");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
       // pc.setModuleName("repository"); //父包模块名
        pc.setParent("com.cm.shirotest"); //父包名
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setXml("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 策略配置 数据库表配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); // 数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tableNames.split(",")); // 表名，多个英文逗号分割
        strategy.setControllerMappingHyphenStyle(true); // 驼峰转连字符
        strategy.setTablePrefix("sys" + "_"); // 表前缀覆盖
        strategy.setEntityLombokModel(true);
        strategy.setLogicDeleteFieldName("delete_state"); //逻辑删除属性名称
        // 自动填充
        TableFill creat_time = new TableFill("create_time", FieldFill.INSERT);
        TableFill modify_time = new TableFill("modify_time", FieldFill.UPDATE);
        List<TableFill> fillList = new ArrayList<>();
        fillList.add(creat_time);
        fillList.add(modify_time);
        strategy.setTableFillList(fillList);
        mpg.setStrategy(strategy);

        /*
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };


        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com.cm.repository.mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir(filePath);
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
 */
/*
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity2.java");
        templateConfig.setService("templates/service2.java");
        templateConfig.setController("templates/controller.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
*/

        mpg.execute();
    }

}