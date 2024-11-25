package com.example.social.Messaging.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
	public String home(Model model, Principal principal)
	{
		List<User> users = userRepository.findAllExcept(principal.getName());
		
		List<MessageNotification> notifications = new ArrayList<>();
		
		for (User user: users)
		{
			MessageNotification notification = new MessageNotification();
			
			notification.sender = user.getUsername();
			notification.numberOfMessages = messageRepository.numOfUnseenMessageBySender(principal.getName(), user.getUsername());
			
			notifications.add(notification);
		}
		
		model.addAttribute("notifications", notifications);
		model.addAttribute("principal", principal);
		return "messaging/home";
	}
	
	@GetMapping("/messaging/chat/{friend}")
	public String chat(Model model, @PathVariable String friend, Principal principal)
	{
		List<Message> unseenMessages = messageRepository.unseenMessageBySender(principal.getName(), friend);
		
		for (Message message: unseenMessages)
		{
			message.setSeen(true);
			messageRepository.save(message);
		}
		

		simpMessagingTemplate.convertAndSendToUser( friend , "/queue/update", principal.getName());
		
		
		// LIST OF ALL MESSAGES, MINE AND MY FRIEND'S
		List<Message> messages = messageRepository.findByPeople(principal.getName(), friend);
		
		model.addAttribute("messages", messages);
		model.addAttribute("principal", principal.getName());
		model.addAttribute("friend", friend);
		return "messaging/chat";
	}
}