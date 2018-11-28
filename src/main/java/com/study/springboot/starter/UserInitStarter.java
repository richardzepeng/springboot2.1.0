package com.study.springboot.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 用户初始化类
 *
 * {@link Ordered} 实现该接口，确定初始化顺序
 *
 * @author Ricahrd.guo
 * @date 2018-11-28 15:15
 */
@Component
public class UserInitStarter implements CommandLineRunner, Ordered {

  private final Logger logger = LoggerFactory.getLogger(UserInitStarter.class);

  @Override
  public void run(String... args) throws Exception {
    logger.info("user init ...");
  }

  @Override
  public int getOrder() {
    return 2;
  }
}
