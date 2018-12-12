package com.study.spring.container;

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

/**
 * @author Ricahrd.guo
 * @date 2018-12-05 16:00
 */
public class ApplicationContextTest {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("");
    FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("");

//    groovy 配置
//    ApplicationContext groovyApplicationContext = new GenericGroovyApplicationContext("services.groovy", "daos.groovy");

    //阅读器加载
    GenericApplicationContext context = new GenericApplicationContext();
    new XmlBeanDefinitionReader(context).loadBeanDefinitions("services.xml", "daos.xml");
    context.refresh();

//    GenericApplicationContext context2 = new GenericApplicationContext();
//    new GroovyBeanDefinitionReader(context2).loadBeanDefinitions("services.groovy", "daos.groovy");
//    context2.refresh();
  }

}
