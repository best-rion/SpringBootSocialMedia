package com.example.social.Messaging.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.social.Messaging.dto.MessageNotification;
import com.example.social.Messaging.model.Message;
import com.example.social.Messaging.repository.MessageRepository;
import com.example.social.model.User;
import com.example.social.repository.UserRepository;

@Controller
public class ChatHomeController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MessageRepository messageRepository;

	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@GetMapping("/messaging")
	public String home(Model model, Authentication auth)
	{
		List<User> users = userRepository.findAllExcept(auth.getName());
		
		List<MessageNotification> notifications = new ArrayList<>();
		
		for (User user: users)
		{
			MessageNotification notification = new MessageNotification();
			
			notification.sender = user.getUsername();
			notification.numberOfMessages = messageRepository.numOfUnseenMessageBySender(auth.getName(), user.getUsername());
			
			notifications.add(notification);
		}
		
		model.addAttribute("notifications", notifications);
		return "messaging/home";
	}
	
	@GetMapping("/messaging/chat/{friend}")
	public String chat(Model model, @PathVariable String friend, Authentication auth)
	{
		List<Message> unseenMessages = messageRepository.unseenMessageBySender(auth.getName(), friend);
		
		for (Message message: unseenMessages)
		{
			message.setSeen(true);
			messageRepository.save(message);
		}
		

		simpMessagingTemplate.convertAndSendToUser( friend , "/queue/update", auth.getName());
		
		
		// LIST OF ALL MESSAGES, MINE AND MY FRIEND'S
		List<Message> messages = messageRepository.findByPeople(auth.getName(), friend);
		
		model.addAttribute("messages", messages);
		model.addAttribute("principal", auth.getName());
		model.addAttribute("friend", friend);
		return "messaging/chat";
	}
}