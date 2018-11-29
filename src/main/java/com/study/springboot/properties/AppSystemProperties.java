package com.study.springboot.properties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

/**
 * 对时间的适配转换，更可读 如：500, PT0.5S and 500ms.
 * • ns for nanoseconds
 * • us for microseconds
 * • ms for milliseconds
 * • s  for seconds
 * • m  for minutes
 * • h  for hours
 * • d  for days
 *
 * @author Ricahrd.guo
 * @date 2018-11-28 18:34
 */
@ConfigurationProperties("app.system")
public class AppSystemProperties {

  @DurationUnit(ChronoUnit.SECONDS)
  private Duration sessionTimeout = Duration.ofSeconds(30);
  private Duration readTimeout = Duration.ofMillis(1000);
  public Duration getSessionTimeout() {
    return this.sessionTimeout;
  }
  public void setSessionTimeout(Duration sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }
  public Duration getReadTimeout() {
    return this.readTimeout;
  }
  public void setReadTimeout(Duration readTimeout) {
    this.readTimeout = readTimeout;
  }
}
