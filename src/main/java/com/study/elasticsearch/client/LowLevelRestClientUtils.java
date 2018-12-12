package com.study.elasticsearch.client;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RequestOptions.Builder;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClient.FailureListener;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 低等级的java rest 客户端
 *
 * @author Ricahrd.guo
 * @date 2018-12-11 16:27
 */
public class LowLevelRestClientUtils {

  private final Logger logger = LoggerFactory.getLogger(LowLevelRestClientUtils.class);
  private static final RestClient CLIENT;
  private static final RequestOptions COMMON_OPTIONS;

  static {
    CLIENT =initClient();
    Builder builder = RequestOptions.DEFAULT.toBuilder();
    //用于设置通用认证或者代理
    //    builder.addHeader("Authorization","");
    builder.setHttpAsyncResponseConsumerFactory(
        new HeapBufferedResponseConsumerFactory(200 * 1024 * 1024));
    COMMON_OPTIONS=builder.build();
  }

  /**
   * 初始化客户端
   */
  public static RestClient initClient() {
    //创造一个客户端实例
    RestClientBuilder restClientBuilder = RestClient
        .builder(new HttpHost("bd225", 9200, HttpHost.DEFAULT_SCHEME_NAME),
            new HttpHost("bd226", 9200, HttpHost.DEFAULT_SCHEME_NAME));
    //配置所有请求的头部信息
    restClientBuilder.setDefaultHeaders(new Header[]{new BasicHeader("header", "value")});
    //配置同一请求失败重试时最大请求间隔(默认30秒）
    restClientBuilder.setMaxRetryTimeoutMillis(RestClientBuilder.DEFAULT_MAX_RETRY_TIMEOUT_MILLIS);
    //配置节点失败监听器
    restClientBuilder.setFailureListener(new CustomeFailureListener());
    //配置请求节点选择器,忽略专用主节点dedicated master nodes
    restClientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
    //修改默认的请求配置
    restClientBuilder.setRequestConfigCallback(
        requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(10000));
    //修改默认http客户端配置
    restClientBuilder.setHttpClientConfigCallback(
        httpClientBuilder -> httpClientBuilder.setMaxConnTotal(20));
    //配置请求前缀,用于代理配置,一般不会用
//    restClientBuilder.setPathPrefix()

    return restClientBuilder.build();
  }

  /**
   * 关闭客户端，释放资源以及底层的http客户端和线程
   * @param client es请求客户端
   * @throws IOException 当关闭失败时,抛出io异常
   */
  public static void close(RestClient client) throws IOException {
    client.close();
  }

  /**
   * 同步请求
   * @return response
   * @throws IOException 出现异常
   */
  public static Response synchronousRequest() throws IOException {
    Request request = new Request("GET","/");
    //设置url参数
    request.addParameter("pretty","true");
    return CLIENT.performRequest(request);
  }

  /**
   * 异步步请求
   * @throws IOException 出现异常
   */
  public static void asynchronousRequest() throws IOException {
    Request request = new Request("POST","/");
    //设置请求体参数
    request.setEntity(new NStringEntity("{\"json\":\"text\"}", ContentType.APPLICATION_JSON));
    //与上面的代码等同
    //request.setJsonEntity("{\"json\":\"text\"}");

    //配置通用请求选项
    // 还可以自定义每个请求的特殊通用,如下
    //RequestOptions.Builder options = COMMON_OPTIONS.toBuilder();
    //options.addHeader("cats", "knock things off of other things");
    //request.setOptions(options);
    request.setOptions(COMMON_OPTIONS);

    CLIENT.performRequestAsync(request, new ResponseListener() {
      @Override
      public void onSuccess(Response response) {
        //成功后
      }

      @Override
      public void onFailure(Exception exception) {
        //失败后
      }
    });
  }

  /**
   * 自定义节点失败监听器
   */
  public static class CustomeFailureListener extends FailureListener {

    private final Logger logger = LoggerFactory.getLogger(CustomeFailureListener.class);

    @Override
    public void onFailure(Node node) {
      HttpHost host = node.getHost();
      logger.error("请求{}失败,节点{}出现异常", host.toHostString(), node.getName());
    }

  }

}





