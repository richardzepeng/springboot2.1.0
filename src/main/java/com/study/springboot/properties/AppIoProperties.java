package com.study.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

/**
 * 数据大小的适配转换，更可读 如：10 and 10MB.
 * • B for bytes
 * • KB for kilobytes
 * • MB for megabytes
 * • GB for gigabytes
 * • TB for terabytes
 *
 * @author Ricahrd.guo
 * @date 2018-11-28 18:59
 */
@ConfigurationProperties("app.io")
public class AppIoProperties {

  @DataSizeUnit(DataUnit.MEGABYTES)
  private DataSize bufferSize = DataSize.ofMegabytes(2);
  private DataSize sizeThreshold = DataSize.ofBytes(512);

  public DataSize getBufferSize() {
    return this.bufferSize;
  }

  public void setBufferSize(DataSize bufferSize) {
    this.bufferSize = bufferSize;
  }

  public DataSize getSizeThreshold() {
    return this.sizeThreshold;
  }

  public void setSizeThreshold(DataSize sizeThreshold) {
    this.sizeThreshold = sizeThreshold;
  }
}
