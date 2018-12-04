package com.study.springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Ricahrd.guo
 * @date 2018-11-22 13:20
 */
public interface HelloWorldService {

  /**
   * check name is valid or not
   * @param name name
   * @return check result
   */
  String checkName(String name);

  /**
   * test configrution inject
   */
  void testInject() throws JsonProcessingException;

}
