package com.study.springboot.properties;

import com.study.springboot.entity.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用户常量类
 *
 * {@link ConfigurationProperties} 从配置文件注入属性，使用需配合{@link org.springframework.boot.context.properties.EnableConfigurationProperties}
 *
 * @author Ricahrd.guo
 * @date 2018-11-27 15:14
 */
@ConfigurationProperties(prefix = "custom")
public class UserProperties {

  private String username;
  private String password;
  private final List<Book> books = new ArrayList<>();
  private final Map<String, Book> bookIndex = new HashMap<>();

  public List<Book> getBooks() {
    return books;
  }

  public Map<String, Book> getBookIndex() {
    return bookIndex;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
