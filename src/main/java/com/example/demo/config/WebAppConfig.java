// package com.example.demo.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

// @Configuration
// @EnableWebMvc
// public class WebAppConfig extends WebMvcConfigurationSupport{
//     @Override
//     public void addViewControllers(ViewControllerRegistry registry) {
//     registry.addRedirectViewController("/configuration/ui", "/swagger-resources/configuration/ui");
//     }

//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//         registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//     }
// }

