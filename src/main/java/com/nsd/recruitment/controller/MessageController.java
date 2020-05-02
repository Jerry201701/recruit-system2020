package com.nsd.recruitment.controller;

import com.nsd.recruitment.common.HttpResult;
import com.nsd.recruitment.domain.ConversationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mongo")
public class MessageController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @PostMapping(value = "/add")
    public HttpResult addToMongo(@RequestBody ConversationMessage conversationMessage){
        mongoTemplate.insert(conversationMessage);

        return HttpResult.ok();
    }
}
