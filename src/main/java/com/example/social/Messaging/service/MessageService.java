package com.example.social.Messaging.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.social.Messaging.dto.MessageNotification;
import com.example.social.Messaging.model.Message;
import com.example.social.Messaging.repository.MessageRepository;
import com.example.social.Social.model.User;
import com.example.social.Social.repository.UserRepository;


@Service
public class MessageService
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	
	public Message save(String sender, String receiver, String content)
	{	System.out.println("f");
		Message newMessage = new Message();
		
		Date date = new Date();
		
		newMessage.setSender(sender);
		newMessage.setReceiver(receiver);;
		newMessage.setContent(content);
		newMessage.setTime( date );
		newMessage.setSeen(0);
		
		messageRepository.save(newMessage);
		return newMessage;
	}
	
	
	public List<MessageNotification> getNotifications(Principal principal)
	{
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
		
		return notifications;
	}
	
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	public void setMessageToSeen(String friend_username, Principal principal)
	{
		List<Message> unseenMessages = messageRepository.unseenMessageBySender(principal.getName(), friend_username);

		for (Message message: unseenMessages)
		{
			message.setSeen(1);
			messageRepository.save(message);
		}
		simpMessagingTemplate.convertAndSendToUser( friend_username , "/queue/update", principal.getName());
	}
	
	
	public List<Message> getChatHistory(String friend_username, Principal principal)
	{
		return messageRepository.findByPeople(principal.getName(), friend_username);
	}
}



