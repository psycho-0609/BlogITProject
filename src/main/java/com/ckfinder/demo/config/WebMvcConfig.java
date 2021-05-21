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
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/image/**").addResourceLocations("classpath:/static/uploadmedia/");
        registry.addResourceHandler("/ckfinder/**").addResourceLocations("classpath:/static/ckfinder/");
        Path imageDir = Paths.get("./video");
        String uploadPath = imageDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/video/**").addResourceLocations("file:"+uploadPath+"/");
        //   file:D:\\data\\file\\image\\

        super.addResourceHandlers(registry);
    }
}
