package com.study.springboot.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void sayHello() throws Exception {
    MockHttpServletRequestBuilder getRequestbuilder = MockMvcRequestBuilders
        .get("/sayHello");
    getRequestbuilder.param("name", "richard.guo");
    MvcResult mvcResult = mvc.perform(getRequestbuilder).andReturn();
    MockHttpServletResponse response = mvcResult.getResponse();
    String contentAsString = response.getContentAsString();
    System.err.println(contentAsString);
    mvc.perform(getRequestbuilder).andDo(MockMvcResultHandlers.log());
  }

  @Test
  public void testInject() throws Exception {
    MockHttpServletRequestBuilder getRequestbuilder = MockMvcRequestBuilders
        .get("/test/inject");
   mvc.perform(getRequestbuilder).andReturn();
  }
}