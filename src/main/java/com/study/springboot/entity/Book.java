package com.study.springboot.entity;

/**
 * @author Ricahrd.guo
 * @date 2018-11-27 17:55
 */
public class Book {

  private String name;
  private String author;
  private Double price;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double pirce) {
    this.price = pirce;
  }

  @Override
  public String toString() {
    return "Book{" +
        "name='" + name + '\'' +
        ", author='" + author + '\'' +
        ", price=" + price +
        '}';
  }
}
