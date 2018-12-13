package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot启动类
 *
 * {@link SpringBootApplication} springBootApplication注解集成3大注解，配置注解，自动装配注解，包扫描注解（默认本注解所在类的包）
 *
 * @author Ricahrd.guo
 */
@SpringBootApplication
public class SpringbootApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootApplication.class, args);
  }
}
