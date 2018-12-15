package com.study.elasticsearch.client;

import java.io.IOException;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.util.StringUtils;

/**
 * 高等级的java rest 客户端，同一大版本并且向后兼容es服务端版本（支持后续的未发布的同一大版本的es），构筑于低级api之上。自动整理request和处理response相应.
 * <p>
 * 和Java client API 的 TransportClient的请求参数和返回一样。 官方计划在Elasticsearch 7.0中不支持TransportClient，并在8.0中完全删除它
 * <p>
 * high level client执行的是HTTP请求，而Java client执行的是序列化的Java请求
 * <p>
 * 所有请求方法都支持同步和异步，异步Async方法暂时不做展示
 *
 * @author Ricahrd.guo
 * @date 2018-12-13 15:00
 * @since java1.8
 */
public class HighLevelRestClientUtils {

  private static RestHighLevelClient client;

  static {
    init();
  }

  /**
   * 初始化客户端
   */
  private static void init() {
    //高级客户端基于低级客户端开发，先配置好低级客户端
    RestClientBuilder restClientBuilder = RestClient
        .builder(new HttpHost("node5", 9200, HttpHost.DEFAULT_SCHEME_NAME),
            new HttpHost("node6", 9200, HttpHost.DEFAULT_SCHEME_NAME))
        .setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);

    client = new RestHighLevelClient(restClientBuilder);
  }

  /**
   * 关闭es客户端连接
   *
   * @throws IOException io异常
   */
  public static void close() throws IOException {
    client.close();
  }

  /**
   * 同步索引文档
   *
   * @param index index
   * @param type type
   * @param documentId id
   * @param documentSource 文档内容，map格式
   * @throws IOException io异常
   */
  public static IndexResponse indexRequest(String index, String type, String documentId,
      String documentSource) throws IOException {
    IndexRequest request;
    if (StringUtils.isEmpty(documentId)){
      request = new IndexRequest(index, type);
    }else {
      request = new IndexRequest(index, type, documentId);
    }
    request.source(documentSource,XContentType.JSON);
    //改索引请求，只能设为index或者create,其中index如果存在相同的索引会替换，create则会报错
    request.opType(OpType.INDEX);
    return client.index(request, RequestOptions.DEFAULT);
  }

  /**
   * 异步索引文档
   *
   * @param index index
   * @param type type
   * @param documentId id
   * @param documentSource 文档内容，jsonString格式
   * @throws IOException io异常
   */
  public static void indexRequest(String index, String type, String documentId,
      Map<String, Object> documentSource) throws IOException {
    IndexRequest request;
    if (StringUtils.isEmpty(documentId)){
      request = new IndexRequest(index, type);
    }else {
      request = new IndexRequest(index, type, documentId);
    }
    request.source(documentSource);
    request.opType(OpType.CREATE);
    client.indexAsync(request,RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
      @Override
      public void onResponse(IndexResponse indexResponse) {

      }

      @Override
      public void onFailure(Exception e) {

      }
    });
  }

}
