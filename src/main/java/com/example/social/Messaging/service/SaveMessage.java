package com.example.social.Messaging.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social.Messaging.model.Message;
import com.example.social.Messaging.repository.MessageRepository;


@Service
public class SaveMessage
{
	@Autowired
	MessageRepository messageRepository;
	
	public Message save(String sender, String receiver, String content)
	{	System.out.println("f");
		Message newMessage = new Message();
		
		newMessage.setSender(sender);
		newMessage.setReceiver(receiver);;
		newMessage.setContent(content);
		newMessage.setTime( new Date() );
		newMessage.setSeen(false);
		
		messageRepository.save(newMessage);
		return newMessage;
	}
}