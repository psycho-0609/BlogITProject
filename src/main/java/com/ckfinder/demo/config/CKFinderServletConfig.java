package com.ckfinder.demo.config;

import com.ckfinder.connector.ConnectorServlet;
import com.ckfinder.demo.user.CustomUserDetail;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class CKFinderServletConfig {


    private String baseDir;
    @Value("${ckeditor.access.image.url}")
    private String baseURL;



    @Bean
    public ServletRegistrationBean connectCKFinder(){

        baseDir = "./imageArticle";
        ServletRegistrationBean registrationBean=new ServletRegistrationBean(new ConnectorServlet(),"/ckfinder/core/connector/java/connector.java");
        registrationBean.addInitParameter("XMLConfig","classpath:/static/ckfinder.xml");
        registrationBean.addInitParameter("debug","false");
        registrationBean.addInitParameter("configuration","com.ckfinder.demo.config.CKFinderConfig");
        //ckfinder.xml
        registrationBean.addInitParameter("baseDir",baseDir);
        registrationBean.addInitParameter("baseURL",baseURL);
        return registrationBean;
    }

}