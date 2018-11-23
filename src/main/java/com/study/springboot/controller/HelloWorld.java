package com.study.springboot.controller;

import com.study.springboot.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorld Demo Class
 *
 * @author Ricahrd.guo
 * @date 2018-11-20 16:37
 */
@RestController
public class HelloWorld {
  /**
   * 依赖注入使用构造器注入，使用final修饰
   */
  private final HelloWorldService helloWorldService;

  @Autowired
  public HelloWorld(HelloWorldService helloWorldService) {
    this.helloWorldService = helloWorldService;
  }

  @RequestMapping("/sayHello")
  public String sayHello(String name) {
    name = helloWorldService.checkName(name);
    return new String(new StringBuilder(name).append(",hello ! Welcome !"));
  }

}
