package cn.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"cn.web.*"})
public class UeditorTestApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(UeditorTestApplication.class, args);
  }
}
