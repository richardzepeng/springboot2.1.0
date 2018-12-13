package com.study.elasticsearch.client;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LowLevelRestClientUtilsTest {

  private final Logger logger = LoggerFactory.getLogger(LowLevelRestClientUtilsTest.class);

  /**
   * 同步请求测试
   *
   * @throws IOException io异常
   */
  @Test
  public void syncRequest() throws IOException {
    Response response = LowLevelRestClientUtils
        .synchronousRequest("GET", "/ailpha-securitylog-20181212/logs/AWeiDETtLGD2xlTNXAf8");
    //ignore参数测试
    //Response response = LowLevelRestClientUtils.synchronousRequest("GET", "/ailpha-securitylog-20181212/logs/");
    RequestLine requestLine = response.getRequestLine();
    HttpHost host = response.getHost();
    int statusCode = response.getStatusLine().getStatusCode();
    Header[] headers = response.getHeaders();
    String responseBody = EntityUtils.toString(response.getEntity());
    logger.trace("syncRequest请求返回：{}", responseBody);
    int processors = Runtime.getRuntime().availableProcessors();
    logger.trace("可用处理器个数为:{}", processors);
    LowLevelRestClientUtils.close();
    logger.trace("es客户端以及嗅探已正确关闭");
  }

}