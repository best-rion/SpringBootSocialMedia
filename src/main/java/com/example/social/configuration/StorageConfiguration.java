package com.example.social.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StorageConfiguration implements WebMvcConfigurer
{
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) 
  {
    registry.addResourceHandler("/upload/**").addResourceLocations("file://" + System.getProperty("user.dir") + "/src/main/upload/");
  }
}
