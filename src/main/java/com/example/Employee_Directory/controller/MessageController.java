package com.example.Employee_Directory.controller;

import com.example.Employee_Directory.dto.MessageRequest;
import com.example.Employee_Directory.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private MessageProducer messageProducer;

    @Autowired
    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping
    public String sendMessage(@RequestBody MessageRequest request) {
        messageProducer.sendMessage(request.getMessage());
        return "Message sent: " + request.getMessage();
    }

    @PostMapping("/send")
    public String sendWithKey(@RequestParam String key, @RequestParam String message) {
        messageProducer.sendMessageWithKey(key, message);
        return "Message sent with key!";
    }
}
