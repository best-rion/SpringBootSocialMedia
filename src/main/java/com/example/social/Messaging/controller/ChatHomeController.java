package com.example.social.Messaging.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.social.Messaging.dto.MessageNotification;
import com.example.social.Messaging.model.Message;
import com.example.social.Messaging.service.MessageService;
import com.example.social.Social.model.User;
import com.example.social.Social.service.UserService;

@Controller
public class ChatHomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;

	
	@GetMapping("/messaging")
	public String home(Model model, Principal principal)
	{
		List<MessageNotification> notifications = messageService.getNotifications(principal);

		model.addAttribute("notifications", notifications);
		model.addAttribute("principal", principal);
		return "messaging/home";
	}
	
	
	@GetMapping("/messaging/chat/{friend_username}")
	public String chat(Model model, @PathVariable String friend_username, Principal principal)
	{
		messageService.setMessageToSeen(friend_username, principal);

		List<Message> messages = messageService.getChatHistory(friend_username, principal);

		User friend = userService.getByUsername(friend_username);
		
		model.addAttribute("messages", messages);
		model.addAttribute("principal", principal);
		model.addAttribute("friend", friend);
		return "messaging/chat";
	}
}