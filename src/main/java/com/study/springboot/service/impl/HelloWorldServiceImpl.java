package com.study.springboot.service.impl;

import com.study.springboot.service.HelloWorldService;
import org.springframework.stereotype.Service;

/**
 * @author Ricahrd.guo
 * @date 2018-11-22 13:21
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldService {

  @Override
  public String checkName(String name) {
    return "richard.guo".equals(name)?name:"everybody";
  }
}
