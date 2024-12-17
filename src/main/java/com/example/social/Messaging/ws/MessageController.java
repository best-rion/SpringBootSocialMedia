package com.example.social.Messaging.ws;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.social.Messaging.model.Message;
import com.example.social.Messaging.repository.MessageRepository;
import com.example.social.Messaging.service.MessageService;


@Controller
public class MessageController
{
	@Autowired
	MessageService messageService;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	
	@MessageMapping("/send-to/{friend}")
	public void messaging(
			@Payload String receivedMessage,
			@DestinationVariable String friend,
			final Principal principal
			) throws Exception
	{
		Message message = messageService.save( principal.getName(), friend, receivedMessage );
	
		simpMessagingTemplate.convertAndSendToUser( friend , "/queue/private", message);

		Thread.sleep(100); // Wait 0.1sec to update seen status in database. See "SeenController" class, how it updates.
		
		message = messageRepository.findById(message.getId()); // Send the message back to me with correct seen status
		
		simpMessagingTemplate.convertAndSendToUser( principal.getName() , "/queue/private", message);
	}  
}