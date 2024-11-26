package com.example.social.Social.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social.Social.model.Post;
import com.example.social.Social.model.User;
import com.example.social.Social.repository.PostRepository;
import com.example.social.Social.repository.UserRepository;

@RestController
@RequestMapping("/delete")
public class DeleteController
{
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@DeleteMapping("/post/{id}")
	public void deletePost(@PathVariable int id, Principal principal)
	{
		Post post = postRepository.findById(id);
		
		User current_user = userRepository.findByUsername(principal.getName());
		
		if (post.isAuthoredBy(current_user))
		{
			jdbcTemplate.execute(String.format("DELETE FROM user_liked_posts where post_id = %d;",id));
			
			postRepository.delete(post);
		}
	}
}