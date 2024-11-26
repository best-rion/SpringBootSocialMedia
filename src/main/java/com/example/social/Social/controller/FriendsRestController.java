package com.example.social.Social.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.social.Social.model.User;
import com.example.social.Social.repository.UserRepository;

@RestController
public class FriendsRestController
{
	@Autowired
	UserRepository userRepository;
	
	
	
	@PutMapping("/sendRequest")
	public String sendRequest(@RequestBody String id, Principal principal)
	{
		User current_user = userRepository.findByUsername(principal.getName());
		User recepient = userRepository.findById(Integer.parseInt(id));
		
		current_user.sentRequests.add(recepient);
		recepient.receivedRequests.add(current_user);

		userRepository.save(current_user);
		userRepository.save(recepient);
		return "1";
	}
	
	
	
	@PutMapping("/cancelRequest")
	public String cancelRequest(@RequestBody String id, Principal principal)
	{
		User current_user = userRepository.findByUsername(principal.getName());
		User recepient = userRepository.findById(Integer.parseInt(id));

		current_user.sentRequests.remove(recepient);
		recepient.receivedRequests.remove(current_user);

		userRepository.save(current_user);
		userRepository.save(recepient);
		return "1";
	}
	
	
	
	@PutMapping("/acceptRequest")
	public String acceptRequest(@RequestBody String id, Principal principal)
	{
		User current_user = userRepository.findByUsername(principal.getName());
		User sender = userRepository.findById(Integer.parseInt(id));
		
		current_user.receivedRequests.remove(sender);
		current_user.friends.add(sender);
		
		sender.sentRequests.remove(current_user);
		sender.friends.add(current_user);
		
		userRepository.save(current_user);
		userRepository.save(sender);
		return "1";
	}
	
	
	
	@PutMapping("/deleteRequest")
	public String deleteRequest(@RequestBody String id, Principal principal)
	{
		User current_user = userRepository.findByUsername(principal.getName());
		User sender = userRepository.findById(Integer.parseInt(id));
		
		current_user.receivedRequests.remove(sender);
		sender.sentRequests.remove(current_user);
		
		userRepository.save(current_user);
		userRepository.save(sender);
		return "1";
	}
}