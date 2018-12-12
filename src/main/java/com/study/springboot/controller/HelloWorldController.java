package com.study.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.springboot.service.HelloWorldService;
import javax.validation.constraints.NotNull;
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
public class HelloWorldController {
  /**
   * 依赖注入使用构造器注入，使用final修饰
   */
  private final HelloWorldService helloWorldService;

  @Autowired
  public HelloWorldController(HelloWorldService helloWorldService) {
    this.helloWorldService = helloWorldService;
  }

  @RequestMapping("/sayHello")
  public String sayHello(String name) {
    name = helloWorldService.checkName(name);
    return new String(new StringBuilder(name).append(",hello ! Welcome !"));
  }

  @RequestMapping("/test/inject")
  public void testInject() throws JsonProcessingException {
    helloWorldService.testInject();
  }

}
