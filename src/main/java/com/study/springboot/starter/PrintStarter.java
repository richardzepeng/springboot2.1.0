package com.study.springboot.starter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.apache.tomcat.util.security.Escape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 初始化类之打印类
 *
 * {@link Order} 标明实现ApplicationRunner或者CommandLineRunner的初始化类的启动顺序
 *
 * @author Ricahrd.guo
 * @date 2018-11-28 15:00
 */
@Order(value = 1)
@Component
public class PrintStarter implements ApplicationRunner {

  private final Logger logger = LoggerFactory.getLogger(PrintStarter.class);

  @Override
  public void run(ApplicationArguments args) {
    List<String> nonOptionArgs = args.getNonOptionArgs();
    logger.info("nonOptionArgs:{}",nonOptionArgs);
    Set<String> optionNames = args.getOptionNames();
    if (optionNames.size()==0){
      logger.info("optionArgs: no optionArgs !");
    }else {
      logger.info("optionArgs:");
      for (String optionName : optionNames) {
        logger.info("{}:{}",optionName,args.getOptionValues(optionName));
      }
    }
    String[] sourceArgs = args.getSourceArgs();
    logger.info("sourceArgs:{}", Arrays.toString(sourceArgs));
  }
}
