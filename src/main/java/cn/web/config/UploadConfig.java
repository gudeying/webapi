package cn.web.config;

import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig
{
  @Value("${web.upload-path}")
  private String path;
  
  @Bean
  MultipartConfigElement multipartConfigElement()
  {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    factory.setLocation(this.path);
    return factory.createMultipartConfig();
  }
}
