package com.example.social.Messaging.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social.Messaging.repository.MessageRepository;

@RestController
public class ActiveController {
	
	@Autowired
	MessageRepository messageRepository;
	
	@PutMapping(value="/offline")
	public void offline()
	{	
		System.out.println("offlineeeeeeeeeeeeee\n\n\n\n\neeeeeeeeeeeeeeeeee");
	}
}