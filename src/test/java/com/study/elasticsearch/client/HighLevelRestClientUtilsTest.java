package com.study.elasticsearch.client;

import java.io.IOException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HighLevelRestClientUtilsTest {
  private final Logger logger = LoggerFactory.getLogger(HighLevelRestClientUtilsTest.class);

  /**
   * 异步索引文档请求
   * @throws IOException
   */
  @Test
  public void indexRequestAsyn() throws IOException {
    String index = "ailpha-securitylog";
    String type = "logs";
    String documentId = "AWfiDETtLGD2xlTNXAf8";
    String documentSource = "{\"msg\":\"BackupAudit Fail:have other process doing!\\r\\n\",\"eventCount\":\"1\",\"srcPort\":\"0\",\"deviceReceiptTime\":\"2018-12-15 14:11:29\",\"deviceName\":\"WEB189\",\"deviceId\":\"1753\",\"severityType\":\"高\",\"timeStart\":\"2018-12-15 14:11:56\",\"destAddress\":\"127.0.0.1\",\"deviceCat\":\"/Audit/Database\",\"eventId\":\"5018051181878689793\",\"deviceAssetTypeId\":\"49\",\"mapperIdentifier\":\"c5a75795-ec53-4361-be6b-e5418be0c77f\",\"eventType\":\"1\",\"srcAddress\":\"192.168.58.105\",\"shacoTime\":\"20181215\",\"deviceAssetType\":\"应用类\",\"srcGeoCountry\":\"局域网\",\"productVendorName\":\"安恒\",\"catObject\":\"/Host/Application\",\"deviceSendProductName\":\"明御数据库审计与风险控制系统\",\"destGeoAddress\":\"没有单位\",\"catTechnique\":\"/UNKNOW\",\"name\":\"备份审计失败\",\"destGeoRegion\":\"安徽\",\"catOutcome\":\"FAIL\",\"deviceProtocol\":\"ftp\",\"rawEvent\":\"dbapp本主机~1~2018-12-15 14:11:29~192.168.58.105:0~127.0.0.1:0~系统告警~高~1103301509303341570~BackupAudit Fail:have other process doing!\\r\\n\",\"collectorReceiptTime\":\"2018-12-15 14:11:56\",\"destSecurityZone\":\"inner_886da035-c033-46b8-8ce8-b31e03db7ab2_1537173568979\",\"recCollectorId\":\"1\",\"deviceAddress\":\"202.109.166.181\",\"destPort\":\"0\",\"deviceProductType\":\"数据库审计\",\"customerId\":\"2\",\"@version\":\"1\",\"startTime\":\"2018-12-15 14:11:29\",\"srcGeoAddress\":\"局域网\",\"direction\":\"00\",\"destGeoCity\":\"池州\",\"timeEnd\":\"2018-12-15 14:11:56\",\"severity\":\"7\",\"srcAssetId\":\"1465\",\"deviceAssetSubType\":\"WEB服务器\",\"srcSecurityZone\":\"inner_886da035-c033-46b8-8ce8-b31e03db7ab2_1537173568979\",\"destGeoPoint\":\"0.0,0.0\",\"message\":\"备份审计失败：其他进程正在执行\",\"@timestamp\":\"2018-12-15T06:11:56.000Z\",\"srcGeoPoint\":\"0.0,0.0\",\"srcAssetName\":\"192.168.58.105\",\"catBehavior\":\"/Execute/Query\",\"text_name\":\"备份审计失败\",\"endTime\":\"2018-12-15 14:11:29\",\"deviceAssetSubTypeId\":\"34\",\"srcGeoRegion\":\"局域网\"}";
    IndexResponse indexResponse = HighLevelRestClientUtils
        .indexRequest(index, type, documentId, documentSource);
    //解析响应结果
    index = indexResponse.getIndex();
    type = indexResponse.getType();
    documentId = indexResponse.getId();
    long version = indexResponse.getVersion();
    if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {

    } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {

    }
    ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
    if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

    }
    if (shardInfo.getFailed() > 0) {
      for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
        String reason = failure.reason();
      }
    }
  }
}