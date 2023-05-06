package com.black.utils;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author black
 * @date 2023/4/19 14:52
 */
@Configuration
@EnableSwagger2  //swagger注解
public class SwaggerConfig {

    /*引入Knife4j提供的扩展类*/
    private OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public SwaggerConfig(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    private final static String groupName = "webApi";//组群名称

    private final static String headerName = "token";//需要swagger每次调接口前携带的头信息的key
    //private final static String headerName2 = "test";//如果要多个请求头信息，自行解放注释

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(webApiInfo())
                //创建选择器控制哪些接口进入文档
                .select()
                //指定标注ApiOperation注解的接口加入文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .securitySchemes(securitySchemes())  //配置安全方案
                .securityContexts(securityContexts()) //配置安全方案的上下文
                .extensions(openApiExtensionResolver.buildExtensions(groupName)); //赋予插件体系
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        //配置header头1
        ApiKey token_access = new ApiKey(headerName, headerName, "header");
        apiKeyList.add(token_access);
        //配置header头2
        //ApiKey token_access2 = new ApiKey( headerName2, headerName2, "header");
        //apiKeyList.add(token_access2);
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        //为每个api添加请求头
        securityReferenceList.add(new SecurityReference(headerName, scopes()));
        //以此类推
        //securityReferenceList.add(new SecurityReference(headerName2, scopes()));
        securityContextList.add(SecurityContext
                .builder()
                .securityReferences(securityReferenceList)
                .forPaths(PathSelectors.any())
                .build()
        );
        return securityContextList;
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessAnything")};//作用域为全局
    }

    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                // 文档标题
                .title("标题：用户管理系统接口文档")
                // 文档描述
                .description("描述：本文档描述了用户管理系统的接口定义")
                // 版本
                .version("1.0")
                // 联系人信息
                .contact(new Contact("black", "", "879059781@qq.com"))
                // 版权
                .license("black")
                // 版权地址
//                .licenseUrl("")
                .build();
    }
}
