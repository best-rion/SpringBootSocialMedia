package com.example.social.Messaging.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.social.Messaging.dto.MessageNotification;
import com.example.social.Messaging.model.Message;
import com.example.social.Messaging.repository.MessageRepository;
import com.example.social.Social.model.User;
import com.example.social.Social.repository.UserRepository;

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
		// get all the messages were i am

		Set<String> peopleIHaveTalkedTo = messageRepository.findPeopleIHaveTalkedTo(principal.getName());

		Set<User> senders = userRepository.findAllInSet(peopleIHaveTalkedTo);

		List<MessageNotification> notifications = new ArrayList<>();

		for (User sender: senders)
		{
			MessageNotification notification = new MessageNotification();
			
			notification.sender = sender;
			notification.numberOfMessages = messageRepository.numOfUnseenMessageBySender(principal.getName(), sender.getUsername());
			notification.lastMessage = messageRepository.lastMessage(principal.getName(), sender.getUsername());

			
			notifications.add(notification);
		}

		model.addAttribute("notifications", notifications);
		model.addAttribute("principal", principal);
		return "messaging/home";
	}
	
	@GetMapping("/messaging/chat/{friend_username}")
	public String chat(Model model, @PathVariable String friend_username, Principal principal)
	{
		List<Message> unseenMessages = messageRepository.unseenMessageBySender(principal.getName(), friend_username);

		User friend = userRepository.findByUsername(friend_username);

		for (Message message: unseenMessages)
		{
			message.setSeen(1);
			messageRepository.save(message);
		}
		simpMessagingTemplate.convertAndSendToUser( friend_username , "/queue/update", principal.getName());

		// LIST OF ALL MESSAGES, MINE AND MY FRIEND'S
		List<Message> messages = messageRepository.findByPeople(principal.getName(), friend_username);

		System.out.println();
		
		model.addAttribute("messages", messages);
		model.addAttribute("principal", principal);
		model.addAttribute("friend", friend);
		return "messaging/chat";
	}
}