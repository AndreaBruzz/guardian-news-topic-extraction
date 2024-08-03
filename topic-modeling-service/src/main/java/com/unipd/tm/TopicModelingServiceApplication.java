package com.unipd.topicmodeling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TopicModelingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicModelingServiceApplication.class, args);
    }
}

@RestController
class TopicModelingController {

    @GetMapping("/topic-modeling")
    public String performTopicModeling(
            @RequestParam String query,
            @RequestParam int numTopics,
            @RequestParam int numWords,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        // Implement topic modeling logic using Mallet
        return "Topic modeling result in JSON format";
    }
}
