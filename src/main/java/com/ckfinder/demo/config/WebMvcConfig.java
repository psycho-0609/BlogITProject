package com.ckfinder.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path dirImageArticle = Paths.get("./imageArticle");
        String upload = dirImageArticle.toFile().getAbsolutePath();
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/image/**").addResourceLocations("file:"+upload+"/");
        registry.addResourceHandler("/ckfinder/**").addResourceLocations("classpath:/static/ckfinder/");
        Path imageDir = Paths.get("./video");
        String uploadPath = imageDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/video/**").addResourceLocations("file:"+uploadPath+"/");
        //   file:D:\\data\\file\\image\\

        super.addResourceHandlers(registry);
    }
}
