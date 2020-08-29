package com.SelfBuildApp.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource("classpath:application.properties")
public class MvcConfigurer implements WebMvcConfigurer {

    @Value( "${app.fileUpload.folder}" )
    private String UPLOADED_FOLDER;

    @Value( "${app.fileUpload.pattern}" )
    private String RESOURCE_PATTERN;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        System.out.println("uploadsHandler");
        registry.addResourceHandler(RESOURCE_PATTERN).addResourceLocations("file:" + UPLOADED_FOLDER);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        System.out.println("qwerty");
//        registry.addMapping("/api/**").allowedOrigins("http://localhost:8080", "http://localhost:3000");
        registry.addMapping("/oauth/**").allowedOrigins("http://localhost:3000");
//        registry
//                .addMapping("/oauth/token")
//                .allowedOrigins("http://localhost:3000")
//                .allowedHeaders("*")
//                .allowedMethods("OPTIONS");

    }
}