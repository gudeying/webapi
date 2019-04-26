package cn.web.config;

import com.github.pagehelper.PageHelper;
import java.io.PrintStream;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfiguration
{
  @Bean
  public PageHelper pageHelper()
  {
    System.out.println("MyBatisConfiguration.pageHelper()");
    PageHelper pageHelper = new PageHelper();
    Properties p = new Properties();
    p.setProperty("offsetAsPageNum", "true");
    p.setProperty("rowBoundsWithCount", "true");
    p.setProperty("reasonable", "true");
    pageHelper.setProperties(p);
    return pageHelper;
  }
}
