package com.unipd.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/topics")
    public String topics() {
        return "topics.html";
    }
}
