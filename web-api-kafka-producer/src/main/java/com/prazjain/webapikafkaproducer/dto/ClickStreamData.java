package com.prazjain.webapikafkaproducer.dto;

/**
 * A simple event data type that hold basic info about click event
 * @param article The news article that was clicked on / read
 * @param userAgent The user device used to read this article, the type of browser eg safari, chrome, edge etc
 */
public record ClickStreamData (String article, String userAgent) {
}
