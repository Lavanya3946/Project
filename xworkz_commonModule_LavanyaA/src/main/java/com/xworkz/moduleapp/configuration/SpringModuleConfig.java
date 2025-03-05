package com.xworkz.moduleapp.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.xworkz.moduleapp")
@Import({SecurityConfig.class})
public class SpringModuleConfig {
}
