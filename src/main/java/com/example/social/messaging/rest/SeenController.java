package com.example.social.messaging.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.social.messaging.model.Message;
import com.example.social.messaging.repository.MessageRepository;

@RestController
public class SeenController {
	
	@Autowired
	MessageRepository messageRepository;
	
	@PutMapping(value="/seen")
	public String seen(@RequestBody String id)
	{	
		Message message = messageRepository.findById(Integer.parseInt(id));
		message.setSeen(true);
		messageRepository.save(message);
		
		return "1";
	}
}