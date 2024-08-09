package com.unipd.frontend.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FrontendController {

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @GetMapping("/monitor")
    public String monitor() {
        return "monitor.html";
    }

    @GetMapping("/{tag}/topics")
    public String topics(@PathVariable("tag") String tag) {
        System.out.println("Tag ricevuto: " + tag);
        return "topics.html";
    }

    @GetMapping("/api/{tag}")
    @ResponseBody
    public Map<String, String> getTag(@PathVariable("tag") String tag) {
        System.out.println("Ho restituito il tag: " + tag);
        return Map.of("tag", tag);
    }
}
