package com.example.chapter4;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import lombok.Setter;

/**
 * Hello world!
 *
 */
public class FirstAppProducer {

  public static final String TOPIC_NAME = "first-app";

  @Setter
  private Producer<Integer, String> producer;

  public void send() {

    int key;
    String value;
    for (int i = 1; i <= 5; i++) {
      key = i;
      value = String.valueOf(i);

      // 3. 送信するMessageを生成
      ProducerRecord<Integer, String> record = new ProducerRecord<>(TOPIC_NAME, key, value);

      // 4. Messageを送信し、Ackを受け取ったときに行う処理(Callback)を登録する
      producer.send(record, new Callback() {
        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
          if (metadata != null) {
            // 送信に成功した場合の処理
            String infoString = String.format("Success partition:%d, offset:%d",
                metadata.partition(), metadata.offset());
            System.out.println(infoString);
          } else {
            // 送信に失敗した場合の処理
            String infoString = String.format("Failed:%s", e.getMessage());
            System.err.println(infoString);
            e.printStackTrace();
          }
        }
      });
    }

    // 5. KafkaProducerをCloseして終了
    producer.close();
  }
}
