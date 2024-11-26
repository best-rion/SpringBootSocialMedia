package com.example.social.Social.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.social.Social.dto.SignupForm;
import com.example.social.Social.model.User;
import com.example.social.Social.repository.UserRepository;

@Controller
public class AuthController
{
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/login")
	public String loginGet()
	{
		return "auth/login";
	}
	
	
	@GetMapping("/signup")
	public String signupGet(Model model)
	{
		model.addAttribute("SignupForm", new SignupForm());
		return "auth/signup";
	}
	
	
	@PostMapping("/signup")
	public String signupPost(@ModelAttribute SignupForm form, Model model)
	{	
		if (userRepository.checkIfUsernameExists(form.getUsername()) != 0)
		{
			model.addAttribute("usernameAlreadyExists", true);
			return "auth/signup";
		}
		userRepository.save(new User(form));
		return "redirect:/login";
	}
}