package com.example.script.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@EnableSwagger2
@Configuration
@Slf4j
public class DemoConfig {

    public static final String TEMP_FILE_PATH = System.getProperty("user.dir") + "/script-demo/datatmp";

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("script-demo").select()
                .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
//                .paths(regex("/(" + serviceInfo.getServiceName() + "/).*"))
                .build().apiInfo(new ApiInfo("script-demo Api", "script-demo相关Api", "v1", null, null, null, null)).useDefaultResponseMessages(false);
    }


    /** * 文件上传临时路径 */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(10240000);
        factory.setMaxRequestSize(10240000);
        String location = TEMP_FILE_PATH;
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            boolean mkdirs = tmpFile.mkdirs();
            log.info("创建临时文件夹结果：{}", mkdirs);
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}
