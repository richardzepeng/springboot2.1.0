package com.study.springboot.service.impl;

import com.study.springboot.properties.UserProperties;
import com.study.springboot.service.HelloWorldService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  public void testInject() {
    logger.info("username:{}", username);
    logger.info("password:{}", password);
    logger.info("books:{}", books);
    logger.info("bookIndex:{}", bookIndex);
  }
}
