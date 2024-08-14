package com.unipd.mallet.controllers;

import com.unipd.mallet.models.Topic;
import com.unipd.mallet.services.MalletService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("api/mallet")
public class MalletController {

    private final MalletService malletService;

    @Autowired
    public MalletController(MalletService malletService) {
        this.malletService = malletService;
    }

    @PostMapping("/topics")
    public List<Topic> getTopics(@RequestBody List<String> documents,
                                     @RequestParam int numTopics,
                                     @RequestParam int numWords) throws Exception {
        return malletService.extractTopics(documents, numTopics, numWords);
    }
}
