package com.example.social.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.social.model.Post;
import com.example.social.model.User;
import com.example.social.repository.PostRepository;
import com.example.social.repository.UserRepository;

@Controller
public class PostController
{
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	

	@GetMapping("/post")
	public String postGet(Model model, Principal principal)
	{
		model.addAttribute("post", new Post());
		model.addAttribute("principal", principal);
		return "post";
	}
	
	
	@PostMapping("/post")
	public String postPost(@ModelAttribute Post post, Principal principal)
	{
		User author = userRepository.findByUsername(principal.getName());
		
		post.setAuthor(author);
		post.setTime(new Date());
		
		postRepository.save(post);
		
		author.posts.add(post);
		
		userRepository.save(author);
		
		return "redirect:/";
	}
}