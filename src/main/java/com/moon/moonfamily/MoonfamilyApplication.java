package com.moon.moonfamily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class MoonfamilyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonfamilyApplication.class, args);
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

        @Override
        public void addCorsMappings(final CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods(ALLOWED_METHOD_NAMES.split(","));
        }
    }

}
