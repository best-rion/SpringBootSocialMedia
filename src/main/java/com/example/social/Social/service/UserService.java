package com.example.social.Social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social.Social.model.User;
import com.example.social.Social.repository.UserRepository;

@Service
public class UserService
{
	@Autowired
	private UserRepository userRepo;
	
	
	public User getByUsername(String username)
	{
		return userRepo.findByUsername(username);
	}
}