package com.study.springboot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.springboot.properties.UserProperties;
import com.study.springboot.service.HelloWorldService;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * {@link EnableConfigurationProperties} 该类启用配置文件属性注入
 *
 * @author Ricahrd.guo
 * @date 2018-11-22 13:21
 */
@Service
@EnableConfigurationProperties(UserProperties.class)
public class HelloWorldServiceImpl implements HelloWorldService {

  private final Logger logger = LoggerFactory.getLogger(HelloWorldService.class);

  private final String username;
  private final String password;
  private final List books;
  private final Map bookIndex;

  @Value("${custom.value}")
  private String value;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  public HelloWorldServiceImpl(UserProperties userProperties) {
    this.username = userProperties.getUsername();
    password = userProperties.getPassword();
    books = userProperties.getBooks();
    bookIndex = userProperties.getBookIndex();
  }

  @Override
  public String checkName(String name) {
    return username.equals(name) ? name : "everybody";
  }

  @Override
  public void testInject() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> getenv = System.getenv();
    Properties properties = System.getProperties();
    logger.info("evnpros:{}",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getenv));
    logger.info("syspros:{}",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(properties));
    logger.info("username:{}", username);
    logger.info("password:{}", password);
    logger.info("books:{}", books);
    logger.info("bookIndex:{}", bookIndex);
    logger.info("value:{}",value);
  }
}
