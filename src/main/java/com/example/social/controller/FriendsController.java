package com.example.social.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.social.model.User;
import com.example.social.repository.UserRepository;

@Controller
public class FriendsController
{
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/friends")
	public String friends(Model model, Principal principal)
	{
		List<User> suggestions = userRepository.findAll();
		
		User current_user = userRepository.findByUsername(principal.getName());
		Set<User> sentRequests = current_user.sentRequests;
		Set<User> receivedRequests = current_user.receivedRequests;
		Set<User> friends = current_user.friends;
		
		suggestions.remove(current_user);
		suggestions.removeAll(sentRequests);
		suggestions.removeAll(receivedRequests);
		suggestions.removeAll(friends);

		model.addAttribute("friends", friends);
		model.addAttribute("received_requests", current_user.receivedRequests);
		model.addAttribute("sent_requests", current_user.sentRequests);
		model.addAttribute("users", suggestions);
		model.addAttribute("principal", principal);
		return "friends";
	}
}