package com.study.elasticsearch.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
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
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认使用http，用户自己编写request请求，自己解析response相应
 * <p>
 * 低等级的java rest 客户端，兼容所有的es版本，
 * <p>
 * sniffer 嗅探兼容2.x及以上版本，默认每5分钟，从集群中获取当前节点的列表，并更新它们
 *
 * @author Ricahrd.guo
 * @date 2018-12-11 16:27
 * @since java1.7
 */
public class LowLevelRestClientUtils {

  private static RestClient client;
  private static Sniffer sniffer;
  private static final RequestOptions COMMON_OPTIONS;

  static {
    init();
    Builder builder = RequestOptions.DEFAULT.toBuilder();
    //用于设置通用认证或者代理
    //    builder.addHeader("Authorization","");
    builder.setHttpAsyncResponseConsumerFactory(
        new HeapBufferedResponseConsumerFactory(200 * 1024 * 1024));
    COMMON_OPTIONS = builder.build();
  }

  /**
   * 初始化客户端
   */
  private static void init() {
    //创造一个客户端实例
    RestClientBuilder restClientBuilder = RestClient
        .builder(new HttpHost("node5", 9200, HttpHost.DEFAULT_SCHEME_NAME),
            new HttpHost("node6", 9200, HttpHost.DEFAULT_SCHEME_NAME));
    //配置所有请求的头部信息
    restClientBuilder.setDefaultHeaders(new Header[]{new BasicHeader("header", "value")});
    //配置同一请求失败重试时最大请求间隔(默认30秒）
    restClientBuilder.setMaxRetryTimeoutMillis(RestClientBuilder.DEFAULT_MAX_RETRY_TIMEOUT_MILLIS);
    //配置自定义的节点失败监听器
//    restClientBuilder.setFailureListener(new CustomeFailureListener());
    //也可以配置一个嗅探失败监听器
    SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
    restClientBuilder.setFailureListener(sniffOnFailureListener);

    //配置请求节点选择器,忽略专用主节点dedicated master nodes（可以自定义实现）
    restClientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
    //修改默认的请求配置
    restClientBuilder.setRequestConfigCallback(
        requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(10000));

    //修改默认http客户端配置(设置工作线程个数,调度线程默认1个,工作线程和处理器个数等同)
    //配置身份验证信息,并禁用抢占式服务，即优先使用无认证信息请求，如果失败返回401，然后再带有认证信息请求
    //还可以设置ssl传输加密（见文档Encrypted communication，不显示设置，会使用Java自带的默认加密
    // https://docs.oracle.com/javase/7/docs/technotes/guides/security/jsse/JSSERefGuide.html#CustomizingStores）
    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider
        .setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "2wsxVFR_"));
    restClientBuilder.setHttpClientConfigCallback(
        httpClientBuilder -> httpClientBuilder
            .setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build())
            .setDefaultCredentialsProvider(credentialsProvider).disableAuthCaching());

    //配置请求前缀,用于代理配置,一般不会用
    //restClientBuilder.setPathPrefix()

    client = restClientBuilder.build();

    //配置嗅探
    //普通正常的间隔5分钟，发现失败后30秒（默认1分钟）添加另一个探测，检测是否恢复正常（如果没有设置失败监听则该属性无效）
    sniffer = Sniffer.builder(client).setSniffIntervalMillis(
        Math.toIntExact(TimeUnit.MINUTES.toMillis(5)))
        .setSniffAfterFailureDelayMillis(Math.toIntExact(TimeUnit.SECONDS.toMillis(30))).build();
    //如果使用https协议访问，使用嗅探需要手动修改
    /*Sniffer.builder(CLIENT).setNodesSniffer(new ElasticsearchNodesSniffer(CLIENT,
        ElasticsearchNodesSniffer.DEFAULT_SNIFF_REQUEST_TIMEOUT,
        Scheme.HTTPS));*/
    sniffOnFailureListener.setSniffer(sniffer);
  }

  /**
   * 关闭客户端，释放资源以及底层的http客户端和线程
   *
   * @throws IOException 当关闭失败时,抛出io异常
   */
  public static void close() throws IOException {
    sniffer.close();
    client.close();
  }

  /**
   * 同步请求
   *
   * @return response
   * @throws IOException 出现异常
   */
  public static Response synchronousRequest(String method, String endpoint) throws IOException {
    Request request = new Request(method, endpoint);
    //设置url参数
//    request.addParameter("pretty", "true");
    //ignore参数可以对返回码进行不报错处理
    request.addParameter("ignore", "400");
    return client.performRequest(request);
  }

  /**
   * 异步步请求
   *
   * @throws IOException 出现异常
   */
  public static void asynchronousRequest() throws IOException {
    Request request = new Request("POST", "/");
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

    client.performRequestAsync(request, new ResponseListener() {
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





