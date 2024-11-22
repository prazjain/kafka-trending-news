package com.prazjain.webapikafkaproducer.controllers;

import com.prazjain.webapikafkaproducer.dto.ClickStreamData;
import com.prazjain.webapikafkaproducer.producers.MyKafkaMessageProducer;
import jakarta.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * This is a basic controller.
 * It receives web request and generate kafka messages, to decouple web api call from event driven processing
 */
@RestController
public class BasicController {
    @Autowired
    private MyKafkaMessageProducer producer;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World");
    }

    /**
     * Generates 10K mock random news read events, and pushes to kafka
     */
    @GetMapping("/generate-events/{count}")
    public ResponseEntity<String> messageToTopic(@PathVariable int count) {
        //The link below are top level sections on bbc.com site
        List<String> newsArticles = Arrays.asList("/business", "/innovation",
            "/culture", "/travel", "/news", "/sport", "/future-planet");
        List<String> clientTypes = Arrays.asList("chrome", "edge", "safari");
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String article = newsArticles.get( random.nextInt(newsArticles.size()) );
            String clientType = clientTypes.get( random.nextInt(clientTypes.size()) );
            ClickStreamData data = new ClickStreamData(article, clientType);
            this.producer.sendMessage(data);
        }
        return ResponseEntity.ok(MessageFormat.format("Sent {0} events to kafka", count));
    }

    /**
     * Cleanup the input article path for use in our processing.
     * eg Convert //cricket-t20-world-cup-schedule.html/?var=test&sample=1230 to cricket-t20-world-cup-schedule
     * @param articlePath path to news article requested by user
     * @return
     */
    private String cleanupArticlePath(String articlePath) {
        //sample articlePath = //cricket-t20-world-cup-schedule.html/?var=test&sample=1230
        articlePath = StringUtils.stripStart(articlePath,"/");
        // here article path = cricket-t20-world-cup-schedule.html/?var=test&sample=1230
        articlePath = articlePath.substring(articlePath.indexOf('/'));
        // here article path = cricket-t20-world-cup-schedule.html
        articlePath = articlePath.substring(articlePath.indexOf("."));
        // now articlePath = cricket-t20-world-cup-schedule
        return articlePath;
    }

    @Autowired
    private HttpServletRequest request;

    /**
     * Simulates a read hit on news article path
     */
    @GetMapping("/news/{articlePath}")
    public ResponseEntity<String> messageToTopic(@PathVariable String articlePath) {
        //The link below are top level sections on bbc.com site
        articlePath = cleanupArticlePath(articlePath);
        String userAgent = request.getHeader("user-agent");
        ClickStreamData data = new ClickStreamData(articlePath, userAgent);
        this.producer.sendMessage(data);
        return ResponseEntity.ok(MessageFormat.format("Sent article read event to kafka : {0}", articlePath ));
    }
}
