package com.study.springboot.controller;

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

  @RequestMapping("/sayHello")
  public String sayHello(String name) {
    return new String(new StringBuilder(name).append(",hello !"));
  }

}
