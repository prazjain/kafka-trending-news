package com.prazjain.webapikafkaproducer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyKafkaProducerConfig {

    public static final String NEWS_ARTICLE_CLICK_STREAM_TOPIC = "news-article-click-stream-topic";
    public static final int NEWS_ARTICLE_CLICK_STREAM_PARTITION_COUNT = 3;

    /**
     * Create topic for new article click stream
     * @return
     */
    @Bean
    public NewTopic createTopic(){
        return new NewTopic(NEWS_ARTICLE_CLICK_STREAM_TOPIC, NEWS_ARTICLE_CLICK_STREAM_PARTITION_COUNT, (short) 1);
    }
}
