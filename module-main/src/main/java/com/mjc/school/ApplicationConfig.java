package com.mjc.school;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("com.mjc.school.controller")
public class ApplicationConfig {

    // MenuHelper is created using @Bean annotation by the following reasons:
    // 1. Just to show that beans can be created
    //    not only by @Component, @Service and other stereotype annotations
    //    but also with help of @Bean annotations in configuration classes (marked as @Configuration).
    // 2. To show that bean can be created and initialized programmatically.

}
