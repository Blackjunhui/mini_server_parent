package com.black.system.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.black.system.base.BaseEntity;
import com.black.system.templateEngine.MyFreemarkerTemplateEngine;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.*;

/**
 * @author black
 * @date 2023/4/20 12:42
 */
public class MyGenerator {

    // TODO 修改服务名以及数据表名
    /**
     * 顶层包名
     */
    private static final String SERVICE_NAME = "system";
    /**
     * 最终包路径
     */
    private static final StringBuilder FINAL_PATH = new StringBuilder();
    /**
     * 多个数据表名
     */
    private static final String[] TABLE_NAMES = new String[]{"tb_user", "t_area"};


    /**
     * tip:表名，多个英文逗号分割
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 代码生成器
     */
    public static void main(String[] args) {
        // 目标数据表名
        String[] tableName = scanner("表名，多个英文逗号分割").split(",");
        generateByTables(tableName);
    }

    private static void generateByTables(String... tableNames) {

        YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
        //获取yml里的数据库参数
        Properties properties = yamlMapFactoryBean.getObject();
        String url = properties.getProperty("spring.datasource.druid.url");
        String username = properties.getProperty("spring.datasource.druid.username");
        String password = properties.getProperty("spring.datasource.druid.password");

        // 获取顶层目录
        String projectPath = System.getProperty("user.dir");
        // 获取子项目目录
        String path = MyGenerator.class.getClassLoader().getResource("").getPath();
        String levelPath = path.substring(0, path.indexOf("target") - 1);
        if (!projectPath.equals(levelPath)) {
            FINAL_PATH.append(levelPath);
        } else {
            FINAL_PATH.append(projectPath);
        }


        FastAutoGenerator.create(url,username,password)
                //全局配置
                .globalConfig(builder -> {
                    builder.outputDir(FINAL_PATH + "/src/main/java") //输出路径
                            .author("black")  //作者名称
                            .disableOpenDir()  //禁止打开输出目录
                            .enableSwagger()  //开启swagger模式
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE);
                })
                //包配置
                .packageConfig(builder -> {
                    builder.moduleName(SERVICE_NAME)  //模块名称
                            .parent("com.black")  //基本路径
                            .entity("entity")  //实体类包
                            .service("service")  //业务接口类包
                            .serviceImpl("service.impl")  //业务实现类包
                            .mapper("mapper")  //持久层包
                            .controller("controller");  //控制器层
                })
                //策略配置
                //主要的表字段映射
                .strategyConfig(builder -> {
                    builder.addInclude(tableNames);  //表
//                            .addTablePrefix("sys_")  //表名映射到实体名称去掉前缀
                })
                //entity配置
                .strategyConfig(builder -> {
                    builder.entityBuilder()  //实体构造参数设置
                            .naming(NamingStrategy.underline_to_camel) //表名映射到实体策略，带下划线的转成 驼峰
                            .columnNaming(NamingStrategy.underline_to_camel) //列名映射到类型属性策略，带下划线的转成驼峰
                            .enableLombok()  //实体类使用lombok
                            .enableChainModel()  //链式结构
                            .superClass(BaseEntity.class);  //指定父类
                })
                //service配置
                .strategyConfig(builder -> {
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService");  //设置别名
                })
                //mapper配置
                .strategyConfig(builder -> {
                    builder.mapperBuilder()
                            .enableBaseResultMap()  //配置xml反射result
                            .enableBaseColumnList();  //配置基本sql
                })
                //controller配置
                .strategyConfig(builder -> {
                    builder.controllerBuilder()
                            .enableRestStyle();  //配置restController
                })
                //自定义模板配置
                .templateConfig(builder -> {
                    builder.entity("template/entity.java")
                            .mapper("template/mapper.java")
                            .xml("template/mapper.xml")
                            .service("template/service.java")
                            .serviceImpl("template/serviceImpl.java")
                            .controller("template/controller.java");
                })
                // 选择 freemarker 引擎，默认 Velocity
                .templateEngine(new MyFreemarkerTemplateEngine(FINAL_PATH))
                .execute();
    }

}
