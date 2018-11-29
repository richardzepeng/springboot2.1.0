package com.study.springboot.properties;

import com.study.springboot.entity.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 用户常量类
 *
 * {@link ConfigurationProperties} 从配置文件注入属性，使用需配合{@link org.springframework.boot.context.properties.EnableConfigurationProperties}
 * {@link Validated} 数据格式校验注解，可使用Java注解，如：{@link Valid,NotEmpty,NotNull}
 *
 * @author Ricahrd.guo
 * @date 2018-11-27 15:14
 */
@ConfigurationProperties(prefix = "custom")
@Validated
public class UserProperties {

  @NotEmpty
  private String username;
  private String password;

  @NotEmpty
  private final List<Book> books = new ArrayList<>();
  @NotEmpty
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
