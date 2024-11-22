package com.prazjain.webapikafkaproducer.producers;

import static com.prazjain.webapikafkaproducer.MyKafkaProducerConfig.NEWS_ARTICLE_CLICK_STREAM_TOPIC;

import com.prazjain.webapikafkaproducer.dto.ClickStreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyKafkaMessageProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(ClickStreamData data) {
        this.kafkaTemplate.send(NEWS_ARTICLE_CLICK_STREAM_TOPIC, data.article(), data);
    }
}
